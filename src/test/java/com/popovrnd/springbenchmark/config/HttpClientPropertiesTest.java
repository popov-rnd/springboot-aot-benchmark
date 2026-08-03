package com.popovrnd.springbenchmark.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class HttpClientPropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfiguration.class);

    @Test
    void bindsConfiguredTimeouts() {
        contextRunner
                .withPropertyValues(
                        "http-client.connect-timeout=750ms",
                        "http-client.read-timeout=2s")
                .run(context -> {
                    var properties = context.getBean(HttpClientProperties.class);

                    assertThat(properties.connectTimeout()).isEqualTo(Duration.ofMillis(750));
                    assertThat(properties.readTimeout()).isEqualTo(Duration.ofSeconds(2));
                });
    }

    @EnableConfigurationProperties(HttpClientProperties.class)
    static class TestConfiguration {
    }
}
