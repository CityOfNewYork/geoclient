# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jre AS builder

# Default for JARFILE assumes Docker context is the root project directory.
ARG JARFILE
ENV JARFILE="${JARFILE:-./geoclient-service/build/libs/geoclient.jar}"

WORKDIR /app
COPY "${JARFILE}" .
COPY --chmod=755 images/run.sh .

RUN set -ex \
  && java -Djarmode=layertools -jar ./geoclient.jar extract

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder app/run.sh .
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

# Assumes a Geosupport installation has been mounted to /opt/geosupport.
ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

ENTRYPOINT ["/app/run.sh"]
EXPOSE 8080