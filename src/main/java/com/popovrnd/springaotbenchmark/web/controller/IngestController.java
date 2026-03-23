package com.popovrnd.springaotbenchmark.web.controller;

import com.popovrnd.springaotbenchmark.web.request.IngestRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestController {

    private static final Logger log =
            LoggerFactory.getLogger(IngestController.class);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path="/ingest", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void ingest(@Valid @RequestBody IngestRequest request) {
        //log.info("Ingest controller, thread = {}", Thread.currentThread());
    }
}
