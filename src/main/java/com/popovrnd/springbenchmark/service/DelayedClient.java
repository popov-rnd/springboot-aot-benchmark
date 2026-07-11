package com.popovrnd.springbenchmark.service;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://127.0.0.1:8081/delay")
public interface DelayedClient {

    @GetExchange
    void getDelayed();
}
