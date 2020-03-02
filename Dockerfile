# Builder
FROM openjdk:8-jdk-slim AS builder
ARG GEOSUPPORT_HOME
ENV GEOSUPPORT_HOME "${GEOSUPPORT_HOME:-/opt/geosupport}"
COPY --from=mlipper/geosupport-docker:1.0.8 $GEOSUPPORT_HOME $GEOSUPPORT_HOME
RUN set -ex \
  && apt-get update \
  && apt-get install -y --no-install-recommends \
      gcc \
      g++ \
      libc6-dev \
  && rm -rf /var/lib/apt/lists/*

COPY . /app
WORKDIR /app

ENV GC_VERSION 2.0.0-rc.4

RUN set -eux; \
    echo '#!/bin/bash' > /app/build.sh; \
    echo >> /app/build.sh; \
    cat $GEOSUPPORT_HOME/bin/initenv >> /app/build.sh; \
    echo "export GC_JNI_VERSION=geoclient-jni-${GC_VERSION}" >> /app/build.sh; \
    echo '/app/gradlew "$@"' >> /app/build.sh; \
    chmod 755 /app/build.sh

RUN /app/build.sh clean build bootJar

# Run
FROM openjdk:8-jdk-slim

ENV GEOSUPPORT_HOME "${GEOSUPPORT_HOME:-/opt/geosupport}"
ENV GC_VERSION 2.0.0-rc.5

COPY --from=mlipper/geosupport-docker:1.0.8 $GEOSUPPORT_HOME $GEOSUPPORT_HOME

WORKDIR /app

COPY --from=builder /app/geoclient-service/build/libs/geoclient-service-${GC_VERSION}-boot.jar /app/geoclient.jar
RUN set -eux; \
  [ -f /app/geoclient.jar ] || exit 1; \
  { \
    echo '#!/bin/bash'; \
    echo; \
    echo 'export GEOSUPPORT_LDCONFIG=true;'; \
    cat $GEOSUPPORT_HOME/bin/initenv; \
    echo; \
    echo "$JAVA_HOME/bin/java -Dspring.profiles.active=bootjar -Dgc.jni.version=$GC_JNI_VERSION -jar /app/geoclient.jar"; \
  } > /app/run.sh \
  && chmod 755 /app/run.sh \
  && cat /app/run.sh

EXPOSE 8080:8080

CMD ["/bin/bash","-c","/app/run.sh"]
