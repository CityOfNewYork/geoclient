# Geoclient #

**Geoclient** is software which provides convenient API's for calling the Geosupport application. Geosupport
is the City of New York's official geocoder of record, written and maintained by the
Department of City Planning. Geoclient (this project) is maintained by the DoITT/Citywide GIS group.

### Start Here ###

* Geoclient provides local and remote client API's for calling the City's official geocoder. Geoclient is designed to simplify programmatic access to this geocoder from modern local and remote platforms

* Although Geoclient is already available to the public as a free RESTful service (sign up at the [New York City Developer Portal](https://developer.cityofnewyork.us)), by releasing the source code under the Apache 2.0 license, "power users" of the API can run the application on their own servers and not worry about resource limits

* Documentation is available for the currently deployed [Geoclient API v1](https://api.cityofnewyork.us/geoclient/v1/doc)

* In the coming weeks, we will be releasing version 2.0 of the Geoclient API

### Geosupport vs. Geoclient ###

For the past 30 years, the Department of City Planning (DCP) has created and maintained the **Geosupport** application. **Geosupport** is the City's official geocoder used by City agencies as the "lingua franca" for validation and standardization of New York City location data.

Until recently, Geosupport existed only on internal City mainframe systems and although it was possible to access the application there, developers of client applications running on non-mainframe systems needed to know detailed information about Geosupport's internal data structures in order to extract and parse returned location data.

In 2013, at the request of DoITT/Citywide GIS, the programmers at DCP compiled Geosupport as a native Linux C application. This allowed for the creation of **Geoclient** which uses a thin layer of C and JNI to provide a lightweight in-process Java library for calling Geosupport functions. Although Geoclient is primarily written in Java, this project also provides the code for running Geoclient as a platform-neutral web service which can be accessed through a simple REST API.

### Installation ###

* Concepts
* Platforms
* Runtimes
* Geosupport
* Deployment

### Documentation ###

* **WARNING** Current build using Gradle v2.10
* ```gradlew clean build```
* TODO

### Geosupport Documentation ###

* TODO

### Building ###

* Configuration
* Dependencies
* How to run tests
* Deployment

### Contributing ###

* Writing tests for new code
* Updating tests for exist code
* Platforms

### Support ###

* TBD