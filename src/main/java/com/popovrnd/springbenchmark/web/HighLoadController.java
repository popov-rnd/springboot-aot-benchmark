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


    @GetMapping("critical")
    @ResponseStatus(HttpStatus.OK)
    @ConcurrencyLimit(limitString = "${concurrency.target}", policy = ConcurrencyLimit.ThrottlePolicy.REJECT)
    public void criticalPath() {
        externalClient.getDelayed();
    }
}
