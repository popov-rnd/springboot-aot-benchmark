package com.popovrnd.springbenchmark;

import com.popovrnd.springbenchmark.service.DelayedClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
@ConfigurationPropertiesScan
@ImportHttpServices(basePackageClasses = DelayedClient.class)
public class SpringBenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBenchmarkApplication.class, args);
    }

}
