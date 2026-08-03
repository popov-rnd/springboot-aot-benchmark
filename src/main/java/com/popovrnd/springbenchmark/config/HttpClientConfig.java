package com.popovrnd.springbenchmark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;

import java.net.http.HttpClient;
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
    HttpClient jdkHttpClient(
            ExecutorService httpClientExecutor,
            HttpClientProperties properties) {
        return HttpClient.newBuilder()
                .executor(httpClientExecutor)
                .connectTimeout(properties.connectTimeout())
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    @Bean
    JdkClientHttpRequestFactory jdkClientHttpRequestFactory(
            HttpClient httpClient,
            HttpClientProperties properties) {
        var requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(properties.readTimeout());
        return requestFactory;
    }

    @Bean
    RestClientHttpServiceGroupConfigurer httpServiceGroupConfigurer(
            JdkClientHttpRequestFactory requestFactory) {

        return groups -> groups.forEachClient(
                (group, clientBuilder) -> clientBuilder.requestFactory(requestFactory));
    }
}
