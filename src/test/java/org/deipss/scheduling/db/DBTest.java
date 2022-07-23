package org.deipss.scheduling.db;

import org.deipss.scheduling.config.SchedulingMysqlDataSourceConfig;
import org.deipss.scheduling.dal.mapper.SchedulingTaskHistoryMapper;
import org.deipss.scheduling.dal.mapper.SchedulingTaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = SchedulingMysqlDataSourceConfig.class)
@RunWith(SpringRunner.class)
public class DBTest {

    @Autowired
    SchedulingTaskMapper schedulingTaskMapper;

    @Autowired
    SchedulingTaskHistoryMapper schedulingTaskHistoryMapper;
    @Test
    public void test(){
        List<String> strings = schedulingTaskMapper.scanLockName(LocalTime.now(), new Date());
        for (String string : strings) {

            System.out.println(string);
        }
    }

    @Test
    public void test1(){

        Date date = schedulingTaskMapper.selectMinNextStart();
        System.out.println(date);
        long a = System.currentTimeMillis() - date.getTime();
        System.out.println(a/1000);
    }
}
