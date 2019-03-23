#
# 1. Before running this image create a volume with Geosupport installed:
#
#   $ docker pull mlipper/geosupport-docker
#   ...
#   $ docker run -it --rm --mount source=vol-geosupport,target=/opt/geosupport mlipper/geosupport-docker 
#
# 2. Build this image
#
#   $ docker build -t geoclient -f Dockerfile.build .
#
# 3a. Run this image to run a clean Gradle build and then delete the container
#     once the build exits:
#
#   $ docker run -d --rm \
#                --mount source=vol-geosupport,target=/opt/geosupport \
#                -v "$(pwd):/app" \
#                geoclient
#
# OR
#
# 3b. Run this image interactively to invoke Gradle tasks from the 
#     container's shell (override this file's default CMD). Creates a container
#     named "gcbuild":
#
#   $ docker run -it --name gcbuild \
#                -p 8080:8080 \
#                -p 5005:5005 \
#                --mount source=vol-geosupport,target=/opt/geosupport \
#                -v "$(pwd):/app" \
#                geoclient \
#                /bin/bash
#
FROM openjdk:8-jdk
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

ENV GRADLE_DEBUG ${GRADLE_DEBUG:-""}
ENV GRADLE_OPTS "-Dorg.gradle.debug=${GRADLE_DEBUG}"
ENV GEOSUPPORT_HOME /opt/geosupport

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

WORKDIR /app

RUN ["/bin/bash", "-c", "echo \". $GEOSUPPORT_HOME/bin/initenv\" >> ~/.bashrc"]

EXPOSE 8080:8080
EXPOSE 5005:5005

CMD ["/app/gradlew", "-Dorg.gradle.debug=$GRADLE_DEBUG", "clean", "build"]
