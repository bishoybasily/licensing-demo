package com.gmail.bishoybasily.licensing.commons.concurrency;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

@RequiredArgsConstructor
public class OverloadHandler {

    private final Strategy strategy;
    private final Semaphore semaphore;

    public <T> T execute(Callable<T> callable) throws Exception {
        boolean acquired = false;
        try {
            switch (strategy) {
                case THROTTLE -> {
                    semaphore.acquire();
                    acquired = true;
                }
                case FAIL -> {
                    acquired = semaphore.tryAcquire();
                    if (!acquired) {
                        throw new Exception("Resource is too busy, no permits available");
                    }
                }
            }
            return callable.call();
        } finally {
            if (acquired) {
                semaphore.release();
            }
        }
    }

    public enum Strategy {

        THROTTLE,
        FAIL

    }

}
