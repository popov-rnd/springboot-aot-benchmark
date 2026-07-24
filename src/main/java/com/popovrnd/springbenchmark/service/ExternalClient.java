package com.popovrnd.springbenchmark.service;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

// Service time ~400 ms
@HttpExchange(url = "http://127.0.0.1:8081")
public interface ExternalClient {

    @GetExchange("/delay")
    void getDelayed();

}
