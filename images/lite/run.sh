#!/usr/bin/env bash

set -eux

"${GEOSUPPORT_HOME}/bin/geosupport" install

exec java -Dgc.jni.version=geoclient-jni-2 \
  -Dspring.profiles.active=default \
  -Xmx2048m \
  org.springframework.boot.loader.JarLauncher

