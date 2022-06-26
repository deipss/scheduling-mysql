package org.deipss.scheduling.config;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {

    @Bean("schedulingThreadPoolExecutor")
    public ThreadPoolExecutor scheduling() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(" task-scheduling-thread" + "-%d")
                .setDaemon(false).build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1, 32, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(128),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        return executor;
    }

    @Bean("executeThreadPoolExecutor")
     public ThreadPoolExecutor executor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(" task-execute-thread" + "-%d")
                .setDaemon(false).build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() << 1, 32, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(256),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );


        return executor;
    }
}
