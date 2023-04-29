package org.deipss.scheduling.db.impl;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.service.AbstractTask;
import org.deipss.scheduling.util.SleepUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskB extends AbstractTask {
    @Override
    public Boolean doBiz() {
        SleepUtil.sleepSecond(2);
        log.info("任务运行B");
        return false;
    }
}
