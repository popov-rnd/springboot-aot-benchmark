package com.popovrnd.springaotbenchmark;

import com.popovrnd.springaotbenchmark.web.controller.DelayedClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
@EnableResilientMethods
@ImportHttpServices(basePackageClasses = DelayedClient.class)
public class SpringBenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBenchmarkApplication.class, args);
    }

}
