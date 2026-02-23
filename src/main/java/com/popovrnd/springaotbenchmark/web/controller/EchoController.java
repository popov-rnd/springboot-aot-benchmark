package com.popovrnd.springaotbenchmark.web.controller;

import com.popovrnd.springaotbenchmark.web.request.EchoRequest;
import com.popovrnd.springaotbenchmark.web.request.EchoRequest300;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    private static final Logger log =
            LoggerFactory.getLogger(EchoController.class);


    @PostMapping("/echo")
    public EchoRequest echo(@Valid @RequestBody EchoRequest request) {
        log.info("Echo controller with payload = {}", request);
        log.info("Thread = {}", Thread.currentThread());
        return request;
    }

    @PostMapping("/echo-300")
    public EchoRequest300 echo300(@Valid @RequestBody EchoRequest300 request) {
        log.info("Echo 300 controller with payload = {}", request);
        log.info("Thread = {}", Thread.currentThread());
        return request;
    }
}
