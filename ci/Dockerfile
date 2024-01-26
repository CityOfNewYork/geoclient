# syntax=docker/dockerfile:1

FROM gradle:jdk17 AS builder

ARG GC_VERSION
ENV GC_VERSION="${GC_VERSION:-2.0.1-beta}"

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
    && gradle clean build testMetrics

RUN set -eux \
  && [ -f "${JARFILE}" ] || exit 1 \
  && /bin/bash -c set -eux \
  && cp -v "${JARFILE}" ./geoclient.jar

RUN set -eux \
  && [ -d build/ci ] || exit 1 \
  && /bin/bash -c set -eux \
  && tar czvf ./geoclient-test-metrics.tgz -C build/ci .

#RUN set -ex \
#  && mkdir -p ./test-results \
#  && find . -path '*/build/*' \
#       -type f -regex '.*\/TEST-.*\.xml' \
#       -exec echo '{}' \; | xargs -n 1 -I{} bash -c 'FILE={} && cp -v -p $FILE ./test-results/$(basename $FILE)'
#
#RUN <<EOF
##!/usr/bin/env bash
#set -ex
#prj_name() {
#    local path="$1"
#    path="$(dirname $path)"
#    path="$(echo "$path" | sed 's|\./||')"
#    echo "${path%%/*}"
#}
#export -f prj_name
#mkdir -p ./coverage
#find . -path '*/build/*' \
#       -type f -regex '.*\/jacocoTestReport\.xml' \
#       -exec bash -c 'echo "{} ./coverage/jacocoTestReport-$(prj_name {}).xml"' \; | xargs -n 2 -I{} cp -v -p {} 2>/dev/null
#EOF

### Run
FROM eclipse-temurin:17-jre-jammy AS runner

ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

COPY --from=builder "${GEOSUPPORT_BASEDIR}" "${GEOSUPPORT_BASEDIR}"

RUN set -eux \
  && "${GEOSUPPORT_HOME}/bin/geosupport" install

WORKDIR /app

COPY --from=builder /app/geoclient.jar ./geoclient.jar
COPY --from=builder /app/geoclient-test-metrics.tgz ./geoclient-test-metrics.tgz

RUN set -ex \
  && tar xzvf ./geoclient-test-metrics.tgz

EXPOSE 8080

ENTRYPOINT ["java", "-Dgc.jni.version=geoclient-jni-2", "-Dspring.profiles.active=default", "-Xmx2048m", "-jar", "/app/geoclient.jar"]
