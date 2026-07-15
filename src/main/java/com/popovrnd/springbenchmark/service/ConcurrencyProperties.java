package com.popovrnd.springbenchmark.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "concurrency")
public record ConcurrencyProperties(Map<String, Integer> locals) {

    public ConcurrencyProperties {

        locals = locals == null ? Map.of() : Map.copyOf(locals);

        locals.forEach((path, limit) -> {
            if (path == null || path.isBlank()) {
                throw new IllegalArgumentException("Local concurrency path must not be blank");
            }

            if (limit == null) {
                throw new IllegalArgumentException("Local concurrency for '%s' cannot be empty".formatted(path));
            }
        });
    }
}