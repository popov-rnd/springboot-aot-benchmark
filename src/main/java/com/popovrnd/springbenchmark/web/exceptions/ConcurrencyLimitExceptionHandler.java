package com.popovrnd.springbenchmark.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.resilience.InvocationRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConcurrencyLimitExceptionHandler {

    @ExceptionHandler(InvocationRejectedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleConcurrencyLimitExceeded(InvocationRejectedException ex) {
    }
}