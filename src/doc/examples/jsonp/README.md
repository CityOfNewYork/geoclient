Geoclient JSONP Example
-----------------------

This sample provides a minimal example of calling the geoclient service using [`JSONP`](https://en.wikipedia.org/wiki/JSONP).

The demo uses geoclient's RESTful [`address`](https://api.cityofnewyork.us/geoclient/v1/address.json) endpoint to geocode an address and use the returned DSNY attribute data to display collection schedule information.

This app is simply static site files that have been hard-coded to call the geoclient service instance hosted on the [NYC Developer Portal](https://developer.cityofnewyork.us/). 

Access to the service is free but requires that you [sign-up](https://developer.cityofnewyork.us/user/register?destination=node/182) and register an application (think named project) requesting app keys to access the `Geoclient API` service. 

> **IMPORTANT:** This sample *will not work* unless you have valid credentials to access a hosted Geoclient service!

### Build
  1. Install [Gradle](https://gradle.org)
  2. Run the Gradle with the appId and appKey properties defined
   a. Add valid id and key values to `gradle.properties`
   OR
   b. Define these properties on the command line:
```bash
$ gradle -PappId=<your_app_id> -PappKey=<your_app_key>
```

> See the Gradle [documentation](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_properties_and_system_properties) for more info about working with Gradle properties.

This will create
```
+---distributions
|       geoclient-examples-jsonp-1.0.0.zip
|
\---geoclient-examples-jsonp
    |   index.html
    |
    \---js
            geocode.js
```

Open the `build/geoclient-examples-jsonp/index.html` in your browser.

> **NOTE:** 
> If you don't want to install Gradle, simply replace the '`@appId@`' and '`@appKey@`' tokens found in the `src/main/webapp/index.html` file with valid values and then open this file in a browser.

> [City of New York / DoITT / GIS](https://maps.nyc.gov/).