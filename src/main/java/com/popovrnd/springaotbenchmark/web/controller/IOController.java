package com.popovrnd.springaotbenchmark.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("/io")
public class IOController {

    private static final Logger log =
            LoggerFactory.getLogger(IOController.class);

    private static final String WAITING_URI = "http://127.0.0.1:8081/waiting";

    // Reuse one client
    //private static final RestClient CLIENT = RestClient.create();

    /*@GetMapping
    public ResponseEntity<Void> getBlocking() {

        //log.info("IO is called! Thread = {}", Thread.currentThread());

        HttpStatusCode status = CLIENT.get()
                .uri(WAITING_URI)
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

        return status.is2xxSuccessful()
                ? ResponseEntity.ok().build()
                : ResponseEntity.internalServerError().build();
    }*/

    // Single reusable client (transport-neutral baseline)
    private static final HttpClient CLIENT =
            HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

    // Reusable immutable request
    private static final HttpRequest REQUEST =
            HttpRequest.newBuilder()
                    .uri(URI.create(WAITING_URI))
                    .GET()
                    .build();

    @GetMapping
    public ResponseEntity<Void> getBlocking() throws Exception {

        //log.info("IO is called! Thread = {}", Thread.currentThread());

        HttpResponse<Void> response =
                CLIENT.send(REQUEST, HttpResponse.BodyHandlers.discarding());

        return response.statusCode() >= 200 && response.statusCode() < 300
                ? ResponseEntity.ok().build()
                : ResponseEntity.internalServerError().build();
    }
}
