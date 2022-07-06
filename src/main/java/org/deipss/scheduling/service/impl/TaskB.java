package org.deipss.scheduling.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.service.AbstractTask;
import org.deipss.scheduling.util.SleepUtil;

@Slf4j
public class TaskB extends AbstractTask {
    @Override
    public Boolean doBiz() {
        SleepUtil.sleepSecond(10);
        log.info("任务运行B");
        return false;
    }
}
