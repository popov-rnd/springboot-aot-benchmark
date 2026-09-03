# Load-Shedding Benchmark: downstream protection

This branch demonstrates how load shedding at a downstream dependency protects goodput and preserves server resources during overload.

The application under test does **not** enforce an application-level or web-server-level concurrency limit. Every request accepted by this application is forwarded to the downstream service. The downstream service owns the admission decision and rejects work it cannot serve.

## Request flow

```text
load generator
    |
    | GET /critical
    v
Spring Boot application (:8080)
    |
    | GET /delayed
    v
Downstream Go-based service (:8081)
```

The Spring Boot application maps a downstream `429 Too Many Requests` response to `503 Service Unavailable` for its caller. This makes shed requests visible as unavailable work rather than successful goodput.

## What the benchmark demonstrates

Under offered load above the downstream service's sustainable capacity, accepting every request causes queues to grow. That increases latency, retains more request state, consumes threads and connections, and can reduce the number of useful responses completed within their latency objective.

Downstream load shedding rejects excess work close to the constrained resource. The benchmark is intended to compare:

- **Goodput**: successful requests completed within the chosen latency objective.
- **Latency**: especially tail latency (p99) for admitted requests.
- **Resource usage**: CPU, memory, active requests, connections, and virtual-thread activity under sustained overload.
- **Allocation & GC activity**: allocation rate and GC pauses (cumulative during the test run).

Expected result: downstream shedding should protect the constrained dependency, but it is expected to protect the Spring application less effectively than application- or server-level admission control because rejected requests still consume ingress and outbound-client resources.

## Scope

This branch tests **downstream-level load shedding only**:

- The downstream service decides whether work is admitted or rejected.
- The application forwards `/critical` requests to that service.
- There is no application-level concurrency limiter around the controller or client call.
- There is no Tomcat/server-level load-shedding valve or request concurrency limit.

This distinction matters: the application may still spend resources accepting a request and making the outbound call before the downstream service rejects it. The benchmark therefore measures the protection provided by the dependency's admission control, not protection at the application ingress.

## Requirements

- JDK 25
- A downstream service listening on `127.0.0.1:8081`
- The downstream service must expose `GET /delay`, with an approximate service time of 200 ms
- To exercise shedding, the downstream service must return `429 Too Many Requests` when its concurrency or capacity limit is reached
- A load generator capable of driving `GET http://localhost:8080/critical` above the downstream service's sustainable rate

## Run

Start the downstream service first, then run this application.

## Build

### Packaging into a Docker/OCI container

Build an OCI image via Buildpacks (recommended for most cases)

Spring Boot Maven plugin can build an OCI image directly from your jar using Cloud Native Buildpacks:

```
./mvnw spring-boot:build-image \
  -Dspring-boot.build-image.imageName=spring-load-shedding:downstream \
  -Dspring-boot.build-image.environment.BP_NATIVE_IMAGE=false
```

This runs the Maven package lifecycle and produces an image without you writing a Dockerfile.

It produces a Docker/OCI image directly inside your local Docker daemon.

To build the image with *spring-boot:build-image*, you need Docker ***installed*** and ***running****.

Verify by running:

```
docker images
```

See more at [docs](https://docs.spring.io/spring-boot/maven-plugin/build-image.html)

*Paketo* explicitly states as default JVM provider:

- The Java Buildpack uses the ***BellSoft Liberica*** impl-s of the JRE and JDK. JVM installation is handled by the BellSoft Liberica Buildpack. The JDK will be installed in the build container, but only the JRE will be contributed to the application image.

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
  -e HTTP_CLIENT_CONNECT_TIMEOUT="100ms" \
  -e HTTP_CLIENT_READ_TIMEOUT="500ms" \
  -e LOG_LEVEL_BENCHMARK="ERROR" \
  "${IMAGE}"
```

## Exercise the systems under open-loop

An approximate k6 script used for the benchmark is available in this repository [branch](https://github.com/popov-rnd/scripted-benchmarks/tree/load-shedding).