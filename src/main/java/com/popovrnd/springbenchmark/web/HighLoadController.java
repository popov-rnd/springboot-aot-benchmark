package com.popovrnd.springbenchmark.web;

import com.popovrnd.springbenchmark.service.ExternalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class HighLoadController {

    private static final Logger log =
            LoggerFactory.getLogger(HighLoadController.class);

    private final ExternalClient externalClient;

    public HighLoadController(ExternalClient externalClient) {
        this.externalClient = externalClient;
    }

    //---------------------Servlet-level concurrency limits-----------------------

    @GetMapping("path1")
    @ResponseStatus(HttpStatus.OK)
    public void protectedPath1() {
        log.info("High load path1 is called! Thread = {}", Thread.currentThread());
        externalClient.getPath1();
    }

    @GetMapping("path2")
    @ResponseStatus(HttpStatus.OK)
    public void protectedPath2() {
        externalClient.getPath1();
    }

    @GetMapping("path3")
    @ResponseStatus(HttpStatus.OK)
    public void protectedPath3() {
        externalClient.getPath1();
    }

    //---------------------App-level concurrency limits--------------------------

    @GetMapping("path4")
    @ResponseStatus(HttpStatus.OK)
    @ConcurrencyLimit(limit = 5, policy = ConcurrencyLimit.ThrottlePolicy.REJECT)
    public void protectedPath4() throws InterruptedException {
        // Heavy operation;
    }
}
