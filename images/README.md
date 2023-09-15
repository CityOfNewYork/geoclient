# Docker Images

Geoclient distributes several Docker images to support common use cases, all of which require a Geosupport installation to work. Furthermore, these images expect the patching and additional management features provided by the [geosupport-docker](https://github.com/mlipper/geosupport-docker) project (see [below](#about-geosupport-docker) for details).

* [full](#the-full-image) - Builds and runs geoclient.
* [lite](#the-lite-image) - Runs geoclient using the `geoclient-service-2.x.jar` and a locally available Docker `Volume` containing a Geosupport installation.

## The `full` image

This image installs Geosupport, builds the project from source using [Gradle](https://gradle.org/), runs Geoclient. Note that this image includes a complete Geosupport installation which is approximately 2.3 GB in size and results in a *very* large image/container.

## The `lite` image

Runs geoclient using the exploded contents of the [spring-boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)-packaged jar assumed to have been built by the `geoclient-service` subproject. Containers built from this image require that a Geosupport installation has been mounted to `/opt/geosupport` at runtime. E.g.,

```sh
docker run --rm -d --mount source=geosupport-23c_23.3,target=/opt/geosupport -p 8080:8080 mlipper/geoclient:latest-lite
```

## About geosupport-docker

The [geosupport-docker](https://github.com/mlipper/geosupport-docker) provides an opinionated, re-packaged version of the official Linux distribution of Geosupport available from New York City's [Department of City Planning](https://www.nyc.gov/site/planning/index.page). This project's Docker images depend upon `geosupport-docker`'s CLI scripts and patched `C` header files for installation, system configuration and native compilation tasks.

Although the `geoclient` runtime only requires that Geosupport's shared libraries are accessible to the `JVM` (via `ldconfig`, `java.library.path`, `LD_LIBRARY_PATH`, etc.) and that the `GEOFILES` environment variable is set to the absolute path (*with an appended '/' character*) of Geosupport's data files, use of `geosupport-docker`, greatly simplifies the effort of installing and managing Geosupport.
