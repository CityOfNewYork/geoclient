#
# 1. Before running this image create a volume with Geosupport installed:
#
#   $ docker pull mlipper/geosupport-docker:latest
#   ...
#   $ docker run -d --name geosupport \
#                   --mount source=vol-geosupport,target=/opt/geosupport \
#                   mlipper/geosupport-docker
#
#
# 2. Build this image
#
#   # Use relative path to default geoclient-service build artifact
#   # ./geoclient-service/build/libs/geoclient-service-<VERSION>-boot.jar
#
#   $ docker build -t geoclient -f Dockerfile .
#
#   OR
#
#   # Specify a non-default path to the geoclient-service Spring Boot jar file
#
#   $ docker build --build-arg JARFILE=./build/libs/gc.jar \
#                  -t geoclient -f Dockerfile .
#
# 3. Run this image
#
#   $ docker run -it --name geoclient \
#                -p 8080:8080 \
#               --mount source=vol-geosupport,target=/opt/geosupport \
#                -v "$(pwd)":/app \
#                geoclient
#
FROM openjdk:8-jdk
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

ARG JARFILE
ENV JARFILE ${JARFILE:-"./geoclient-service/build/libs/*boot.jar"}

ARG GEOSUPPORT_HOME
ENV GEOSUPPORT_HOME ${GEOSUPPORT_HOME:-/opt/geosupport}

RUN set -o errexit -o nounset \
  && apt-get update \
  && apt-get install -y --no-install-recommends \
      bash \
      vim \
  && rm -rf /var/lib/apt/lists/*

ADD $JARFILE /app/geoclient.jar

WORKDIR /app

RUN set -o errexit -o nounset && . $GEOSUPPORT_HOME/bin/initenv

EXPOSE 8080:8080

CMD ["java", "-jar", "geoclient.jar"]
