package org.deipss.scheduling.db;

import org.deipss.scheduling.config.SchedulingMysqlDataSourceConfig;
import org.deipss.scheduling.config.SchedulingMysqlRunnerConfig;
import org.deipss.scheduling.config.SchedulingMysqlThreadConfig;
import org.deipss.scheduling.dal.mapper.SchedulingTaskHistoryMapper;
import org.deipss.scheduling.dal.mapper.SchedulingTaskMapper;
import org.deipss.scheduling.db.impl.TaskA;
import org.deipss.scheduling.db.impl.TaskB;
import org.deipss.scheduling.service.impl.TaskSchedulerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {SchedulingMysqlRunnerConfig.class, SchedulingMysqlThreadConfig.class, SchedulingMysqlDataSourceConfig.class,
        TaskA.class, TaskB.class, TaskSchedulerImpl.class
})
@RunWith(SpringRunner.class)
@TestComponent(value = "org.deipss.scheduling.config")
public class DBTest {

    @Autowired
    SchedulingTaskMapper schedulingTaskMapper;

    @Autowired
    SchedulingTaskHistoryMapper schedulingTaskHistoryMapper;
    @Autowired
    private TaskSchedulerImpl taskScheduler;

    @Test
    public void testRun() {
        taskScheduler.scheduling();
    }

    @Test
    public void test() {
        List<String> strings = schedulingTaskMapper.scanLockName(LocalTime.now(), new Date());
        for (String string : strings) {

            System.out.println(string);
        }
    }

    @Test
    public void test1() {

        Date date = schedulingTaskMapper.selectMinNextStart();
        System.out.println(date);
        long a = System.currentTimeMillis() - date.getTime();
        System.out.println(a / 1000);
    }
}
