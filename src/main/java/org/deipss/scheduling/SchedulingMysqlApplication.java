package org.deipss.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("org.deipss.scheduling")
public class SchedulingMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingMysqlApplication.class, args);
    }

}
