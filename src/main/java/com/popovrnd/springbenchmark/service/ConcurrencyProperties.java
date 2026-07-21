package com.popovrnd.springbenchmark.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "concurrency")
public record ConcurrencyProperties(int target) {

    public ConcurrencyProperties {
        if (target <= 0) {
            throw new IllegalArgumentException("Target concurrency must be > 0");
        }
    }
}