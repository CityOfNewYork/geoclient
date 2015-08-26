=====================
BUILDING THIS PROJECT
=====================

Java
----
Use any of the standard Maven commands to clean, package, install and deploy 
this project's Maven artifact: 'target/geoclient-jni-${version}-${classifier}.jar'. 

NOTE: The C build no longer copies the generated Gluegen .java files to 
      src/main/java. When changing dependency versions or this project's 
      C API's, remember to diff the re-generated files:
          src/main/java/gov/nyc/doitt/gis/geoclient/jni/*.java
          src/main/c/generated/*.java

C on Windows
------------
NOTE: The Makefile is no longer hardcoded to execute Maven tasks. 
      Step 1. (below) _must_be_done_manually_ before running 'make'.

1. Run 'mvn clean package' from project root
2. cd src/main/c
3. make all  --> creates binaries: 'GeoclientImpl_JNI.dll' and 'ctest.exe'
4. make dist --> copies binaries to <project root>/dist/v${version}/mingw32-i686

C on Linux
------------
1. Run 'mvn clean package' from project root to create the assembly zip.
2. scp target/geoclient-jni-1.0.10-linux-x86_64-sources.zip dev.nycnet:~/mydir
3. ssh me@dev.nycnet
4. cd ~/mydir
5. make all  --> creates binaries: 'GeoclientImpl_JNI.so' and 'ctest'
6. make dist --> copies binaries to ~/mydir/dist/v${version}/linux-x86_64


XXXXXXXXXXXXXXXXXXXXX
WARNING: What follows below is out of date!!
XXXXXXXXXXXXXXXXXXXXX
===================
Windows Development - TODO: Update this with latest info
===================
Geosupport Desktop Edition("GDE" for this doc) is a native WIN32 application. 
Java/JNI can only call 32bit native applications from a 32bit JVM.

Setup
-----
1. Install GDE
   - http://www.nyc.gov/html/dcp/html/bytes/applbyte.shtml#geocoding_application
   - Default install is fine

2. Set environment variable GEOFILES="<GDE>\Fls\" if necessary
   - GDE install may have already set this variable
   - Trailing slash is required!

3. Add GDE binaries to PATH if necessary
   - GDE install may have already added these directories to the PATH
   - Add dir "<GDE>\Bin" to PATH so that .dll files will be found**
   - If you are compiling C code then also:
       * Add dir "<GDE>\Include\MSVC Library" to PATH so that NYCgeo.lib is found

4. Add geoclient-jni .dll to PATH
   - Checkout geoclient-jni
   - Add dir "<geoclient-jni>\src\main\c" to PATH so that GeoclientImpl_JNI.dll 
     is found

NOTE: 

* It doesn't matter which directories contain the .dll and .lib files as long 
  as the directories themselves are on the PATH
  
* Environment variable setup can also be done by using Eclipse Debug/Run 
  Configurations if you don't want to make changes to your global system 
  variables

* GDE default install: 
  - [Base dir]   C:\Program Files (x86)\Geosupport Desktop Edition
  - [GEOFILES]   C:\Program Files (x86)\Geosupport Desktop Edition\Fls
  - [.dll's]     C:\Program Files (x86)\Geosupport Desktop Edition\Bin
  - [NYCgeo.lib] C:\Program Files (x86)\Geosupport Desktop Edition\Include\MSVC Library

Example:

SET GEOCLIENT_DLL="D:\workspace\geoclient-jni\src\main\c"
SET GDE="C:\Program Files (x86)\Geosupport Desktop Edition"
SET GEOFILES="%GDE%\fls\"
SET JAVA_HOME="C:\jdk1.7.0_40-32bit"
SET PATH="%GDE%\Bin";"%GDE%\Include\MSVC Library";"%GEOCLIENT_DLL%";%PATH%

Java
----
* Use a 32bit JRE for running Geoclient apps on Windows.

* It is OK to use a 32bit JDK/javac to build geoclient artifacts for our 64bit 
  Citiserv platform. Java bytecode itself is platform independent.

Maven
------
Make sure Maven is run with a 32bit JDK so Geoclient integration tests do not
fail. For example, 

C:\> SET JAVA_HOME=<My jdk-1.7.x_32bit install>
C:\> mvn test

Eclipse
-------
* Configure project to use a 32bit JRE

* Configure WTP servers run with a 32bit JRE

* Configure Maven to run with a 32bit JRE
  
* When using system-level environment variables, remember to set them before 
  starting Eclipse so they are visible.

* You can also configure Eclipse Run/Debug launch profiles to include necessary 
  PATH and GEOFILES variables if you don't want to change Windows environment.

=============
C Development
=============

See src/main/c/Makefile in this project.
