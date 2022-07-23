package org.deipss.scheduling.config;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "scheduling.mysql", value = "enabled")
public class SchedulingMysqlThreadConfig {

    @Bean("schedulingMysqlThreadPoolExecutor")
    public ThreadPoolExecutor scheduling() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-scheduling-thread" + "-%d")
                .setDaemon(false).build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1, 32, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(128),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!executor.isShutdown()) {
                List<Runnable> runnables = executor.shutdownNow();
                log.warn("schedulingMysqlThreadPoolExecutor task losing {}", runnables.size());
            }
            log.info("schedulingMysqlThreadPoolExecutor close");
        }));
        return executor;
    }

    @Bean("schedulingExecuteThreadPoolExecutor")
     public ThreadPoolExecutor executor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-execute-thread" + "-%d")
                .setDaemon(false).build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() << 1, 32, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(256),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!executor.isShutdown()) {
                List<Runnable> runnables = executor.shutdownNow();
                log.warn("schedulingExecuteThreadPoolExecutor task losing :{}", runnables.size());
            }
            log.info("schedulingExecuteThreadPoolExecutor close");
        }));
        return executor;
    }
}
