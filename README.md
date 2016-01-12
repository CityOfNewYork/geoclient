# Geoclient #

**Geoclient** is software which provides developer-firendly API's for calling the *Geosupport* application. Geosupport
is the City of New York's official geocoder of record, written and maintained by the
Department of City Planning. **Geoclient** is maintained by the DoITT/Citywide GIS group.


* Geoclient provides local and remote client API's for calling the City's official geocoder. Geoclient is designed to simplify programmatic access to this geocoder from modern local and remote platforms

* Although Geoclient is already available to the public as a free RESTful service (sign up at the [New York City Developer Portal](https://developer.cityofnewyork.us)), by releasing the source code under the Apache 2.0 license, "power users" of the API can run the application on their own servers and not worry about resource limits

* In the coming weeks, we will be releasing version 2.0 of the Geoclient API. Development will take place, here, on GitHub.

* If you are a developer looking to build from source, please see the Building section  below.

* A new approach to generated documentation for Geoclient v2.0 is under active development. The most comprehensive project documentation is available for the currently deployed [Geoclient API v1](https://api.cityofnewyork.us/geoclient/v1/doc).


**NOTE:** Before you read more about Geoclient, it is important to be aware of the difference between Geo*client* (this project) and Geo*support*. Geoclient is basically just a proxy API for accessing the Geosupport application which is the actual Geocoder and data repository. The former is like a JDBC/ODBC/DBI/etc. driver, whereas the latter is the database itself (*with application logic*, in this case).

### Installation of the Geoclient Service ###

The geoclient-service subproject provides the code to run Geoclient in a Java Servlet container. If you are just looking to run Geoclient as a REST service on your own servers, this section describes the high-level concepts that you need to understand to run the provided binary distribution.

#### Concepts ####

The big picture:

![Geoclient components](https://github.com/CityOfNewYork/geoclient/blob/master/geoclient-service/doc/deployment-landscape.png)

Geoclient is written in Java and Geosupport is written in C. Java applications use the Java Native Interface (JNI) to call C applications.

* Platforms
  * Currently - Linux x86_64
  * Unsupported but working - Windows x86_64

### Building ###

**WARNING** The latest build is somewhat unstable but should be buildable on standard 64bit Linux systems. 

* Gradle v2.10
* JDK 1.7
* gcc 4.+
* g++ also required for Gradle

### History ###

For the past 30 years, the Department of City Planning (DCP) has created and maintained the **Geosupport** application. **Geosupport** is the City's official geocoder used by City agencies as the "lingua franca" for validation and standardization of New York City location data.

Until recently, Geosupport existed only on internal City mainframe systems and although it was possible to access the application there, developers of client applications running on non-mainframe systems needed to know detailed information about Geosupport's internal data structures in order to extract and parse returned location data.

In 2013, at the request of DoITT/Citywide GIS, the programmers at DCP compiled Geosupport as a native Linux C application. This allowed for the creation of **Geoclient** which uses a thin layer of C and JNI to provide a lightweight in-process Java library for calling Geosupport functions. Although Geoclient is primarily written in Java, this project also provides the code for running Geoclient as a platform-neutral web service which can be accessed through a simple REST API.

### Geosupport Documentation ###

* The Department of City Planning's [GOAT](http://nyc.gov/goat) is an HTML-based front-end for calling Geosupport
* The [GOAT User Guide](http://nyc.gov/goat/userguide.aspx) is a good introductary overview to the different kinds of location data that's available from Geosupport

### Contributing ###

* Writing tests for new code
* Updating tests for exist code
* Platforms

### Support ###

* TBD
