#
# 1. Before running this image create a volume with Geosupport installed:
#
#   $ docker pull mlipper/geosupport-docker
#   ...
#   $ docker volume create vol-geosupport
#   ...
#   $ docker run --name geosupport -v vol-geosupport:/opt/geosupport
#
# 2. Build this image
#
#   $ docker build -t mlipper/geoclient .
#
# 3. Run this image
#
#   $ docker run -d \
#                --name geoclient \
#                -v vol-geosupport:/opt/geosupport \
#                -v "$(PWD)":/home/gradle/geoclient \
#                mlipper/geoclient:latest
#
FROM mlipper/geosupport-docker:latest-dvc as build
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

RUN set -o errexit -o nounset \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
    libc6-dev \
    gcc \
    g++ \
    vim \
    openjdk-8-jdk \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64

ADD . /app

WORKDIR /app

RUN ["/bin/bash", "-c", "./gradlew build"]

#
# Run geoclient as an HTTP-based REST service
#
FROM tomcat:8.5

COPY --from=build /app/setenv.sh $CATALINA_HOME/bin/setenv.sh

COPY --from=build /app/geoclient-service/build/libs/*.war $CATALINA_HOME/webapps/geoclient.war

EXPOSE 8088 8080

CMD ["catalina.sh", "run"]
