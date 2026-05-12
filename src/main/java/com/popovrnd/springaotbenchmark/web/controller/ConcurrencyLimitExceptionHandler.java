package com.popovrnd.springaotbenchmark.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.resilience.InvocationRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConcurrencyLimitExceptionHandler {

    @ExceptionHandler(InvocationRejectedException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void handleConcurrencyLimitExceeded(InvocationRejectedException ex) {
    }
}
