# syntax=docker/dockerfile:1

FROM gradle:jdk17 AS builder

# Default for JARFILE assumes Docker context is the root project directory.
ARG JARFILE
ENV JARFILE="${JARFILE:-./geoclient-service/build/libs/geoclient.jar}"

ENV GEOSUPPORT_BASEDIR=/opt/geosupport

RUN set -ex \
  && apt-get update \
  && apt-get install --yes --no-install-recommends \
     gcc \
     g++ \
     libc6-dev \
  && rm -rf /var/lib/apt/lists/*

COPY --from=mlipper/geosupport-docker:latest-dist "/dist/geosupport.tgz" "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && tar xzvf "${GEOSUPPORT_BASEDIR}/geosupport.tgz" -C "${GEOSUPPORT_BASEDIR}" \
  && rm "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && SCRIPT=$(find ${GEOSUPPORT_BASEDIR}/version-*/bin/geosupport) \
  && [ -f "${SCRIPT}" ] || exit 1 \
  && /bin/bash -c set -eux \
  && "${SCRIPT}" install

WORKDIR /app
COPY . .

ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"
ENV GS_BIN_PATH="${GEOSUPPORT_BASEDIR}/current/bin"
ENV GS_LIBRARY_PATH="${GEOSUPPORT_BASEDIR}/current/lib"
ENV GS_INCLUDE_PATH="${GEOSUPPORT_BASEDIR}/current/include"

RUN set -ex \
    && gradle clean build

RUN set -ex \
  && [ -f "${JARFILE}" ] || exit 1 \
  && cp -v "${JARFILE}" ./geoclient.jar \
  && java -Djarmode=layertools -jar ./geoclient.jar extract

### Run
FROM eclipse-temurin:17-jre-jammy AS runner

ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

COPY --from=builder "${GEOSUPPORT_BASEDIR}" "${GEOSUPPORT_BASEDIR}"

RUN set -eux \
  && "${GEOSUPPORT_HOME}/bin/geosupport" install

WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

ENTRYPOINT ["java", "-Dgc.jni.version=geoclient-jni-2", "-Dspring.profiles.active=default", "-Xmx2048m", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080
