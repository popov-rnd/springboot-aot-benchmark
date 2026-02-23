# Spring Boot AOT&JIT Benchmarks

Benchmark project to compare JIT (JAR) vs AOT (native image) with Spring Boot.

We measure such *static* metrics:

- Startup time;
- Memory footprint;
- Artifact size;
- Compilation time (secondary).

We measure such *dynamic* metrics:

- *Closed-loop*: concurrency-throughput curves;
- *Open-loop*: p50-p99 latencies distributions;

Tested with Maven (wrapper), JVM/GraalVM 25+.

## JIT compilation

### Packaging fat .jar and running the app

In Spring Boot, an executable JAR is a single fat JAR that contains:

- Your compiled classes → BOOT-INF/classes/
- All dependencies → BOOT-INF/lib/ (including embedded Tomcat/Jetty/Undertow if it’s a web app)
- Spring Boot loader classes → org.springframework.boot.loader.*
- Manifest with Main-Class: JarLauncher and Start-Class: <your @SpringBootApplication>

More [Executable Jar](https://docs.spring.io/spring-boot/maven-plugin/packaging.html)

```shell script
./mvnw package
```

It is now runnable using:

`java -jar target/*-.jar`.

### Packaging into a Docker/OCI container

Build an OCI image via Buildpacks (recommended for most cases)

Spring Boot Maven plugin can build an OCI image directly from your jar using Cloud Native Buildpacks:

```
./mvnw spring-boot:build-image
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

- The Java Buildpack uses the ***BellSoft Liberica*** impl-s of the JRE and JDK. JVM installation is handled by the BellSoft Liberica Buildpack. The JDK will be installed in the build container but only the JRE will be contributed to the application image.

See more at: [docs](https://paketo.io/docs/reference/java-reference)


## AOT compilation

### Creating a native executable

There are two main ways to build a Spring Boot native image application:

- Using GraalVM Native Build Tools to generate a native executable.
- Using Spring Boot support for Cloud Native Buildpacks with the Paketo Java Native Image buildpack to generate a lightweight container containing a native executable.

This command requires GraalVM to be installed locally and set default.

```shell script
./mvnw -Pnative native:compile
```
You can then execute your native executable with:

`./target/spring-aot-benchmark`

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw -Pnative spring-boot:build-image
```

This will produce docker image in your local Docker registry

You can then run your docker image with:

`docker run -it spring-aot-benchmark:0.0.1-SNAPSHOT`

More [GraalVM Native image](https://docs.spring.io/spring-boot/how-to/native-image/developing-your-first-application.html)


## Running Benchmarks

Run both builds (.jar and native)

📊 Measure (*TODO*):

- Build time → taken from Maven’s own build output (e.g., [INFO] BUILD SUCCESS in _ s).
- Startup time → taken from Spring’s startup log (e.g., Spring Boot started in _ s).
- Memory usage → from ps after startup, Processes tab in Linux Mint’s System Monitor is essentially a GUI wrapper around what commands like ps and top show in the terminal.
- Artifact size → from ls -lh or direct filesystem metadata.