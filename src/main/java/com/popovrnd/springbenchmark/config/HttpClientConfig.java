package com.popovrnd.springbenchmark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
public class HttpClientConfig {

    @Bean
    ExecutorService httpClientExecutor() {
        return Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual()
                        .name("jdk-http-client-", 0)
                        .factory());
    }

    @Bean
    HttpClient jdkHttpClient(ExecutorService httpClientExecutor) {
        return HttpClient.newBuilder()
                .executor(httpClientExecutor)
                .connectTimeout(Duration.ofMillis(500))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    @Bean
    JdkClientHttpRequestFactory jdkClientHttpRequestFactory(HttpClient httpClient) {
        var requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(1));
        return requestFactory;
    }

    @Bean
    RestClientHttpServiceGroupConfigurer httpServiceGroupConfigurer(
            JdkClientHttpRequestFactory requestFactory) {

        return groups -> groups.forEachClient(
                (group, clientBuilder) -> clientBuilder.requestFactory(requestFactory));
    }
}
