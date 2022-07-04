package org.deipss.scheduling.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.dal.entity.SchedulingTask;
import org.deipss.scheduling.dal.entity.SchedulingTaskHistory;
import org.deipss.scheduling.dal.mapper.SchedulingTaskHistoryMapper;
import org.deipss.scheduling.dal.mapper.SchedulingTaskMapper;
import org.deipss.scheduling.enums.TaskStatusEnum;
import org.deipss.scheduling.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@Slf4j
public abstract class AbstractTask implements Task<Boolean> {

    @Autowired
    private SchedulingTaskMapper schedulingTaskMapper;

    @Autowired
    private SchedulingTaskHistoryMapper schedulingTaskHistoryMapper;

    protected boolean lock() {
        return schedulingTaskMapper.lock(this.getClass().getName(), IpUtil.getIP(), LocalTime.now(), new Date()) > 0;
    }


    protected boolean unlock() {
        return schedulingTaskMapper.unlock(this.getClass().getName(), IpUtil.getIP(), calculateNextTime()) > 0;
    }

    public Boolean execute() {

        SchedulingTask schedulingTask = schedulingTaskMapper.selectByLockName(this.getClass().getName());
        if (!lock()) {
            log.info("上锁失败");
            schedulingTaskHistoryMapper.insert(getSchedulingTaskHistory(schedulingTask,TaskStatusEnum.LOCK_FAIL));
            return false;
        }
        try {
            Boolean rst = doBiz();
            if(rst){
                schedulingTaskHistoryMapper.insert(getSchedulingTaskHistory(schedulingTask,TaskStatusEnum.DOWN));
            }

            return rst;
        } catch (Exception e) {
            log.error("任务执行异常", e);
            schedulingTaskHistoryMapper.insert(getSchedulingTaskHistory(schedulingTask,TaskStatusEnum.EXCEPTION));
            return false;
        } finally {
            unlock();
        }
    }

    private SchedulingTaskHistory getSchedulingTaskHistory(SchedulingTask t , TaskStatusEnum taskStatusEnum) {
        SchedulingTaskHistory schedulingTaskHistory = new SchedulingTaskHistory();
        schedulingTaskHistory.setLockName(t.getLockName());
        schedulingTaskHistory.setTaskStatus(taskStatusEnum);
        schedulingTaskHistory.setStartTime(t.getStartTime());
        schedulingTaskHistory.setEndTime(t.getEndTime());
        schedulingTaskHistory.setNextStart(t.getNextStart());
        schedulingTaskHistory.setTryLockCnt(t.getTryLockCnt());
        schedulingTaskHistory.setTimeGap(t.getTimeGap());
        schedulingTaskHistory.setOwnerIp(IpUtil.getIP());
        return schedulingTaskHistory;
    }


    private Date calculateNextTime() {
        String s = schedulingTaskMapper.selectTimeGap(this.getClass().getName());
        LocalDateTime localDateTime = LocalDateTime.now();
        int gap = Integer.parseInt(s.substring(0, s.length() - 1));
        if (s.toLowerCase().endsWith("s")) {
            localDateTime = localDateTime.plusSeconds(gap);
        }
        if (s.toLowerCase().endsWith("m")) {
            localDateTime = localDateTime.plusMinutes(gap);
        }
        if (s.toLowerCase().endsWith("h")) {
            localDateTime = localDateTime.plusHours(gap);
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


}
