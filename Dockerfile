# syntax=docker/dockerfile:1

FROM gradle:jdk17 AS builder

ARG GC_VERSION
ENV GC_VERSION="${GC_VERSION:-2.0.1-SNAPSHOT}"

ENV GEOSUPPORT_BASEDIR=/opt/geosupport

RUN set -ex \
  && apt-get update \
  && apt-get install --yes --no-install-recommends \
     gcc \
     g++ \
     libc6-dev \
  && rm -rf /var/lib/apt/lists/*

COPY --from=mlipper/geosupport-docker:2.0.12-dist "/dist/geosupport.tgz" "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && tar xzvf "${GEOSUPPORT_BASEDIR}/geosupport.tgz" -C "${GEOSUPPORT_BASEDIR}" \
  && rm "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && SCRIPT=$(find ${GEOSUPPORT_BASEDIR}/version-*/bin/geosupport) \
  && [ -f "${SCRIPT}" ] || exit 1 \
  && /bin/bash -c set -eux && "${SCRIPT}" install

ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"
ENV GS_BIN_PATH="${GEOSUPPORT_BASEDIR}/current/bin"
ENV GS_LIBRARY_PATH="${GEOSUPPORT_BASEDIR}/current/lib"
ENV GS_INCLUDE_PATH="${GEOSUPPORT_BASEDIR}/current/include"

COPY . /app

WORKDIR /app

RUN set -ex \
    && gradle clean build bootJar \
    && cp -v "/app/geoclient-service/build/libs/geoclient-service-${GC_VERSION}.jar" /app/geoclient.jar

### Run
FROM eclipse-temurin:17-jdk-jammy AS runner

ARG GC_VERSION
ENV GC_VERSION="${GC_VERSION:-2.0.1-alpha}"

ARG GEOSUPPORT_BASEDIR
ENV GEOSUPPORT_BASEDIR="${GEOSUPPORT_BASEDIR:-/opt/geosupport}"

COPY --from=builder "${GEOSUPPORT_BASEDIR}" "${GEOSUPPORT_BASEDIR}"

ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

RUN set -eux \
  && "${GEOSUPPORT_HOME}/bin/geosupport" install

WORKDIR /app

COPY --from=builder /app/geoclient.jar /app/geoclient.jar

# TODO implement an optimization strategy from https://spring.io/guides/topicals/spring-boot-docker
# TODO use only '8080' and document use of '-p 8080' with docker run
EXPOSE 8080:8080

# TODO fix GC_VERSION not expanded by using script to 'exec java ...'
CMD ["java", "-Dgc.jni.version=geoclient-jni-${GC_VERSION}", "-jar", "/app/geoclient.jar"]