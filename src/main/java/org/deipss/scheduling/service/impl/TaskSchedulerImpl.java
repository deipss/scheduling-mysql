package org.deipss.scheduling.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.dal.mapper.SchedulingTaskMapper;
import org.deipss.scheduling.service.Task;
import org.deipss.scheduling.service.TaskScheduler;
import org.deipss.scheduling.util.IpUtil;
import org.deipss.scheduling.util.SleepUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskSchedulerImpl implements TaskScheduler<Boolean> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SchedulingTaskMapper schedulingTaskMapper;


    @Autowired
    @Qualifier("schedulingMysqlThreadPoolExecutor")
    private ThreadPoolExecutor schedulingThreadPoolExecutor;

    @Autowired
    @Qualifier("schedulingExecuteThreadPoolExecutor")
    private ThreadPoolExecutor executeThreadPoolExecutor;

    @Override
    public void scheduling() {
        while (true) {
            CompletableFuture<List<Task<Boolean>>> listCompletableFuture = CompletableFuture.supplyAsync(this::scan, schedulingThreadPoolExecutor);
            try {
                List<Task<Boolean>> tasks = listCompletableFuture.get();
                if (null == tasks || tasks.size() < 1) {
                    continue;
                }
                List<CompletableFuture<Boolean>> completableFutures = new ArrayList<>(tasks.size());
                for (Task<Boolean> task : tasks) {
                    completableFutures.add(CompletableFuture.supplyAsync(task::execute, executeThreadPoolExecutor));
                }
                List<Boolean> collect = completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
                log.info("执行结果:::{}", collect);
            } catch (InterruptedException | ExecutionException e) {
                log.error("任务扫描或执行异常,信息={}", e.getMessage(), e);
            }
            SleepUtil.sleepSecond(10);
        }

    }

    @Override
    public List<Task<Boolean>> scan() {
        List<String> beanNameList = schedulingTaskMapper.scanLockName(LocalTime.now(), new Date());
        if (null == beanNameList || beanNameList.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<Task<Boolean>> taskList = new ArrayList<>(beanNameList.size());
        for (String s : beanNameList) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(s);
            } catch (ClassNotFoundException e) {
                log.error("{}类不存在", s);
            }
            if (clazz != null) {
                Task bean = (Task) applicationContext.getBean(clazz);
                taskList.add(bean);
            }
        }
        return taskList;
    }


}
