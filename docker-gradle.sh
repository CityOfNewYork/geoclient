#!/usr/bin/env bash

#
# TODO Replace with a Docker plugin for Gradle
#

USAGE=$(cat <<USAGE

Usage:
        $0 run [cmd]
    OR
        $0 debug [cmd]
    OR
        $0 start

USAGE
)

if [ "$#" == "0" ]; then
  echo "$USAGE"
  exit 1
fi

action=$1

shift

#
# Create geoclient container from mlipper/geoclient:latest images.
# Assumes a data volume container has been created called 'geosupport'.
#
function start {
  docker start \
    -i \
    geoclient
}

#
# Create geoclient container from mlipper/geoclient:latest images.
# Assumes a data volume container has been created called 'geosupport'.
#
function run {
  docker run \
    -it \
    --volumes-from geosupport \
    -v "$PWD":/home/gradle/geoclient \
    --name geoclient \
    mlipper/geoclient:latest \
    "$@"
}

function debug {
  docker run \
    --rm \
    -it \
    -p 5005:5005 \
    -e GRADLE_OPTS="-Dorg.gradle.debug=true" \
    --volumes-from geosupport \
    -v "$PWD":/home/gradle/geoclient \
    --name geoclient-debug \
    mlipper/geoclient:latest \
    ./gradlew "$@"
}

case $action in
  start)
    start "$@"
    ;;
  run)
    run "$@"
    ;;
  debug)
    debug "$@"
    ;;
  *) echo "Error: unrecogized action $action"
     exit 1
     ;;
esac
