# Builder
FROM openjdk:8-jdk-slim AS builder

ARG GEOSUPPORT_HOME
ARG GC_VERSION

ENV GEOSUPPORT_HOME "${GEOSUPPORT_HOME:-/opt/geosupport}"
ENV GC_VERSION "${GC_VERSION:-2.0.0-rc.8}"

# Set version of mlipper/geosupport-docker image to copy from Docker Hub
COPY --from=mlipper/geosupport-docker:1.0.9 "${GEOSUPPORT_HOME}" "${GEOSUPPORT_HOME}/"

RUN set -ex \
  && apt-get update \
  && apt-get install -y --no-install-recommends \
      gcc \
      g++ \
      libc6-dev \
  && rm -rf /var/lib/apt/lists/*

COPY . /app

WORKDIR /app

RUN set -eux; \
    echo '#!/bin/bash' > /app/build.sh; \
    echo >> /app/build.sh; \
    cat "${GEOSUPPORT_HOME}/bin/initenv" >> /app/build.sh; \
    echo "export GC_JNI_VERSION=geoclient-jni-${GC_VERSION}" >> /app/build.sh; \
    echo '/app/gradlew "$@"' >> /app/build.sh; \
    chmod 755 /app/build.sh

RUN set -ex; \
    /app/build.sh clean build bootJar; \
    ls -l /app/geoclient-service/build/libs; \
    cp -v "/app/geoclient-service/build/libs/geoclient-service-${GC_VERSION}.jar" /app/geoclient.jar; \
    [ -f /app/geoclient.jar ] || exit 1

# Run
FROM openjdk:8-jdk-slim

ARG GEOSUPPORT_HOME
ARG GC_VERSION

ENV GEOSUPPORT_HOME "${GEOSUPPORT_HOME:-/opt/geosupport}"
ENV GC_VERSION "${GC_VERSION:-2.0.0-rc.8}"

RUN set -ex; \
    env | sort; \
    [ -n "$GEOSUPPORT_HOME" ] || exit 1

COPY --from=builder "$GEOSUPPORT_HOME" "$GEOSUPPORT_HOME/"
COPY --from=builder  /app/geoclient.jar /app/geoclient.jar

RUN set -eux; \
  { \
    echo '#!/bin/bash'; \
    echo; \
    cat "${GEOSUPPORT_HOME}/bin/initenv"; \
    echo; \
    echo "${JAVA_HOME}/bin/java -Dspring.profiles.active=bootRun -Dgc.jni.version=geoclient-jni-${GC_VERSION} -jar /app/geoclient.jar"; \
  } > /app/run.sh \
  && chmod 755 /app/run.sh

WORKDIR /app
EXPOSE 8080:8080

CMD ["/bin/bash","-c","/app/run.sh"]
