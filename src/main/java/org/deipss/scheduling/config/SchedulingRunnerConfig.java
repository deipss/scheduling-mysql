package org.deipss.scheduling.config;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.service.TaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "scheduling.mysql" ,value = "enabled")
public class SchedulingRunnerConfig implements ApplicationRunner {
    @Autowired
    private TaskScheduler taskScheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("执行启动成功，开始执行定时任务");
        taskScheduler.scheduling();
    }

}
