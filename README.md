# Spring Boot Load-Shedding demo

This repository is a small Spring Boot application for testing concurrency-based load shedding, with platform thread pools. It exposes several endpoints with concurrency limits at different layers.

## Load-shedding levels

- **Global (N<sub>G</sub>)**: the Tomcat executor itself limits total concurrent requests as thread pool size. The limit is configured by `GLOBAL_CONCURRENCY` (default: `200`). Requests above it are queued in the TaskQueue with min size (maxQueueSize = 1) and once full, related TCP connections are closed.
- **Local (N<sub>i</sub>), servlet filter**: `/path1`, `/path2`, and `/path3` use independent non-blocking semaphore filters. Their limits are `PATH1_CONCURRENCY=100`, `PATH2_CONCURRENCY=80`, and `PATH3_CONCURRENCY=20` by default. Excess requests receive HTTP `503`.
- **Local (N<sub>i</sub>), Spring `@ConcurrencyLimit`**: `/path4` uses `@ConcurrencyLimit(limit = 5, policy = REJECT)`. Invocations above the limit are mapped to HTTP `503`.

## Build and run

### Tested on

- Java 25
- Spring Boot 4.1.0

```
.\mvnw clean package
java -jar target\spring-benchmark-0.0.1-SNAPSHOT.jar
```

Or start it directly:

```
.\mvnw spring-boot:run
```

The application listens on `http://localhost:8080`. Endpoints `/path1`-`/path3` call a downstream service at `http://127.0.0.1:8081`, which must be running for successful responses.

## What to expect

Under the configured limits, requests complete normally (HTTP `200`, assuming the downstream service is available). When any applicable concurrency limit is exhausted, new work is shed immediately with HTTP `503 Service Unavailable` instead of being queued. Metrics are available at `/actuator/prometheus`.
