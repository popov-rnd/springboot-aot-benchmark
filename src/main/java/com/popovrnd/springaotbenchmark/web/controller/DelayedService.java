package com.popovrnd.springaotbenchmark.web.controller;

import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.stereotype.Service;

@Service
public class DelayedService {

    @ConcurrencyLimit(
            limitString = "${concurrency.max}",
            policy = ConcurrencyLimit.ThrottlePolicy.REJECT
    )
    public void callBlocking(Runnable action) {
        action.run();
    }
}