# syntax=docker/dockerfile:1

FROM gradle:jdk17 AS builder

ARG GC_VERSION
ENV GC_VERSION="${GC_VERSION:-2.0.0-rc.9}"

ARG GEOSUPPORT_FULLVERSION
ENV GEOSUPPORT_FULLVERSION="${GEOSUPPORT_FULLVERSION:-22c_22.3}"

ENV GEOSUPPORT_BASEDIR=/opt/geosupport

RUN set -ex \
  && apt-get update \
  && apt-get install --yes --no-install-recommends \
     gcc \
     g++ \
     libc6-dev \
  && rm -rf /var/lib/apt/lists/*

WORKDIR "${GEOSUPPORT_BASEDIR}"

COPY --from=mlipper/geosupport-docker:2.0.5-dist "/dist/geosupport-${GEOSUPPORT_FULLVERSION}.tgz" "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && tar xzvf "${GEOSUPPORT_BASEDIR}/geosupport.tgz" \
  && "${GEOSUPPORT_BASEDIR}/version-${GEOSUPPORT_FULLVERSION}/bin/geosupport" install \
  && rm "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

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
ENV GC_VERSION="${GC_VERSION:-2.0.0-rc.9}"

ARG GEOSUPPORT_FULLVERSION
ENV GEOSUPPORT_FULLVERSION="${GEOSUPPORT_FULLVERSION:-22c_22.3}"

ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

COPY --from=builder "${GEOSUPPORT_BASEDIR}" "${GEOSUPPORT_BASEDIR}"

RUN "${GEOSUPPORT_HOME}/bin/geosupport" install

WORKDIR /app

COPY --from=builder /app/geoclient.jar /app/geoclient.jar

EXPOSE 8080:8080

CMD ["java", "-Dgc.jni.version=geoclient-jni-${GC_VERSION}", "-jar", "/app/geoclient.jar"]