#
# 1. Before running this image create a volume with Geosupport installed:
#
#   $ docker pull mlipper/geosupport-docker
#   ...
#   $ docker run --rm --mount source=gsvol,target=/opt/geosupport mlipper/geosupport-docker
#
#
# 2. Build this image
#
#   $ docker build -t geoclient .
#
# 3. Run this image
#
#   $ docker run -it --name geoclient \
#                -p 8080:8080 \
#               --mount source=gsvol,target=/opt/geosupport \
#                -v "$(pwd)":/app \
#                geoclient
#
FROM openjdk:8-jdk
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

ENV GEOSUPPORT_HOME /opt/geosupport
ENV JNI_JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64

RUN set -o errexit -o nounset \
  && echo "Validating Geosupport environment variables." \
  && apt-get update \
  && apt-get install -y --no-install-recommends \
      bash \
      gcc \
      g++ \
      libc6-dev \
      vim \
  && rm -rf /var/lib/apt/lists/*

ADD . /app

WORKDIR /app

RUN set -o errexit -o nounset \
  && cp /app/geoclient-service/setenv.sh $CATALINA_HOME/bin \
  && printf '%s:\n%s\n%s\n%s' \
  "Remember to run this image with --mount source=gsvol,target=/opt/geosupport" \
  ". $GEOSUPPORT_HOME/bin/initenv" \
  "./gradlew build" \
  "cp /app/geoclient-service/build/libs/*.war $CATALINA_HOME/webapps/geoclient.war" \
  "$CATALINA_HOME/bin/catalina.sh start"

EXPOSE 8080:8080

CMD ["/bin/bash"]

# Run geoclient as an HTTP-based REST service
#
##FROM tomcat:8.5
##
##ENV GEOSUPPORT_HOME /opt/geosupport
##
##RUN . $GEOSUPPORT_HOME/bin/initenv
##
##RUN set -o errexit -o nounset \
##  && echo "Validating Geosupport environment variables." \
##  && gsconf
##  && echo "Configuring Tomcat environment." \
##  && apt-get update \
##  && apt-get install -y --no-install-recommends \
##      vim \
##  && rm -rf /var/lib/apt/lists/*
##
##COPY --from=build /home/gradle/geoclient/geoclient-service/setenv.sh $CATALINA_HOME/bin/setenv.sh
##
##COPY --from=build /home/gradle/geoclient/geoclient-service/build/libs/*.war $CATALINA_HOME/webapps/geoclient.war
##
##EXPOSE 8088 8080
##
##CMD ["catalina.sh", "run"]
