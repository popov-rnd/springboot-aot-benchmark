package com.popovrnd.springbenchmark.web;

import com.popovrnd.springbenchmark.service.DelayedClient;
import com.popovrnd.springbenchmark.service.DelayedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/io")
public class IOController {

    private static final Logger log =
            LoggerFactory.getLogger(IOController.class);

    private final DelayedClient delayedClient;

    private final DelayedService delayedService;

    public IOController(DelayedClient delayedClient, DelayedService delayedService) {
        this.delayedClient = delayedClient;
        this.delayedService = delayedService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getBlocking() {
        //log.info("IO is called! Thread = {}", Thread.currentThread());
        delayedService.callBlocking(() -> delayedClient.getDelayed());
    }
}
