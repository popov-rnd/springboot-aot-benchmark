package com.popovrnd.springbenchmark.config;

import com.popovrnd.springbenchmark.service.ConcurrencyProperties;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> semaphoreValveCustomizer(ConcurrencyProperties properties) {

        return factory -> factory.addContextCustomizers(context -> {
            var valve = new LoadSheddingValve();

            valve.setConcurrency(properties.target());
            valve.setBlock(false);
            valve.setFairness(false);
            valve.setHighConcurrencyStatus(503);

            context.getPipeline().addValve(valve);
        });
    }
}
