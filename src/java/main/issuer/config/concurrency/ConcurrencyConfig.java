package com.gmail.bishoybasily.licensing.issuer.config.concurrency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ConcurrencyConfig {

    @Bean
    Executor executor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    SchedulersProvider schedulersProvider(Executor executor) {
        return SchedulersProvider.of(executor);
    }
}
