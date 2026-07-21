package com.popovrnd.springbenchmark.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ConcurrencyLimitExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.TooManyRequests.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleDownstreamConcurrencyLimit(
            HttpClientErrorException.TooManyRequests exception) {
    }
}