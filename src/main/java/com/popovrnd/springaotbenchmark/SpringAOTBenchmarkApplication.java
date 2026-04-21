package com.popovrnd.springaotbenchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAOTBenchmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAOTBenchmarkApplication.class, args);
    }

}
