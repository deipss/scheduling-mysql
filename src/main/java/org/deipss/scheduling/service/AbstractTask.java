package org.deipss.scheduling.service;

import lombok.AllArgsConstructor;
import org.deipss.scheduling.dal.mapper.SchedulingTaskMapper;
import org.deipss.scheduling.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

@AllArgsConstructor
public abstract class AbstractTask implements Task {

    @Autowired
    private SchedulingTaskMapper schedulingTaskMapper;

    protected boolean lock() {
        return schedulingTaskMapper.lock(this.getClass().getName(), IpUtil.getIP(), LocalTime.now()) > 0;
    }


    private boolean unlock() {
        return schedulingTaskMapper.unlock(this.getClass().getName(), IpUtil.getIP(), calculateNextTime()) > 0;
    }


    private LocalTime calculateNextTime(){
        return LocalTime.now();
    }


}
