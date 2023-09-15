# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jre-jammy AS runner

ARG GC_VERSION
ENV GC_VERSION="${GC_VERSION:-2.0.1-SNAPSHOT}"
ARG JARFILE
# Default value for the path to the jar file assumes that the
# Docker context is the root project directory.
ENV JARFILE="${JARFILE:-./geoclient-service/build/libs/geoclient-service-${GC_VERSION}.jar}"

WORKDIR /app

COPY ./BOOT-INF ./BOOT-INF/
COPY ./META-INF ./META-INF/
COPY ./org ./org/
COPY ./run.sh .

# Assumes a Geosupport installation has been mounted to /opt/geosupport.
ENV GEOSUPPORT_BASEDIR=/opt/geosupport
ENV GEOSUPPORT_HOME="${GEOSUPPORT_BASEDIR}/current"
ENV GEOFILES="${GEOSUPPORT_BASEDIR}/current/fls/"

ENTRYPOINT ["/app/run.sh"]

EXPOSE 8080

