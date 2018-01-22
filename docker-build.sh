#!/usr/bin/env bash

# Runs Geoclient build using Gradle's remote JVM debugging port
# docker run \
#   -it \
#   --volumes-from geosupport \
#   -v "$PWD":/home/gradle/geoclient \
#   --name geoclient mlipper/geoclient:latest \
#   "$@"

#  -w /home/gradle/geoclient \
#  -e GEOFILES=/opt/geosupport/fls/ \
#  -e GS_LIBRARY_PATH=/opt/geosupport/lib \
#  -e LD_LIBRARY_PATH=/opt/geosupport/lib:/home/gradle/geoclient/build/libs \

docker start -i geoclient

# gradle -Dorg.gradle.debug=true