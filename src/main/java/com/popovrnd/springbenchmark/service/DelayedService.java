package com.popovrnd.springbenchmark.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;

@Service
public class DelayedService {

    private final Semaphore semaphore;

    private final boolean useSemaphore;

    public DelayedService(ConcurrencyProperties props) {
        this.useSemaphore = props.semaphore();
        this.semaphore = new Semaphore(props.max());
    }

    public void callBlocking(Runnable action) {

        if (!useSemaphore) {
            action.run();
            return;
        }

        boolean acquired = false;

        try {
            semaphore.acquire();
            acquired = true;

            action.run();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while acquiring semaphore", e);
        } finally {
            if (acquired) {
                semaphore.release();
            }
        }
    }
}