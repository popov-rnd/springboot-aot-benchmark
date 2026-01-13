package com.popovrnd.springaotbenchmark.web.controller;

import com.popovrnd.springaotbenchmark.web.request.EchoRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echo")
public class EchoController {

    private static final Logger log =
            LoggerFactory.getLogger(EchoController.class);

    @PostMapping
    public EchoRequest echo(@Valid @RequestBody EchoRequest request) {
        log.info("Echo controller with payload = {}", request);
        return request;
    }
}
