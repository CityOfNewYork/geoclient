#
# 1. Before running this image create a volume with Geosupport installed:
#
#   $ docker pull mlipper/geosupport-docker:latest-dvc
#   ...
#   $ docker run --name geosupport \
#               --mount source=vol-geosupport,target=/opt/geosupport \
#               mlipper/geosupport-docker:latest-dvc
#
#
# 2. Build this image
#
#   $ docker build -t geoclient .
#
# 3. Run this image
#
#   $ docker run -d \
#                --name geoclient \
#                -p 8088:8080 \
#               --mount source=vol-geosupport,target=/opt/geosupport \
#                -v "$(PWD)":/home/gradle/geoclient \
#                geoclient
#
FROM mlipper/geosupport-docker:latest-onbuild as build
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

VOLUME ["$GEOSUPPORT_HOME"]

RUN set -o errexit -o nounset \
    && apt-get update && apt-get install -y --no-install-recommends \
    gcc \
    g++ \
    libc6-dev \
    openjdk-8-jdk \
    vim \
    && rm -rf /var/lib/apt/lists/*

ADD . /app

WORKDIR /app

RUN ["/bin/bash", "-c", "./gradlew build"]

#
# Run geoclient as an HTTP-based REST service
#
FROM tomcat:8.5

COPY --from=build /app/geoclient-service/setenv.sh $CATALINA_HOME/bin/setenv.sh

COPY --from=build /app/geoclient-service/build/libs/*.war $CATALINA_HOME/webapps/geoclient.war

EXPOSE 8088 8080

CMD ["catalina.sh", "run"]
