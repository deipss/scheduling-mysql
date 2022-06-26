package org.deipss.scheduling.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.dal.mapper.SchedulingTaskMapper;
import org.deipss.scheduling.service.Task;
import org.deipss.scheduling.service.TaskLock;
import org.deipss.scheduling.service.TaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class TaskSchedulerImpl implements TaskScheduler<Boolean> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SchedulingTaskMapper schedulingTaskMapper;

    @Autowired
    private TaskLock taskLock;

    @Autowired
    @Qualifier("schedulingThreadPoolExecutor")
    private ThreadPoolExecutor schedulingThreadPoolExecutor;

    @Autowired
    @Qualifier("executeThreadPoolExecutor")
    private ThreadPoolExecutor executeThreadPoolExecutor;

    @Override

    public void scheduling() {
        while (true) {
            CompletableFuture<List<Task<Boolean>>> listCompletableFuture = CompletableFuture.supplyAsync(this::scan, schedulingThreadPoolExecutor);
            try {
                List<Task<Boolean>> tasks = listCompletableFuture.get();
                if (null == tasks) {
                    continue;
                }
                tasks.forEach(this::execute);
            } catch (InterruptedException | ExecutionException e) {
                log.error("任务扫描或执行异常,信息={}", e.getMessage(), e);
            }
        }

    }

    @Override
    public List<Task<Boolean>> scan() {
        List<String> beanNameList = schedulingTaskMapper.scan();
        if (null == beanNameList || beanNameList.isEmpty()) {
            return null;
        }
        List<Task<Boolean>> taskList = new ArrayList<>(beanNameList.size());
        for (String s : beanNameList) {
            Task bean = (Task) applicationContext.getBean(s);
            taskList.add(bean);
        }
        return taskList;
    }

    @Override
    public Boolean execute(Task<Boolean> task) {
        String name = task.getClass().getName();
        boolean lock = taskLock.lock(name);
        if (!lock) {
            log.info("上锁失败={}", name);
            return false;
        }
        try {
            CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(task::execute, schedulingThreadPoolExecutor);
            return completableFuture.get();
        } catch (Exception e) {
            log.error("任务执行异常", e);
        } finally {
            boolean unlock = taskLock.unlock(name);
            if (!unlock) {
                log.info("锁释放失败={}", name);
            }
        }
        return false;
    }
}