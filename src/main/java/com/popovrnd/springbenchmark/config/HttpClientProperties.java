package com.popovrnd.springbenchmark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "http-client")
public record HttpClientProperties(
        Duration connectTimeout,
        Duration readTimeout) {
}
