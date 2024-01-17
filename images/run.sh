#!/usr/bin/env bash

set -eux

"${GEOSUPPORT_HOME}/bin/geosupport" install

exec java -Dspring.profiles.active=default \
  org.springframework.boot.loader.launch.JarLauncher

