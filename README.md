# Load-Shedding Benchmark: server protection

This branch demonstrates how server-level load shedding protects goodput and preserves server resources during overload.

The application enforces a server-level concurrency limit with Tomcat's `SemaphoreValve`. Every request admitted by the valve is forwarded to the downstream service, which additionally protects itself by rejecting work it cannot serve.

## Request flow

```text
load generator
    |
    | GET /critical
    v
Spring Boot application (:8080)
    |-- rejected by SemaphoreValve --> HTTP 503
    |
    | GET /delay
    v
Downstream Go-based service (:8081)
```

The `SemaphoreValve` is tuned to return the plain HTTP error code 503, rather than an HTML page with a corresponding message.


## What the benchmark demonstrates

Under offered load above the application's or downstream service's sustainable capacity, accepting every request causes queues to grow. That increases latency, retains more request state, consumes threads and connections, and can reduce the number of useful responses completed within their latency objective.

Server-level load shedding rejects excess work at the application ingress, while downstream load shedding protects the constrained dependency. The benchmark is intended to compare:

- **Goodput**: successful requests completed within the chosen latency objective.
- **Latency**: especially tail latency (p99) for admitted requests.
- **Resource usage**: CPU, memory, active requests, connections, and virtual-thread activity under sustained overload.
- **Allocation & GC activity**: allocation rate and GC pauses (cumulative during the test run).

The expected result is deliberate rejection once server-level or downstream capacity is exhausted, while admitted traffic continues to complete predictably and resource consumption remains bounded.

## Scope

This branch tests **server-level load shedding with additional downstream protection**:

- Tomcat's non-blocking `SemaphoreValve` admits requests up to the configured concurrency limit and rejects excess requests with `503 Service Unavailable`.
- The server-level concurrency target is configured by `TARGET_CONCURRENCY` (default: `1000`).
- The downstream service decides whether work is admitted or rejected.
- The application forwards `/critical` requests to that service.
- There is no application-level concurrency limiter around the controller or client call.

This distinction matters: the `SemaphoreValve` protects the application at server ingress, and the downstream service's admission control separately protects the dependency.

## Requirements

- JDK 25
- A downstream service listening on `127.0.0.1:8081`
- The downstream service must expose `GET /delay`, with an approximate service time of 200 ms
- To exercise shedding, the downstream service must return `429 Too Many Requests` when its concurrency or capacity limit is reached
- A load generator capable of driving `GET http://localhost:8080/critical` above the downstream service's sustainable rate

## Run

Start the downstream service first, then run this application:

## Build

### Packaging into a Docker/OCI container

Build an OCI image via Buildpacks (recommended for most cases)

Spring Boot Maven plugin can build an OCI image directly from your jar using Cloud Native Buildpacks:

```
./mvnw spring-boot:build-image \
  -Dspring-boot.build-image.imageName=spring-load-shedding:server \
  -Dspring-boot.build-image.environment.BP_NATIVE_IMAGE=false
```

This runs the Maven package lifecycle and produces an image without you writing a Dockerfile.

It produces a Docker/OCI image directly inside your local Docker daemon.

To build the image with *spring-boot:build-image*, you need Docker ***installed*** and ***running***.

Verify by running:

```
docker images
```

See more at [docs](https://docs.spring.io/spring-boot/maven-plugin/build-image.html)

*Paketo* explicitly states as default JVM provider:

- The Java Buildpack uses the ***BellSoft Liberica*** impl-s of the JRE and JDK. JVM installation is handled by the BellSoft Liberica Buildpack. The JDK will be installed in the build container but only the JRE will be contributed to the application image.

See more at: [docs](https://paketo.io/docs/reference/java-reference)


### Run docker image

Run the Spring Boot application in Docker:

```bash
docker run -d \
  --name spring-load-shedding \
  --network host \
  --cpuset-cpus="1,2" \
  --memory=2G \
  -e MAX_CONNECTIONS="${MAX_CONNECTIONS}" \
  -e TARGET_CONCURRENCY="${MAX_CONCURRENCY}" \
  -e HTTP_CLIENT_CONNECT_TIMEOUT="100ms" \
  -e HTTP_CLIENT_READ_TIMEOUT="500ms" \
  -e LOG_LEVEL_BENCHMARK="ERROR" \
  "${IMAGE}"
```

## Exercise the systems under open-loop

An approximate k6 script used for the benchmark is available in this repository [branch](https://github.com/popov-rnd/scripted-benchmarks/tree/load-shedding).