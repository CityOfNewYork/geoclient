# Docker

This section explains the two most common ways to build and run `geoclient` using Docker:

* [latest-build](#the-build-image): builds the project from source and then runs `geoclient-service`.
* [latest-run](#the-run-image): runs `geoclient-service` using the `geoclient.jar` built by the `geoclient-service` subproject and a local, named Docker `Volume` pre-populated with a recent Geosupport installation.

## Assumptions

* All examples assume you have a local clone of the `geoclient` GitHub [repository](https://github.com/mlipper/geoclient.git) and are running commands from the root project directory.

  See [Building Docker Images](#building-a-docker-image-from-source) below for details.
* Containers built from these images expect that Geosupport (`GEOSUPPORT_BASE`) is installed in `/opt/geosupport` at runtime.
* These examples start containers in the foregroung with the `-t` switch. To run `geoclient` in the background, use the `-d` switch instead.

## Requirements

* The `geoclient` REST service (`geoclient-service`) **requires** a Geosupport installation to work.
* The examples here require the Geosupport distribution packaged by the [geosupport-docker](https://github.com/mlipper/geosupport-docker) project:
  * Compiling the `geoclient-jni` C code requires patched header files.
  * The example `Dockerfile`s rely on the additional management and installation features that `geosupport-docker` provides.
  * See [below](#about-geosupport-docker) for more details about `geosupport-docker`.

> *NOTE: geoclient can use the official Linux distribution of Geosupport available from New York City's [Department of City Planning](https://www.nyc.gov/site/planning/index.page). However, additional installation and configuration steps are required.*

## Building a Docker image from source

Clone the `geoclient` from GitHub and change to the project's **root** directory.

  ```sh
  git clone https://github.com/mlipper/geoclient.git

  Cloning into 'geoclient'...
  remote: Enumerating objects: 11580, done.
  remote: Counting objects: 100% (2578/2578), done.
  remote: Compressing objects: 100% (904/904), done.
  remote: Total 11580 (delta 1020), reused 2463 (delta 972), pack-reused 9002
  Receiving objects: 100% (11580/11580), 10.27 MiB | 13.71 MiB/s, done.
  Resolving deltas: 100% (4942/4942), done.

  # Change to the root project directory.
  cd geoclient
  ```

## The `build` image

This image installs Geosupport, builds the project from source using [Gradle](https://gradle.org/), and runs geoclient.

A complete Geosupport installation is approximately 2.3 GB in size and adding it directly to the image results in both a *very* large image and container. In general, prefer adding Geosupport via a [named volume](#creating-a-named-geosupport-volume).

1. Build the image from the root of the project.

    ```sh
    docker build -t geoclient:latest-build -f images/build.Dockerfile .
    ```

1. Create and run a temporary container in the background, mapping the container port `8080` to your host port `8080`.

    ```sh
    docker run --rm -t -p 8080:8080 geoclient:latest-build
    ```

1. [Test the container](#testing-the-container).

## The `run` image

Runs geoclient using the exploded contents of the [spring-boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)-packaged jar built from the `geoclient-service` subproject.

1. Build the `run` image from the root of the project.

    ```sh
    docker build -t geoclient:latest-run -f images/run.Dockerfile .
    ```

   This assumes you are using the `geoclient.jar` jar artifact produced by the `geoclient/geoclient-service` subproject's Gradle `build` task. If the the default geoclient-service bootJar artifact `<root project>/geoclient-service/libs/geoclient.jar` is somewhere else, add a Docker build argument with the path to jar file:

    ```sh
    docker build --build-arg JARFILE=/path/to/geoclient-service.jar -t geoclient:latest-run -f images/run.Dockerfile .
    ```

1. Follow the steps [below](#creating-a-named-geosupport-volume) to create a local volume named `geosupport-latest`, pre-populated with the uncompressed Geosupport distribution.

1. Run a temporary container, mounting the local `geosupport-latest` volume into the container at `/opt/geosupport`.

    ```sh
    docker run --rm -t --mount source=geosupport-latest,target=/opt/geosupport -p 8080:8080 geoclient:latest-run
    ```

1. [Test the container](#testing-the-container).

## Creating a named Geosupport volume

Create a local, named volume containing a Geosupport installation, using the [geosupport-docker](https://github.com/mlipper/geosupport-docker) project. Use the default `GEOSUPPORT_BASE` path of `/opt/geosupport`.

   ```sh
   docker volume create geosupport-latest
   docker run --rm --mount source=geosupport-latest,target=/opt/geosupport mlipper/geosupport-docker:latest /bin/true
   ```

> *See the [`README`](https://github.com/mlipper/geosupport-docker/blob/main/README.md) for [geosupport-docker](#about-geosupport-docker) for a more detailed example.*

## Docker Compose

This section assumes you've followed the instructions in [The Run Image](#the-run-image) and [Creating a Named Geosupport Volume](#creating-a-named-geosupport-volume) above. The following objects should be available from your local Docker registry/installation:

* The `geoclient:latest-run` image.
* The `geosupport-latest` volume.

Note that the `GEOCLIENT_IMAGE` and `GEOSUPPORT_VOLUME` environment variables are defaulted to `geoclient:latest-run` and `geosupport-latest`, respectively by the `images/.env` file.

To start the service, run the following from the geoclient project root directory:

```sh
docker compose -f images/compose.yaml up
```

To shut down the service, run:

```sh
docker compose -f images/compose.yaml down
```

## Testing the container

Using the tool of your choice, geocode a NYC intersection. E.g., "east 53 street and 3rd avenue".

* Use `curl` from the command line

    ```sh
    curl 'http://localhost:8080/geoclient/v2/search?input=east%2053%20street%20and%203rd%20avenue'
    ```

* Or use a browser to open [this URL](http://localhost:8080/geoclient/v2/search?input=east%2053%20street%20and%203rd%20avenue).

## About geosupport-docker

The [geosupport-docker](https://github.com/mlipper/geosupport-docker) provides an opinionated, re-packaged version of the official Linux distribution of Geosupport available from New York City's [Department of City Planning](https://www.nyc.gov/site/planning/index.page). This project's Docker images depend upon `geosupport-docker`'s CLI scripts and patched `C` header files for installation, system configuration and native compilation tasks.

Although the `geoclient` runtime only requires that Geosupport's shared libraries are accessible to the `JVM` (via `ldconfig`, `java.library.path`, `LD_LIBRARY_PATH`, etc.) and that the `GEOFILES` environment variable is set to the absolute path (*with an appended '/' character*) of Geosupport's data files, use of `geosupport-docker`, greatly simplifies the effort of installing and managing Geosupport.
