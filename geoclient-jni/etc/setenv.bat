@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Windows setup for running against Desktop Geosupport
@rem
@rem ##########################################################################

set GDE_LIB=D:\dev\lib\gde\14A_14.1
set GEOCLIENT_LIB=D:\dev\lib\geoclient-jni\1.0.10-SNAPSHOT
set GEOFILES=D:\dev\lib\gde\14A_14.1\Fls\
set JAVA_HOME=C:\dev\lang\java\jdk1.7.0_40-32bit
set PATH=%GEOCLIENT_LIB%;%GDE_LIB%;%GEOFILES%;%PATH%
