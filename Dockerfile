FROM tomcat:8.5
LABEL maintainer "Matthew Lipper <mlipper@gmail.com>"

ENV LOCAL_JNI_LIB_DIR build/libs
ENV LOCAL_JNI_JAR_DIR geoclient-jni/build/dist
ENV LOCAL_APP_WAR_DIR geoclient-service/build/dist

COPY $LOCAL_JNI_LIB_DIR/*.so $CATALINA_HOME/shared/lib
COPY $LOCAL_JNI_JAR_DIR/*.jar $CATALINA_HOME/shared/lib

# Defaults used by mlipper/geosupport-docker
ENV GEOSUPPORT_HOME ${GEOSUPPORT_HOME:-/opt/geosupport}
ENV GS_LIBRARY_PATH ${GS_LIBRARY_PATH:-$GEOSUPPORT_HOME/lib}

EXPOSE 8888 8080

CMD ["catalina.sh", "run", "--java.library.path=$CATALINA_HOME/shared/lib:$GS_LIBRARY_PATH"]
