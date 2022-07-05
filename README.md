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

![Geoclient components](src/doc/geoclient-runtime.png)

Geoclient is written in Java and Geosupport is written in C. Java applications use the Java Native Interface (JNI) to call C applications.

* Before you run/build Geoclient, download and extract Geosupport binaries and C header files.

* Platforms
  * Currently - Linux x86_64
  * Unsupported but working - Windows x86_64

### Install Geosupport ###

* Download the Linux version of Geosupport Desktop files [here](http://www.nyc.gov/html/dcp/html/bytes/applbyte.shtml#geocoding_application)

* *details in flux but coming shortly...*

### Building ###

> Building Geoclient from source is tricky because of the many combinations of platforms, runtimes, tools, and pre-existing Geosupport binary artifacts. If you have a choice, prefer building/running on Linux as more of the standard C/JNI conventions work as expected. It is possible to build and run Geoclient/Geosupport on 64bit Windows, but we highly recommend Linux for the best performance.

#### High-level Requirements
  * 64-bit Linux or Windows
  * 64-bit Geosupport 15+
  * 64-bit native compiler: gcc/g++ 4.3+
  * 64-bit JDK: 1.7 or 1.8 ("Full" JDK required. JRE by itself will *not* build)
  * Gradle 2.14+ (pre-install not required if your build machine has Internet connectivity)

```bash

$ git clone https://github.com/CityOfNewYork/geoclient

$ cd geoclient

$ ./gradlew build

```

See the [build documentation](./src/doc/BUILD.md) for real details.

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

