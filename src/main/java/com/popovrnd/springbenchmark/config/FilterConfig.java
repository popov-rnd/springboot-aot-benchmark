package com.popovrnd.springbenchmark.config;

import com.popovrnd.springbenchmark.config.filter.ConcurrencyLimitFilter;
import com.popovrnd.springbenchmark.service.ConcurrencyProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    FilterRegistrationBean<ConcurrencyLimitFilter> path1ConcurrencyFilter(ConcurrencyProperties properties) {

        return createFilter(
                "ConcurrencyLimitPath1Filter",
                "/path1/*",
                properties.locals().get("path1"),
                0);
    }

    @Bean
    FilterRegistrationBean<ConcurrencyLimitFilter> path2ConcurrencyFilter(ConcurrencyProperties properties) {

        return createFilter(
                "ConcurrencyLimitPath2Filter",
                "/path2/*",
                properties.locals().get("path2"),
                0);
    }

    @Bean
    FilterRegistrationBean<ConcurrencyLimitFilter> path3ConcurrencyFilter(ConcurrencyProperties properties) {

        return createFilter(
                "ConcurrencyLimitPath3Filter",
                "/path3/*",
                properties.locals().get("path3"),
                0);
    }

    private FilterRegistrationBean<ConcurrencyLimitFilter> createFilter(String name, String urlPattern, int maxConcurrency, int order) {

        var registration = new FilterRegistrationBean<ConcurrencyLimitFilter>();
        registration.setName(name);
        registration.setFilter(new ConcurrencyLimitFilter(maxConcurrency));
        registration.addUrlPatterns(urlPattern);
        registration.setOrder(order);

        return registration;
    }
}
