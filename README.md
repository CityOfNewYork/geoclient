# Geoclient #

**Geoclient** is software which provides developer-friendly API's for calling the *Geosupport* application. Geosupport
is the City of New York's official geocoder of record, written and maintained by the
Department of City Planning. **Geoclient** is maintained by the DoITT/Citywide GIS group.


* Geoclient provides local and remote client API's for calling the City's official geocoder. Geoclient is designed to simplify programmatic access to this geocoder from modern local and remote platforms

* Although Geoclient is already available to the public as a free RESTful service (sign up at the [New York City Developer Portal](https://developer.cityofnewyork.us)), by releasing the source code under the Apache 2.0 license, "power users" of the API can run the application on their own servers and not worry about resource limits

* In the coming weeks, we will be releasing version 2.0 of the Geoclient API. Development will take place, here, on GitHub.

* If you are a developer looking to build from source, please see the Building section  below.

* A new approach to generated documentation for Geoclient v2.0 is under active development. The most comprehensive project documentation is available for the currently deployed [Geoclient API v1](https://api.cityofnewyork.us/geoclient/v1/doc).


>**NOTE:** Before you read more about Geoclient, it is important to be aware of the difference between Geo*client* (this project) and Geo*support*. Geoclient is basically just a proxy API for accessing the Geosupport application which is the actual Geocoder and data repository. The former is like a JDBC/ODBC/DBI/etc. driver, whereas the latter is the database itself (*with application logic*, in this case).


### News ###

_2016/06/29_

The `master` branch has been updated with some important build fixes.

> **CAUTION:**
>
> The Geoclient v1.0 codebase is being actively re-organized for v2.0. Please consider the `dev` branch as __unstable and subject to rebase__.
> For now, think about this ancient saying before relying on `dev` downstream: _"Fork not lest ye be vexed!"_. (yes, we are naughty)

### Documentation ###

* [BetaNYC](http://betanyc.us/) [presentation](https://github.com/CityOfNewYork/geoclient/blob/master/src/doc/presentations/BetaNYC-nyc-doitt-geoclient.pdf)
* [Code4LibNyc](http://code4lib.org/) [presentation](https://github.com/CityOfNewYork/geoclient/blob/master/src/doc/presentations/Code4LibNYC-geoclient-overview.pdf)
* [GeoNYC](http://www.meetup.com/geonyc) [presentation](https://github.com/CityOfNewYork/geoclient/blob/master/src/doc/presentations/nyc-geoclient-api.pdf)
* [DoITT](http://www1.nyc.gov/site/doitt/index.page) [Press Release](http://on.nyc.gov/1ZnZwEX)

### Installation of the Geoclient Service ###

The geoclient-service subproject provides the code to run Geoclient in a Java Servlet container. If you are just looking to run Geoclient as a REST service on your own servers, this section describes the high-level concepts that you need to understand to run the provided binary distribution.

#### Concepts ####

The big picture:

![Geoclient components](https://github.com/CityOfNewYork/geoclient/blob/master/geoclient-service/doc/deployment-landscape.png)

Geoclient is written in Java and Geosupport is written in C. Java applications use the Java Native Interface (JNI) to call C applications.

* Before you run/build Geoclient, download and extract Geosupport binaries and C header files. 

* Platforms
  * Currently - Linux x86_64
  * Unsupported but working - Windows x86_64

### Install Geosupport ###

* Download the Linux version of Geosupport Desktop files [here](http://www.nyc.gov/html/dcp/html/bytes/applbyte.shtml#geocoding_application)

* *details in flux but coming shortly...*

### Building ###

**WARNING** Building Geoclient from source is tricky because of the many combinations of platforms, runtimes, tools, and pre-existing Geosupport binary artifacts. If you have a choice, prefer building/running on Linux as more of the standard C/JNI conventions work as expected. It is possible to build and run Geoclient/Geosupport on 64bit Windows, but we highly recommend Linux for the best performance.

* JDK 1.7 or 1.8 (Full JDK **required**, Oracle distribution recommended)
* gcc 4.+
* g++ also required for Gradle
* Gradle 2.x (2.14 recommended, pre-install not required if your build machine has Internet connectivity)

```bash

$ git clone https://github.com/CityOfNewYork/geoclient

$ cd geoclient

$ ./gradlew regenerate

```

#### Geoclient's compile, link and runtime requirements

Geoclient relies on [the Department of City Planning's Geosupport geocoder](http://www1.nyc.gov/site/planning/data-maps/open-data.page#geocoding_application) to do anything useful. On Linux, Windows, and (soon, hopefully) OSX, Geosupport is distributed as a handful of C shared libraries and proprietary data files.

Geoclient is written in Java and currently uses the JDK's mysterious [JNI](https://en.wikipedia.org/wiki/Java_Native_Interface) API to make "function calls" into Geosupport from a running JVM. At a high level, building Geoclient from source on any supported platform, requires that everyone in both C and Java-land know what's going on. 

**To build Geoclient from source**, it's necessary that:
>- the C compiler can find the header files for Geosupport, Geoclient, the JDK (Java), and the platform's standard C libs.
>- the linker can find the C libraries for Geosupport, Geoclient, the JDK and the standard C libs.
>- the Java compiler can find any Java components not defined by the Geoclient Java source itself (e.g. the CLASSPATH for external jar files).

**At runtime, Geosupport needs to know** where to find:
>- its own data files (GEOFILES)
>- its own C libraries (see above). 

**At runtime, the JVM must know** where to find:
>- Geoclient's classes and external class dependencies (see above)
>- it's own built-in classes (JRE 1.7 or 1.8)
>- the Geosupport and platform C libraries (see above).


#### Compiling the Geoclient C files
Geoclient contains a thin layer ("waaferr thin") of C that exists primarily to abstract the platform-specific naming conventions and differing runtime requirements Geosupport uses on each platform. Geoclient uses [Gradle](https://gradle.org) build, test and package both the C and Java code base.

While this will be increasingly awesome (hopefully) as Gradle evolves, it currently is a big, fat P.I.A. because, on Windows, linking against the latest versions of Geosupport requires Visual Studio 2015. At the moment, Gradle doesn't support VS 2015 (there is a [long-awaited patch stuck in the release queue](https://github.com/gradle/gradle/pull/500)) and it's likely that some TechNet research or ugly hard-coded path hack will work but we're using `mingw-w64-x86_64-gcc` from [MSYS2](http://msys2.github.io/) for now.
 
**The location of the Geosupport shared libraries.** 
Either of the following should work:
1. Set environment variable `GS_LIBRARY_PATH=<geosupport install>/lib`
2. Specify the `gsLibraryPath` property in the `gradle.properties` file in the base Geoclient source tree or on the command line as a Gradle project property `-PgsLibraryPath=<geosupport install>/lib`
  
**The location of Geosupport's required GEOFILES environment variable**
1. Set environment variable `GEOFILES=<geosupport install>/fls`
2. Specify the `gsGeofiles` property in the `gradle.properties` file in the base Geoclient source tree or on the command line as a Gradle project property `-PgsGeofiles=<geosupport install>/fls`

Note that Geosupport requires this environment variable be set at runtime and that on Windows it must end with a trailing file separator (`GS_LIBRARY_PATH=<geosupport install>/Fls/`). The Gradle build will export this environment variable automatically if it is not set _and_ `gsGeofiles` _is specified_ but this will only be visible to the forked JVM used when Gradle runs test tasks.) 

**The location of the correct Geosupport header files**
1. Set environment variable `GS_INCLUDE_PATH=<geosupport install>/foruser/include`
2. Specify the `gsIncludePath` property in the `gradle.properties` file in the base Geoclient source tree or on the command line as a Gradle project property `-PgsIncludePath=<geosupport install>/lib`

>TODO:
>- Table of config source, owner, platform, etc.
>- Manual adjustment of Geosupport headers
>- java.library.path
>- Hackety-hacks: LD_LIBRARY_PATH for Linux, PATH for Windows
>- MSYS2 binary requires PATH
>- Use of Gluegen to generate optimized JNI code
>- Geosupport installation tips
>- Deployment recipes: Tomcat Servlet context, CLI, etc.

### History ###

For the past 30 years, the Department of City Planning (DCP) has created and maintained the **Geosupport** application. **Geosupport** is the City's official geocoder used by City agencies as the "lingua franca" for validation and standardization of New York City location data.

Until recently, Geosupport existed only on internal City mainframe systems and although it was possible to access the application there, developers of client applications running on non-mainframe systems needed to know detailed information about Geosupport's internal data structures in order to extract and parse returned location data.

In 2013, at the request of DoITT/Citywide GIS, the programmers at DCP compiled Geosupport as a native Linux C application. This allowed for the creation of **Geoclient** which uses a thin layer of C and JNI to provide a lightweight in-process Java library for calling Geosupport functions. Although Geoclient is primarily written in Java, this project also provides the code for running Geoclient as a platform-neutral web service which can be accessed through a simple REST API.

### Geosupport Documentation ###

* The Department of City Planning's [GOAT](http://nyc.gov/goat) is an HTML-based front-end for calling Geosupport
* The [GOAT User Guide](http://nyc.gov/goat/userguide.aspx) is a good introductary overview to the different kinds of location data that's available from Geosupport

### Contributing ###

* The source is rife with FIXME and TODO comments. Real milestone and release plans coming soon...

### Support ###

* Found a bug (*..gasp..*)? Please [let us know](https://github.com/cityofnewyork/geoclient/issues)
* Unanswered technical questions? mlipper at doitt.nyc.gov 

### License ###

[Apache 2.0](https://github.com/CityOfNewYork/geoclient/blob/master/src/dist/license.txt)

