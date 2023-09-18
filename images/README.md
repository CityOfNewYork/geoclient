# Docker Images

Geoclient distributes several Docker images to support common use cases, all of which require a Geosupport installation to work. Furthermore, these images expect the patching and additional management features provided by the [geosupport-docker](https://github.com/mlipper/geosupport-docker) project (see [below](#about-geosupport-docker) for details).

* [full](#the-full-image) - Builds and runs geoclient.
* [lite](#the-lite-image) - Runs geoclient using the `geoclient-service-2.x.jar` and a locally available Docker `Volume` containing a Geosupport installation.

## Building Docker images

The following shows the first steps to building all Geoclient Docker image flavors described on this page. All examples are shown using a `bash` shell but it should be straightforward to run these commands from other CLIs.

1. Clone the project from GitHub and insure you are at the **root** of the project.

    Checkout the project from GitHub.

    ```sh
    git clone https://github.com/mlipper/geoclient.git

    Cloning into 'geoclient'...
    remote: Enumerating objects: 11580, done.
    remote: Counting objects: 100% (2578/2578), done.
    remote: Compressing objects: 100% (904/904), done.
    remote: Total 11580 (delta 1020), reused 2463 (delta 972), pack-reused 9002
    Receiving objects: 100% (11580/11580), 10.27 MiB | 13.71 MiB/s, done.
    Resolving deltas: 100% (4942/4942), done.
    ```

    Change to the root project directory.

    ```sh
    cd geoclient
    ```

1. (OPTIONAL) Set the `GC_VERSION` environment variable to the version being built. This is optional but will reduce the chance of typos and the amount of typing necessary.

    ```sh
    export GC_VERSION='2.0.1-beta'
    ```

## The `full` image

This image installs Geosupport, builds the project from source using [Gradle](https://gradle.org/), runs Geoclient. Note that this image includes a complete Geosupport installation which is approximately 2.3 GB in size and results in a *very* large image/container.

*NOTE:* This example assumes you have a local clone of the Geoclient source and are running commands in `bash` from the root project directory. [See above](#building-docker-images) for details.

1. Build the image from the root of the project.

    ```sh
    docker build --build-arg GC_VERSION="${GC_VERSION}" -t "mlipper/geoclient:${GC_VERSION}" -f images/full/Dockerfile .
    ```

1. Create and run a temporary container in the background, mapping the container port `8080` to your host port `8080`.

    ```sh
    docker run --rm -d -p 8080:8080 mlipper/geoclient:${GC_VERSION}
    ```

1. Using the tool of your choice, geocode a NYC intersection. E.g., "east 53 street and 3rd avenue".

   * Use `curl` from the command line

    ```sh
    curl 'http://localhost:8080/geoclient/v2/search?input=east%2053%20street%20and%203rd%20avenue'
    ```

   * Or use a browser to open [this URL](http://localhost:8080/geoclient/v2/search?input=east%2053%20street%20and%203rd%20avenue).

## The `lite` image

Runs geoclient using the exploded contents of the [spring-boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)-packaged jar built from the `geoclient-service` subproject. Containers built from this image require that a Geosupport installation has been mounted to `/opt/geosupport` at runtime. E.g.,

*NOTE:* This example assumes you have a local clone of the Geoclient source and are running commands in `bash` from the root project directory. [See above](#building-docker-images) for details.

1. Build the `lite` image from the root of the project.

    ```sh
    docker build --build-arg GC_VERSION="${GC_VERSION}" -t "mlipper/geoclient:${GC_VERSION}-lite" -f images/lite/Dockerfile .
    ```

1. Create a local volume containing a Geosupport installation, using the default `GEOSUPPORT_BASE` path of `/opt/geosupport`. This example shows how to do this using the [geosupport-docker](https://github.com/mlipper/geosupport-docker) project.

   ```sh
   docker volume create geosupport-23c_23.3
   docker run --rm --mount source=geosupport-23c_23.3,target=/opt/geosupport  mlipper/geosupport-docker:2.0.12 /bin/true
   ```

1. Run a temporary container, mounting the local `geosupport-23c_23.3` volume into the container file system at `/opt/geosupport`.

    ```sh
    docker run --rm -d --mount source=geosupport-23c_23.3,target=/opt/geosupport -p 8080:8080 "mlipper/geoclient:${GC_VERSION}-lite"
    ```

## About geosupport-docker

The [geosupport-docker](https://github.com/mlipper/geosupport-docker) provides an opinionated, re-packaged version of the official Linux distribution of Geosupport available from New York City's [Department of City Planning](https://www.nyc.gov/site/planning/index.page). This project's Docker images depend upon `geosupport-docker`'s CLI scripts and patched `C` header files for installation, system configuration and native compilation tasks.

Although the `geoclient` runtime only requires that Geosupport's shared libraries are accessible to the `JVM` (via `ldconfig`, `java.library.path`, `LD_LIBRARY_PATH`, etc.) and that the `GEOFILES` environment variable is set to the absolute path (*with an appended '/' character*) of Geosupport's data files, use of `geosupport-docker`, greatly simplifies the effort of installing and managing Geosupport.
