# syntax=docker/dockerfile:1

FROM gradle:jdk17 AS builder

ARG GC_VERSION
ENV GC_VERSION="${GC_VERSION:-latest}"

# Default for JARFILE assumes Docker context is the root project directory.
ARG JARFILE
ENV JARFILE="${JARFILE:-./geoclient-service/build/libs/geoclient-service-${GC_VERSION}.jar}"

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
    && gradle clean build tarCiReports

RUN <<EOF
#!/usr/bin/env bash

set -eux -o pipefail
tmp_dir=$(mktemp -d)
mkdir -p ./dist
if ! [ -f "${JARFILE}" ]; then
  JARFILE=$(find ./geoclient-service/build/libs -type f \
      -not -name '*-javadoc.jar' \
      -a -not -name '*-sources.jar' \
      -a -not -name '*-plain.jar')
fi
[ -f "${JARFILE}" ] || exit 1
cp -v "${JARFILE}" ./dist/geoclient.jar
find . -path '**/build/*' -type f -name '*-ci-reports.tgz' | xargs -I% tar xzvf % -C "${tmp_dir}"
tar czvf ./dist/geoclient-ci-reports.tgz -C "${tmp_dir}" .
rm -rf "${tmp_dir}"
EOF

### Run
FROM eclipse-temurin:17-jre-jammy AS runner

ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

COPY --from=builder "${GEOSUPPORT_BASEDIR}" "${GEOSUPPORT_BASEDIR}"

RUN set -eux \
  && "${GEOSUPPORT_HOME}/bin/geosupport" install

WORKDIR /app

COPY --from=builder /app/dist/geoclient.jar ./geoclient.jar
COPY --from=builder /app/dist/geoclient-ci-reports.tgz ./geoclient-ci-reports.tgz

RUN set -ex \
  && mkdir -p ./ci \
  && tar xzvf ./geoclient-ci-reports.tgz -C ./ci \
  && rm -f ./geoclient-ci-reports.tgz

EXPOSE 8080

ENTRYPOINT ["java", "-Dgc.jni.version=geoclient-jni-2", "-Dspring.profiles.active=default", "-Xmx2048m", "-jar", "/app/geoclient.jar"]
