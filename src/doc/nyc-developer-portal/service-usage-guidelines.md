**Service Usage Guidelines**

The Geoclient API is a free geocoding service offered by the City of New York for use by the general public. Access to the service requires registering for a free account on [this website](https://developer.cityofnewyork.us/user/register?destination=node/182) and requesting an access key.

Due to rapidly increasing usage of Geoclient, we are seeing a commensurate decline in overall application availability based our current contractual resource allowance from third party platform services. While we work towards increasing this capacity, we are forced to set service usage guidelines.

From this point forward, the following service usage guidelines apply:

- Maximum of 2,500 requests per minute.
- Maximum of 500,000 requests per day.

Note that these are guidelines and not hard limits. Existing subscriptions will continue with their currently configured resource allocations on a provisional, case-by-case basis provided other accounts are not impacted. New sign-ups will automatically be provisioned with the resource plan above. If you believe your project requires additional bandwidth, you have two alternatives:

  1. Run the Geosupport application and Geoclient on your own server, workstation, etc.
  2. Contact us to discuss whether we can apply a mutually agreeable resource plan which does not unfairly restrict usage to other applications/accounts.

Please note that if we see applications continually exceeding these limits or impacting overall service availability, we will attempt to notify the account administrator based on contact information provided in the sign-up forms. If corrective action is not taken, we will then set whatever limits we deem necessary to provide equal resources averaged across all active accounts. Moving forward, as we increase capacity, we may adjust the usage guidelines.

**Geoclient Source Code**

The source code for the Geoclient API project was released under the Apache 2.0 license and is available for download on [GitHub](https://github.com/CityOfNewYork/geoclient). Since the Geoclient API relies on the local installation of the Department of City Planning's [Geosupport](http://a030-goat.nyc.gov/goat/Default.aspx) geocoder, the Geosupport application binaries and data need to be downloaded from the NYC Department of City Planning [website](http://www1.nyc.gov/site/planning/data-maps/open-data.page#geocoding_application) and configured for use with Geoclient.

For technical questions about using, installing, or contributing to the Geoclient project, please use our [GitHub Issues page](https://github.com/CityOfNewYork/geoclient/issues/).

**Geosupport Binaries**

Geoclient is not the only way to use the Department of City Planning's Geosupport application. The same Geosupport downloads [mentioned above](http://www1.nyc.gov/site/planning/data-maps/open-data.page#geocoding_application) include several pre-built executables and C/C++ programs on supported platforms can link directly with Geosupport's documented, lower-level, in-process API.

For questions about the Geosupport application's functionality, data and built-in or additional (non-Geoclient) client API's, please see the [User Guide](http://www1.nyc.gov/assets/planning/download/pdf/data-maps/open-data/upg.pdf) and [FAQ](http://www1.nyc.gov/assets/planning/download/pdf/data-maps/open-data/gdeguide.pdf) links provided on the Department of City Planning's [download page](http://www1.nyc.gov/site/planning/data-maps/open-data.page#geocoding_application). DCP also hosts an [interactive website](http://nyc.gov/goat) for calling Geosupport directly.
