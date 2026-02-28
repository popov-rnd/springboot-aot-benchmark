package com.popovrnd.springaotbenchmark.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("/io")
public class IOController {

    private static final Logger log =
            LoggerFactory.getLogger(IOController.class);

    // Reuse client (important for performance)
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final URI WAITING_URI = URI.create("http://127.0.0.1:8081/waiting");

    @GetMapping
    public ResponseEntity<Void> getBlocking() throws IOException, InterruptedException {

        //log.info("IO is called!");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(WAITING_URI)
                .GET()
                .build();

        HttpResponse<Void> response =
                CLIENT.send(request, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() != 200) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
