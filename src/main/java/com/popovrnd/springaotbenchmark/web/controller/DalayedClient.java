package com.popovrnd.springaotbenchmark.web.controller;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/waiting")
public interface DalayedClient {

    @GetExchange
    void getDelayed();
}
