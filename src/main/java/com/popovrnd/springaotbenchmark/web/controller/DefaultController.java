package com.popovrnd.springaotbenchmark.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class DefaultController {

    private static final Logger log =
            LoggerFactory.getLogger(DefaultController.class);

    @GetMapping
    public ResponseEntity<Void> get() {
        log.info("Default is called!");
        return ResponseEntity.ok().build();
    }
}
