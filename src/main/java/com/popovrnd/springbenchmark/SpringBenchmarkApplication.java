package com.popovrnd.springbenchmark;

import com.popovrnd.springbenchmark.service.ExternalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
@EnableResilientMethods
@ConfigurationPropertiesScan
@ImportHttpServices(basePackageClasses = ExternalClient.class)
public class SpringBenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBenchmarkApplication.class, args);
    }

}
