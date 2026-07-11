package com.popovrnd.springbenchmark.web;

import com.popovrnd.springbenchmark.service.ExternalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class ProtectedController {

    private static final Logger log =
            LoggerFactory.getLogger(ProtectedController.class);

    private final ExternalClient externalClient;

    public ProtectedController(ExternalClient externalClient) {
        this.externalClient = externalClient;

    }

    @GetMapping("path1")
    @ResponseStatus(HttpStatus.OK)
    public void protectedPath1() {
        log.info("Protected path1 is called! Thread = {}", Thread.currentThread());
        //externalClient.getPath1();
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
}
