# Geoclient Development with Docker

*Assumptions*



## Docker Setup for `mlipper/geosupport-docker` Repository



## Build/Test with Gradle

  docker pull mlipper/geosupport-docker:18a_18.1
  docker run -d --name geosupport mlipper/geosupport-docker:18a_18.1 bash
  docker build -t mlipper/geoclient:snapshot -f Dockerfile.gradle .
  docker docker run -it --name geoclient-snapshot --volumes-from geosupport bash
  docker run -it --name geoclient-snapshot --volumes-from geosupport
  docker run -it --name geoclient-snapshot -v "$PWD":/home/gradle/geoclient --volumes-from geosupport mlipper/geoclient:snapshot bash
