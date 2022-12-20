# Builder
FROM debian:bullseye-slim AS builder

ENV GEOSUPPORT_BASEDIR "${GEOSUPPORT_BASEDIR:-/opt/geosupport}"
ENV GEOSUPPORT_FULLVERSION "${GEOSUPPORT_FULLVERSION:-22a2_22.12}"

WORKDIR "${GEOSUPPORT_BASEDIR}"

COPY --from=geosupport-docker:latest-dist "/dist/geosupport-${GEOSUPPORT_FULLVERSION}.tgz" "${GEOSUPPORT_BASEDIR}/geosupport.tgz"

RUN set -eux \
  && tar xzvf "${GEOSUPPORT_BASEDIR}/geosupport.tgz" \
  && "${GEOSUPPORT_BASEDIR}/version-${GEOSUPPORT_FULLVERSION}/bin/geosupport" install \
  && rm "${GEOSUPPORT_BASEDIR}/geosupport.tgz" \
  && . "${GEOSUPPORT_BASEDIR}/current/bin/geosupport.env"

RUN set -ex \
  && apt-get update \
  && apt-get install -y --no-install-recommends \
     gcc \
     g++ \
     libc6-dev \
  && rm -rf /var/lib/apt/lists/*

COPY . /app

WORKDIR /app

ENV GC_VERSION "${GC_VERSION:-2.0.0-rc.9}"
ENV GC_JNI_VERSION "geoclient-jni-${GC_VERSION}"

RUN set -ex \
    && /app/gradlew clean build bootJar \
    && cp -v "/app/geoclient-service/build/libs/geoclient-service-${GC_VERSION}.jar" /app/geoclient.jar

# Run
FROM openjdk:11-jdk-slim-bullseye

ENV GEOSUPPORT_BASEDIR "${GEOSUPPORT_BASEDIR:-/opt/geosupport}"
ENV GEOSUPPORT_FULLVERSION "${GEOSUPPORT_FULLVERSION:-22a2_22.12}"

COPY --from=builder /opt/geosupport /opt/geosupport

RUN set -ex \
  && "${GEOSUPPORT_BASEDIR}/version-${GEOSUPPORT_FULLVERSION}/bin/geosupport" install \
  && . "${GEOSUPPORT_BASEDIR}/current/bin/geosupport.env"

WORKDIR /app

COPY --from=builder  /app/geoclient.jar /app/geoclient.jar

EXPOSE 8080:8080

ENV GC_VERSION "${GC_VERSION:-2.0.0-rc.9}"

CMD ["java", "-Dspring.profiles.active=bootRun", "-Dgc.jni.version=geoclient-jni-${GC_VERSION}", "-jar", "/app/geoclient.jar"]
