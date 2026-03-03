package com.popovrnd.springaotbenchmark.web.config;

import com.popovrnd.springaotbenchmark.web.controller.DelayedClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.registry.ImportHttpServices;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@ImportHttpServices(basePackageClasses = DelayedClient.class)
public class RestConfig {

    /*@Bean
    public RestClient restClient() {

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        var requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(10));

        return RestClient.builder()
                .baseUrl("http://127.0.0.1:8081")
                .requestFactory(requestFactory)
                .build();
    }*/
}
