package org.deipss.scheduling.config;

import lombok.extern.slf4j.Slf4j;
import org.deipss.scheduling.service.TaskScheduler;
import org.deipss.scheduling.util.SleepUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "scheduling.mysql", value = "enabled")
@ComponentScan("org.deipss.scheduling")
public class SchedulingMysqlRunnerConfig implements ApplicationRunner {
    @Autowired
    private TaskScheduler taskScheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("执行启动成功，开始执行定时任务");
        //SleepUtil.sleepSecond(10);
        int init = taskScheduler.init();
        log.info("初始化{}个任务完成", init);
        taskScheduler.scheduling();
    }

}
