package com.popovrnd.springaotbenchmark.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/io")
public class IOController {

    private static final Logger log =
            LoggerFactory.getLogger(IOController.class);

    private final DelayedClient delayedClient;

    public IOController(DelayedClient delayedClient) {
        this.delayedClient = delayedClient;
    }

    @GetMapping
    public void getBlocking() {
        //log.info("IO is called! Thread = {}", Thread.currentThread());
        delayedClient.getDelayed();
    }
}
