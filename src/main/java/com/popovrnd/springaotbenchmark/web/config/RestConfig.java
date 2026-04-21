package com.popovrnd.springaotbenchmark.web.config;

import com.popovrnd.springaotbenchmark.web.controller.ConcurrencyProperties;
import com.popovrnd.springaotbenchmark.web.controller.DelayedClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestConfig {

   @Bean
    public RestClient restClient(ConcurrencyProperties props) {

        PoolingHttpClientConnectionManager cm =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setMaxConnTotal(props.max())
                        .setMaxConnPerRoute(props.max())
                        .build();

        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setConnectionManager(cm)
                        .build();

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }

    @Bean
    public DelayedClient delayedClient(RestClient restClient) {

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                        .build();

        return factory.createClient(DelayedClient.class);
    }
}
