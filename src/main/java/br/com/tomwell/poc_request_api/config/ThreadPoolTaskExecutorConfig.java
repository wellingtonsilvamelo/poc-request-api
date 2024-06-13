package br.com.tomwell.poc_request_api.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ch.qos.logback.classic.Logger;

@Configuration
public class ThreadPoolTaskExecutorConfig {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ThreadPoolTaskExecutorConfig.class);

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setRejectedExecutionHandler((runnable, threadPoolexecutor) -> LOGGER.warn("Thread pool is full"));
        executor.initialize();
        return executor;
    }

}
