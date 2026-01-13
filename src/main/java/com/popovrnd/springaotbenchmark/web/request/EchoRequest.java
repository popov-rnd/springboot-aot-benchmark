package com.popovrnd.springaotbenchmark.web.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EchoRequest(

        @NotBlank
        String message,

        @NotNull
        @Min(1)
        Integer count,

        @NotBlank
        String source
) {}