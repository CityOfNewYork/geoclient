# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jre as builder

ARG GC_VERSION
ENV GC_VERSION=${GC_VERSION:-2.0.1-beta}
# Default for JARFILE assumes Docker context is the root project directory.
ARG JARFILE
ENV JARFILE=${JARFILE:-geoclient-service/build/libs/geoclient-service-${GC_VERSION}.jar}

WORKDIR /app
COPY --chmod=755 images/run.sh .
COPY "${JARFILE}" geoclient.jar
RUN java -Djarmode=layertools -jar geoclient.jar extract

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