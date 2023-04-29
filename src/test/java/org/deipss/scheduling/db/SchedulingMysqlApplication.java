package org.deipss.scheduling.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SchedulingMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingMysqlApplication.class, args);
    }

}
