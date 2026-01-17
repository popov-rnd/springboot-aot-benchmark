# Spring Boot AOT&JIT Benchmarks

Benchmark project to compare JIT (JAR) vs AOT (native image) with Spring Boot.

We measure:

- Startup time;
- Memory footprint (rss = shared + private);
- Artifact size;
- Compilation time (secondary).

Tested with Maven (wrapper), JVM: Temurin 25/GraalVM CE 25.

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

## AOT compilation

### Creating a native executable

There are two main ways to build a Spring Boot native image application:

- Using GraalVM Native Build Tools to generate a native executable.
- Using Spring Boot support for Cloud Native Buildpacks with the Paketo Java Native Image buildpack to generate a lightweight container containing a native executable.

#### GraalVM is installed in OS

This command requires GraalVM to be installed locally and set ***default***.

```shell script
./mvnw -Pnative native:compile
```

Prior to executing this, just make sure that:
```
./mvnw -v
```
Produces smth. like:

>Java version: 25, vendor: GraalVM

If Maven reports another than GraalVM JDK, the native build will not be valid.

You can then execute your native executable with:

`./target/spring-aot-benchmark`

#### GraalVM is NOT installed in OS

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw -Pnative spring-boot:build-image
```

This will produce docker image in your local Docker registry

You can then run your docker image with:

`docker run -it spring-aot-benchmark:0.0.1-SNAPSHOT`

More [GraalVM Native image](https://docs.spring.io/spring-boot/how-to/native-image/developing-your-first-application.html)


## Running Benchmarks

Metrics collected with the (*script*)[https://github.com/popov-rnd/script-aot-benchmark.git]

### Metrics

- **Startup time** -> taken from .sh script output after first http 200 response (not relying on, Spring Boot's logs started in _ s).
- **Memory usage** -> from standard RSS Unix tool, also in .sh script.
- **Artifact size** -> from ls -lh or direct filesystem metadata.
- ***Build time*** -> taken from Maven’s own build output (e.g., [INFO] BUILD SUCCESS in _ s).