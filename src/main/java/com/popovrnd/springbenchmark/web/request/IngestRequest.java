package com.popovrnd.springbenchmark.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record IngestRequest(

        @NotBlank
        String deviceId,

        @NotNull
        @Positive
        Long timestamp,

        @NotBlank
        String firmware,

        @NotNull
        @Valid
        Location location,

        @NotNull
        @Valid
        Metrics metrics,

        @NotNull
        @Valid
        Status status,

        @NotNull
        @Size(min = 1)
        List<@NotBlank String> tags

) {

        public record Location(
                @NotNull Double lat,
                @NotNull Double lon,
                @NotNull Double alt
        ) {}

        public record Metrics(
                @NotNull Double temperature,
                @NotNull Double humidity,
                @NotNull Double pressure,
                @NotNull Double vibration,
                @NotNull Double battery
        ) {}

        public record Status(
                @NotNull Boolean online,
                @NotNull Integer signalStrength,
                @NotNull Integer errorCode
        ) {}
}