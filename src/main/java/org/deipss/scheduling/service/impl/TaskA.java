package org.deipss.scheduling.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.service.AbstractTask;

@Slf4j
public class TaskA extends AbstractTask {
    @Override
    public Boolean doBiz() {
        log.info("任务运行A");
        return true;
    }
}
