package com.popovrnd.springaotbenchmark.web.config;

import com.popovrnd.springaotbenchmark.web.controller.DelayedClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(basePackageClasses = DelayedClient.class)
public class RestConfig {

    @Bean
    public CloseableHttpClient apacheHttpClient() {

        PoolingHttpClientConnectionManager cm =
                new PoolingHttpClientConnectionManager();

        cm.setMaxTotal(10000);
        cm.setDefaultMaxPerRoute(10000);

        return HttpClients.custom()
                .setConnectionManager(cm)
                .disableAutomaticRetries()
                .build();
    }

    @Bean
    public RestClient restClient(CloseableHttpClient apacheHttpClient) {

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(apacheHttpClient);

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}
