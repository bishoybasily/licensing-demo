package com.gmail.bishoybasily.licensing.issuer.config.concurrency;

import com.gmail.bishoybasily.licensing.commons.concurrency.OverloadHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Configuration
public class ConcurrencyConfig {

    @Bean
    Executor executor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    SchedulersProvider databaseSchedulersProvider(Executor executor) {
        return SchedulersProvider.of(executor);
    }

    @Bean
    Semaphore databaseSemaphore() {
        return new Semaphore(10, true);
    }

    @Bean
    OverloadHandler databaseOverloadHandler(Semaphore databaseSemaphore) {
        return new OverloadHandler(OverloadHandler.Strategy.FAIL, databaseSemaphore);
    }

}
