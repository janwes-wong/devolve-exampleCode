package com.janwes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.config
 * @date 2021/6/3 14:22
 * @description
 */
@Configuration
public class ScheduledConfig {

    /**
     * 手动注入TaskScheduler定时任务bean，解决springboot原生的定时任务与websocket的定时任务@EnableWebSocket注解冲突
     * 冲突的源码在WebSocketConfigurationSupport类中的最后一个方法defaultSockJsTaskScheduler方法
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolScheduler = new ThreadPoolTaskScheduler();
        threadPoolScheduler.setThreadNamePrefix("SockJS-");
        threadPoolScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        threadPoolScheduler.setRemoveOnCancelPolicy(true);
        threadPoolScheduler.setPoolSize(10);
        threadPoolScheduler.initialize();
        return threadPoolScheduler;
    }
}
