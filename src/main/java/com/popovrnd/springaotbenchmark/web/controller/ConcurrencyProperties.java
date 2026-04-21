package com.popovrnd.springaotbenchmark.web.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "concurrency")
public record ConcurrencyProperties(
        int max,
        boolean semaphore) {

    public ConcurrencyProperties {
        if (max <= 0) {
            throw new IllegalArgumentException("Max concurrency must be > 0");
        }
    }
}
