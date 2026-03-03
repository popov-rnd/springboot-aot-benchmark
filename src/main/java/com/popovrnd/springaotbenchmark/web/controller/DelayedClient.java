package com.popovrnd.springaotbenchmark.web.controller;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://127.0.0.1:8081/waiting")
public interface DelayedClient {

    @GetExchange
    void getDelayed();
}
