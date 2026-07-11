package com.popovrnd.springbenchmark.service;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "http://127.0.0.1:8081")
public interface ExternalClient {

    @GetExchange("/path1")
    void getPath1();

    @GetExchange("/path2")
    void getPath2();

    @GetExchange("/path3")
    void getPath3();
}
