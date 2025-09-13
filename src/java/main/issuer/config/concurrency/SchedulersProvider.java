package com.gmail.bishoybasily.licensing.issuer.config.concurrency;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executor;

public interface SchedulersProvider {

    static SchedulersProvider of(Executor executor) {
        return new SchedulersProvider() {
            @Override
            public Scheduler subscribeOn() {
                return Schedulers.fromExecutor(executor);
            }

        };
    }

    Scheduler subscribeOn();

}
