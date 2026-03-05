package com.popovrnd.springaotbenchmark.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


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


    //private static final String WAITING_URI = "http://127.0.0.1:8081/waiting";

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

    /*@GetMapping
    public void getBlocking(HttpServletResponse response) {

        //log.info("IO is called! Thread = {}", Thread.currentThread());

        int status = CLIENT.get()
                .uri(WAITING_URI)
                .exchange((req, res) -> res.getStatusCode().value());

        response.setStatus(status);
    }*/

    /*private HttpClient client;

    private HttpRequest request;

    @PostConstruct
    void init() {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        request =
                HttpRequest.newBuilder()
                        .uri(URI.create( "http://127.0.0.1:8081/waiting"))
                        .GET()
                        .build();

    }

    @GetMapping
    public ResponseEntity<Void> getBlocking() throws Exception {

        //log.info("IO is called! Thread = {}", Thread.currentThread());

        HttpResponse<Void> response =
                client.send(request, HttpResponse.BodyHandlers.discarding());

        return response.statusCode() >= 200 && response.statusCode() < 300
                ? ResponseEntity.ok().build()
                : ResponseEntity.internalServerError().build();
    }*/
}
