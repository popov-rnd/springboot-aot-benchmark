package com.popovrnd.springbenchmark.web.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.InvocationRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
public class ConcurrencyLimitExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimitExceptionHandler.class);

    @ExceptionHandler(InvocationRejectedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleConcurrencyLimitExceeded(InvocationRejectedException ex) {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleAllOtherExceptions(Exception exception) {

        log.warn("Unhandled request processing exception, message = {}", exception.getMessage());

        if (exception instanceof RestClientResponseException responseException) {
            return ResponseEntity
                    .status(responseException.getStatusCode())
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}