# Geoclient API v1 (BETA)

The Geoclient application is a cloud based web service which exposes select
function calls to the New York City Department of City Planning's Geosupport
application. At the current time, there are six types of location requests 
that the Geoclient service provides:

| Type         | Description                                                                                           | Geosupport Function |
| ------------ | ----------------------------------------------------------------------------------------------------- | ------------------- |
| Address      | Given a valid address, provides blockface-level, property-level, and political information.           | 1B                  |
| BBL          | Given a valid borough, block, and lot provides property-level information.                            | BL                  |
| BIN          | Given a valid building identification number provides property-level information.                     | BN                  |
| Blockface    | Given a valid borough, "on street" and cross streets provides blockface-level information.            | 3                   |
| Intersection | Given a valid borough and cross streets returns information for the point defined by the two streets. | 2                   |
| Place        | Same as 'Address' above using well-known NYC place name for input.                                    | 1B                  |

## <a id="roadmap">Roadmap</a>

1. [**Section 1.0**](#section-1.0) titled "Calling the Geoclient API" documents
   how application client should call the API and what provides example
   responses.

2. [**Section 2.0**](#section-2.0) titled "Understanding Geoclient Return Codes"
   provides reference information for the HTTP and application-level return codes
   which are provided for all requests.

3. [**Section 3.0**](#section-3.0) titled "Geoclient Function Reference" details
   what data elements are input and output from each request type.
 
4. [**Section 4.0**](#section-4.0) titled "Geoclient Data Dictionary" provides
   more detailed reference information on available data items.

---

## <a id="section-1.0">1.0 - Calling the Geoclient API</a>

All requests are made as simple HTTP GET's with input arguments specified as 
URL query parameters. The service returns the JSON (application/json) or XML 
(application/xml) media types, depending on the requested file extension.

Note that although the XML and JSON example responses are "pretty-printed"
in this document, calling applications should not depend on any particular
formatting of responses. Furthermore, the ordering of returned data elements 
is unspecified. Client code which parses responses from Geoclient
_should not rely upon significant whitespace or element/attribute ordering_.

Also, even within the same service call types (Address, Intersection, etc...)
the set of returned data elements will vary since those elements with NULL values 
are not returned.
 
### <a id="section-1.1">1.1 - Common Request Parameters</a>
The following request parameters are required for all operations (except where
noted):

* URL file extension which specifies the desired media-type of the response: 
   * __format__ (required) -  `json`, `xml`

* API authorization parameters:
   * __app_id__ (required)  - assigned access application id
   * __app_key__ (required) - assigned access application key

* __borough__ (required<sup>1</sup>)  parameter - must be one of the following (case-insensitive):
   * `Manhattan`
   * `Bronx`
   * `Brooklyn`
   * `Queens`
   * `Staten Island`

<sup>1</sup>Not required for BIN calls

### <a id="section-1.2">1.2 - Request Parameters by Type</a>

The following sections describe how to call the Geoclient API. Required and
optional query parameters are listed for each type of supported call.
Although the actual Geosupport API can be called with a vast number of
optional parameters, at this time this service only supports what's listed
below.

*__Note:__* Example `GET` requests are shown unencoded (for example, with space 
characters instead of '%20'). When making these requests programmatically, 
you may need to URL-encode the URL if your tool or platform does not do so 
automatically. See this [article](https://en.wikipedia.org/wiki/Url_encoding)
for more details. 

### <a id="section-1.2.1">1.2.1 - Address</a>

**Path:** /v1/address.{format}

**Parameters:**

| Parameter Name | Required/Optional | Comments                                             |
| -------------- | ----------------- | ---------------------------------------------------- | 
| houseNumber    | required          | House number of the address.                         |
| street         | required          | Street name or 7-digit street code.                  | 
| borough        | required          | Valid values defined in [section 1.1](#section-1.1). |

**Example Requests:**

    /v1/address.json?houseNumber=314&street=west 100 st&borough=manhattan&app_id=abc123&app_key=def456
    /v1/address.xml?houseNumber=109-20&street=71st rd&borough=queens&app_id=abc123&app_key=def456
  
**Example JSON Response**

```json
{
  "address": {
    "assemblyDistrict": "69",
    "bbl": "1018887502",
    "bblBoroughCode": "1",
    "bblTaxBlock": "01888",
    "bblTaxLot": "7502",
    "boeLgcPointer": "1",
    "boePreferredStreetName": "WEST  100 STREET",
    "boePreferredstreetCode": "13577001",
    "boroughCode1In": "1",
    "buildingIdentificationNumber": "1057093",
    "censusBlock2000": "6000",
    "censusBlock2010": "2000",
    "censusTract1990": "187",
    "censusTract2000": "187",
    "censusTract2010": "187",
    "cityCouncilDistrict": "09",
    "civilCourtDistrict": "05",
    "coincidenceSegmentCount": "1",
    "communityDistrict": "107",
    "communityDistrictBoroughCode": "1",
    "communityDistrictNumber": "07",
    "communitySchoolDistrict": "03",
    "condominiumBillingBbl": "1018887502",
    "condominiumFlag": "C",
    "congressionalDistrict": "10",
    "cooperativeIdNumber": "0000",
    "crossStreetNamesFlagIn": "E",
    "dcpPreferredLgc": "01",
    "dofCondominiumIdentificationNumber": "1981",
    "dotStreetLightContractorArea": "1",
    "dynamicBlock": "601",
    "electionDistrict": "049",
    "fireBattalion": "11",
    "fireCompanyNumber": "022",
    "fireCompanyType": "L",
    "fireDivision": "03",
    "firstBoroughName": "MANHATTAN",
    "firstStreetCode": "13577001010",
    "firstStreetNameNormalized": "WEST  100 STREET",
    "fromLionNodeId": "0023422",
    "fromPreferredLgcsFirstSetOf5": "01",
    "genericId": "0060351",
    "geosupportFunctionCode": "1B",
    "geosupportReturnCode": "00",
    "geosupportReturnCode2": "00",
    "gi5DigitStreetCode1": "35770",
    "giBoroughCode1": "1",
    "giBuildingIdentificationNumber1": "1057093",
    "giDcpPreferredLgc1": "01",
    "giHighHouseNumber1": "316",
    "giLowHouseNumber1": "314",
    "giSideOfStreetIndicator1": "L",
    "giStreetCode1": "13577001",
    "giStreetName1": "WEST  100 STREET",
    "healthArea": "3110",
    "healthCenterDistrict": "16",
    "highBblOfThisBuildingsCondominiumUnits": "1018881233",
    "highCrossStreetB5SC1": "129690",
    "highCrossStreetCode1": "12969001",
    "highCrossStreetName1": "RIVERSIDE DRIVE",
    "highHouseNumberOfBlockfaceSortFormat": "000398000AA",
    "houseNumber": "314",
    "houseNumberIn": "314",
    "houseNumberSortFormat": "000314000AA",
    "interimAssistanceEligibilityIndicator": "I",
    "internalLabelXCoordinate": "0991892",
    "internalLabelYCoordinate": "0230017",
    "legacySegmentId": "0037349",
    "lionKeyBoroughCode": "1",
    "lionKeyFaceCode": "5345",
    "lionKeyForVanityAddressBoroughCode": "1",
    "lionKeyForVanityAddressFaceCode": "5345",
    "lionKeyForVanityAddressSequenceNumber": "00060",
    "lionKeySequenceNumber": "00060",
    "listOf4Lgcs": "01",
    "lowBblOfThisBuildingsCondominiumUnits": "1018881201",
    "lowCrossStreetB5SC1": "144990",
    "lowCrossStreetCode1": "14499001",
    "lowCrossStreetName1": "WEST END AVENUE",
    "lowHouseNumberOfBlockfaceSortFormat": "000300000AA",
    "lowHouseNumberOfDefiningAddressRange": "000314000AA",
    "nta": "MN12",
    "ntaName": "Upper West Side",
    "numberOfCrossStreetB5SCsHighAddressEnd": "1",
    "numberOfCrossStreetB5SCsLowAddressEnd": "1",
    "numberOfCrossStreetsHighAddressEnd": "1",
    "numberOfCrossStreetsLowAddressEnd": "1",
    "numberOfEntriesInListOfGeographicIdentifiers": "0001",
    "numberOfExistingStructuresOnLot": "0001",
    "numberOfStreetFrontagesOfLot": "01",
    "physicalId": "0069454",
    "policePatrolBoroughCommand": "2",
    "policePrecinct": "024",
    "returnCode1a": "00",
    "returnCode1e": "00",
    "roadwayType": "1",
    "rpadBuildingClassificationCode": "R4",
    "rpadSelfCheckCodeForBbl": "5",
    "sanbornBoroughCode": "1",
    "sanbornPageNumber": "034",
    "sanbornVolumeNumber": "07",
    "sanbornVolumeNumberSuffix": "S",
    "sanitationCollectionSchedulingSectionAndSubsection": "5B",
    "sanitationDistrict": "107",
    "sanitationRecyclingCollectionSchedule": "ET",
    "sanitationRegularCollectionSchedule": "TTHS",
    "sanitationSnowPriorityCode": "P",
    "segmentAzimuth": "151",
    "segmentIdentifier": "0037349",
    "segmentLengthInFeet": "00574",
    "segmentOrientation": "W",
    "segmentTypeCode": "U",
    "selfCheckCodeOfBillingBbl": "5",
    "sideOfStreetIndicator": "L",
    "sideOfStreetOfVanityAddress": "L",
    "splitLowHouseNumber": "000300000AA",
    "stateSenatorialDistrict": "31",
    "streetName1In": "west 100 st",
    "streetStatus": "2",
    "taxMapNumberSectionAndVolume": "10703",
    "toLionNodeId": "0023852",
    "toPreferredLgcsFirstSetOf5": "01",
    "trafficDirection": "A",
    "underlyingstreetCode": "13577001",
    "workAreaFormatIndicatorIn": "C",
    "xCoordinate": "0992059",
    "xCoordinateHighAddressEnd": "0991683",
    "xCoordinateLowAddressEnd": "0992186",
    "xCoordinateOfCenterofCurvature": "0000000",
    "yCoordinate": "0230011",
    "yCoordinateHighAddressEnd": "0230221",
    "yCoordinateLowAddressEnd": "0229944",
    "yCoordinateOfCenterofCurvature": "0000000",
    "zipCode": "10025"
  }
}
```

**Example XML Response**

```xml
<geosupportResponse>
  <address>
    <assemblyDistrict>28</assemblyDistrict>
    <attributeBytes>H</attributeBytes>
    <bbl>4022250006</bbl>
    <bblBoroughCode>4</bblBoroughCode>
    <bblTaxBlock>02225</bblTaxBlock>
    <bblTaxLot>0006</bblTaxLot>
    <boeLgcPointer>1</boeLgcPointer>
    <boePreferredStreetName>71 ROAD</boePreferredStreetName>
    <boePreferredstreetCode>41504001</boePreferredstreetCode>
    <boroughCode1In>4</boroughCode1In>
    <buildingIdentificationNumber>4052426</buildingIdentificationNumber>
    <censusBlock2000>4005</censusBlock2000>
    <censusBlock2010>5001</censusBlock2010>
    <censusTract1990>739</censusTract1990>
    <censusTract2000>739</censusTract2000>
    <censusTract2010>739</censusTract2010>
    <cityCouncilDistrict>29</cityCouncilDistrict>
    <civilCourtDistrict>04</civilCourtDistrict>
    <coincidenceSegmentCount>1</coincidenceSegmentCount>
    <communityDistrict>406</communityDistrict>
    <communityDistrictBoroughCode>4</communityDistrictBoroughCode>
    <communityDistrictNumber>06</communityDistrictNumber>
    <communitySchoolDistrict>28</communitySchoolDistrict>
    <condominiumBillingBbl>0000000000</condominiumBillingBbl>
    <congressionalDistrict>06</congressionalDistrict>
    <cooperativeIdNumber>0000</cooperativeIdNumber>
    <cornerCode>SE</cornerCode>
    <crossStreetNamesFlagIn>E</crossStreetNamesFlagIn>
    <dcpPreferredLgc>01</dcpPreferredLgc>
    <dotStreetLightContractorArea>4</dotStreetLightContractorArea>
    <dynamicBlock>410</dynamicBlock>
    <electionDistrict>029</electionDistrict>
    <fireBattalion>50</fireBattalion>
    <fireCompanyNumber>151</fireCompanyNumber>
    <fireCompanyType>L</fireCompanyType>
    <fireDivision>13</fireDivision>
    <firstBoroughName>QUEENS</firstBoroughName>
    <firstStreetCode>41504001010</firstStreetCode>
    <firstStreetNameNormalized>71 ROAD</firstStreetNameNormalized>
    <fromLionNodeId>0067082</fromLionNodeId>
    <genericId>0070010</genericId>
    <geosupportFunctionCode>1B</geosupportFunctionCode>
    <geosupportReturnCode>00</geosupportReturnCode>
    <geosupportReturnCode2>00</geosupportReturnCode2>
    <gi5DigitStreetCode1>15040</gi5DigitStreetCode1>
    <gi5DigitStreetCode2>59990</gi5DigitStreetCode2>
    <giBoroughCode1>4</giBoroughCode1>
    <giBoroughCode2>4</giBoroughCode2>
    <giBuildingIdentificationNumber1>4052426</giBuildingIdentificationNumber1>
    <giBuildingIdentificationNumber2>4052426</giBuildingIdentificationNumber2>
    <giDcpPreferredLgc1>01</giDcpPreferredLgc1>
    <giDcpPreferredLgc2>01</giDcpPreferredLgc2>
    <giHighHouseNumber1>109-20</giHighHouseNumber1>
    <giHighHouseNumber2>109-05</giHighHouseNumber2>
    <giLowHouseNumber1>109-06</giLowHouseNumber1>
    <giLowHouseNumber2>109-05</giLowHouseNumber2>
    <giSideOfStreetIndicator1>R</giSideOfStreetIndicator1>
    <giSideOfStreetIndicator2>L</giSideOfStreetIndicator2>
    <giStreetCode1>41504001</giStreetCode1>
    <giStreetCode2>45999001</giStreetCode2>
    <giStreetName1>71 ROAD</giStreetName1>
    <giStreetName2>QUEENS BOULEVARD</giStreetName2>
    <healthArea>1920</healthArea>
    <healthCenterDistrict>46</healthCenterDistrict>
    <highBblOfThisBuildingsCondominiumUnits>4022250006</highBblOfThisBuildingsCondominiumUnits>
    <highCrossStreetB5SC1>420390</highCrossStreetB5SC1>
    <highCrossStreetCode1>42039001</highCrossStreetCode1>
    <highCrossStreetName1>110 STREET</highCrossStreetName1>
    <highHouseNumberOfBlockfaceSortFormat>100109098AA</highHouseNumberOfBlockfaceSortFormat>
    <houseNumber>109-20</houseNumber>
    <houseNumberIn>109-20</houseNumberIn>
    <houseNumberSortFormat>100109020AA</houseNumberSortFormat>
    <interimAssistanceEligibilityIndicator>I</interimAssistanceEligibilityIndicator>
    <internalLabelXCoordinate>1028016</internalLabelXCoordinate>
    <internalLabelYCoordinate>0202046</internalLabelYCoordinate>
    <legacySegmentId>0113813</legacySegmentId>
    <lionKeyBoroughCode>4</lionKeyBoroughCode>
    <lionKeyFaceCode>5820</lionKeyFaceCode>
    <lionKeyForVanityAddressBoroughCode>4</lionKeyForVanityAddressBoroughCode>
    <lionKeyForVanityAddressFaceCode>5820</lionKeyForVanityAddressFaceCode>
    <lionKeyForVanityAddressSequenceNumber>02020</lionKeyForVanityAddressSequenceNumber>
    <lionKeySequenceNumber>02020</lionKeySequenceNumber>
    <listOf4Lgcs>01</listOf4Lgcs>
    <lowBblOfThisBuildingsCondominiumUnits>4022250006</lowBblOfThisBuildingsCondominiumUnits>
    <lowCrossStreetB5SC1>435780</lowCrossStreetB5SC1>
    <lowCrossStreetCode1>435780</lowCrossStreetCode1>
    <lowCrossStreetName1>BEND</lowCrossStreetName1>
    <lowHouseNumberOfBlockfaceSortFormat>100109000AA</lowHouseNumberOfBlockfaceSortFormat>
    <lowHouseNumberOfDefiningAddressRange>100109006AA</lowHouseNumberOfDefiningAddressRange>
    <nta>QN17</nta>
    <ntaName>Forest Hills</ntaName>
    <numberOfCrossStreetB5SCsHighAddressEnd>1</numberOfCrossStreetB5SCsHighAddressEnd>
    <numberOfCrossStreetB5SCsLowAddressEnd>1</numberOfCrossStreetB5SCsLowAddressEnd>
    <numberOfCrossStreetsHighAddressEnd>1</numberOfCrossStreetsHighAddressEnd>
    <numberOfCrossStreetsLowAddressEnd>1</numberOfCrossStreetsLowAddressEnd>
    <numberOfEntriesInListOfGeographicIdentifiers>0002</numberOfEntriesInListOfGeographicIdentifiers>
    <numberOfExistingStructuresOnLot>0001</numberOfExistingStructuresOnLot>
    <numberOfStreetFrontagesOfLot>02</numberOfStreetFrontagesOfLot>
    <physicalId>0080824</physicalId>
    <policePatrolBoroughCommand>6</policePatrolBoroughCommand>
    <policePrecinct>112</policePrecinct>
    <returnCode1a>00</returnCode1a>
    <returnCode1e>00</returnCode1e>
    <roadwayType>1</roadwayType>
    <rpadBuildingClassificationCode>D1</rpadBuildingClassificationCode>
    <rpadSelfCheckCodeForBbl>8</rpadSelfCheckCodeForBbl>
    <sanbornBoroughCode>4</sanbornBoroughCode>
    <sanbornPageNumber>066</sanbornPageNumber>
    <sanbornVolumeNumber>19</sanbornVolumeNumber>
    <sanitationCollectionSchedulingSectionAndSubsection>1E</sanitationCollectionSchedulingSectionAndSubsection>
    <sanitationDistrict>406</sanitationDistrict>
    <sanitationRecyclingCollectionSchedule>EW</sanitationRecyclingCollectionSchedule>
    <sanitationRegularCollectionSchedule>WS</sanitationRegularCollectionSchedule>
    <sanitationSnowPriorityCode>P</sanitationSnowPriorityCode>
    <segmentAzimuth>021</segmentAzimuth>
    <segmentIdentifier>0113813</segmentIdentifier>
    <segmentLengthInFeet>00455</segmentLengthInFeet>
    <segmentOrientation>1</segmentOrientation>
    <segmentTypeCode>U</segmentTypeCode>
    <sideOfStreetIndicator>R</sideOfStreetIndicator>
    <sideOfStreetOfVanityAddress>R</sideOfStreetOfVanityAddress>
    <splitLowHouseNumber>100109000AA</splitLowHouseNumber>
    <stateSenatorialDistrict>16</stateSenatorialDistrict>
    <streetAttributeIndicator>H</streetAttributeIndicator>
    <streetName1In>71st rd</streetName1In>
    <streetStatus>2</streetStatus>
    <taxMapNumberSectionAndVolume>41204</taxMapNumberSectionAndVolume>
    <toLionNodeId>0050496</toLionNodeId>
    <toPreferredLgcsFirstSetOf5>01</toPreferredLgcsFirstSetOf5>
    <trafficDirection>A</trafficDirection>
    <underlyingstreetCode>41504001</underlyingstreetCode>
    <workAreaFormatIndicatorIn>C</workAreaFormatIndicatorIn>
    <xCoordinate>1027969</xCoordinate>
    <xCoordinateHighAddressEnd>1028268</xCoordinateHighAddressEnd>
    <xCoordinateLowAddressEnd>1027844</xCoordinateLowAddressEnd>
    <xCoordinateOfCenterofCurvature>0000000</xCoordinateOfCenterofCurvature>
    <yCoordinate>0202112</yCoordinate>
    <yCoordinateHighAddressEnd>0202233</yCoordinateHighAddressEnd>
    <yCoordinateLowAddressEnd>0202066</yCoordinateLowAddressEnd>
    <yCoordinateOfCenterofCurvature>0000000</yCoordinateOfCenterofCurvature>
    <zipCode>11375</zipCode>
  </address>
</geosupportResponse>
```

### <a id="section-1.2.2">1.2.2 - BBL</a>

**Path:** /v1/bbl.{format}

**Parameters:**

| Parameter Name | Required/Optional | Comments                                  |
| -------------- | ----------------- | ----------------------------------------- | 
| borough        | required          | Valid values [section 1.1](#section-1.1). |
| block          | required          | Tax block.                                |
| lot            | required          | Tax lot.                                  |

**Example Requests:**

  /v1/bbl.json?borough=manhattan&block=1889&lot=1&app_id=abc123&app_key=def456
  /v1/bbl.xml?borough=manhattan&block=67&lot=1&app_id=abc123&app_key=def456

**Example JSON Response**

```json
{
   "bbl":
   {
	   "bbl": "1018890001",
	   "bblBoroughCode": "1",
	   "bblBoroughCodeIn": "1",
	   "bblTaxBlock": "01889",
	   "bblTaxBlockIn": "1889",
	   "bblTaxLot": "0001",
	   "bblTaxLotIn": "1",
	   "buildingIdentificationNumber": "1057127",
	   "condominiumBillingBbl": "0000000000",
	   "cooperativeIdNumber": "0000",
	   "cornerCode": "NE",
	   "crossStreetNamesFlagIn": "E",
	   "firstBoroughName": "MANHATTAN",
	   "geosupportFunctionCode": "BL",
	   "geosupportReturnCode": "00",
	   "gi5DigitStreetCode1": "29690",
	   "gi5DigitStreetCode2": "35770",
	   "giBoroughCode1": "1",
	   "giBoroughCode2": "1",
	   "giBuildingIdentificationNumber1": "1057127",
	   "giBuildingIdentificationNumber2": "1057127",
	   "giDcpPreferredLgc1": "01",
	   "giDcpPreferredLgc2": "01",
	   "giGeographicIdentifier1": "V",
	   "giHighHouseNumber1": "280",
	   "giHighHouseNumber2": "337",
	   "giLowHouseNumber1": "280",
	   "giLowHouseNumber2": "327",
	   "giSideOfStreetIndicator1": "R",
	   "giSideOfStreetIndicator2": "R",
	   "giStreetCode1": "12969001",
	   "giStreetCode2": "13577001",
	   "highBblOfThisBuildingsCondominiumUnits": "1018890001",
	   "internalLabelXCoordinate": "0991817",
	   "internalLabelYCoordinate": "0230239",
	   "lowBblOfThisBuildingsCondominiumUnits": "1018890001",
	   "lowHouseNumberOfDefiningAddressRange": "000280000AA",
	   "numberOfEntriesInListOfGeographicIdentifiers": "0002",
	   "numberOfExistingStructuresOnLot": "0001",
	   "numberOfStreetFrontagesOfLot": "02",
	   "rpadBuildingClassificationCode": "D3",
	   "rpadSelfCheckCodeForBbl": "3",
	   "sanbornBoroughCode": "1",
	   "sanbornPageNumber": "034",
	   "sanbornVolumeNumber": "07",
	   "sanbornVolumeNumberSuffix": "S",
	   "taxMapNumberSectionAndVolume": "10703",
	   "workAreaFormatIndicatorIn": "C"
   }
}
```

**Example XML Response**

```xml
<geosupportResponse>
  <bbl>
    <bbl>1000670001</bbl>
    <bblBoroughCode>1</bblBoroughCode>
    <bblBoroughCodeIn>1</bblBoroughCodeIn>
    <bblTaxBlock>00067</bblTaxBlock>
    <bblTaxBlockIn>67</bblTaxBlockIn>
    <bblTaxLot>0001</bblTaxLot>
    <bblTaxLotIn>1</bblTaxLotIn>
    <buildingIdentificationNumber>1079043</buildingIdentificationNumber>
    <condominiumBillingBbl>0000000000</condominiumBillingBbl>
    <cooperativeIdNumber>0000</cooperativeIdNumber>
    <cornerCode>CR</cornerCode>
    <crossStreetNamesFlagIn>E</crossStreetNamesFlagIn>
    <dcpCommercialStudyArea>11004</dcpCommercialStudyArea>
    <firstBoroughName>MANHATTAN</firstBoroughName>
    <geosupportFunctionCode>BL</geosupportFunctionCode>
    <geosupportReturnCode>00</geosupportReturnCode>
    <gi5DigitStreetCode1>24050</gi5DigitStreetCode1>
    <gi5DigitStreetCode2>25630</gi5DigitStreetCode2>
    <gi5DigitStreetCode3>45440</gi5DigitStreetCode3>
    <gi5DigitStreetCode4>45440</gi5DigitStreetCode4>
    <giBoroughCode1>1</giBoroughCode1>
    <giBoroughCode2>1</giBoroughCode2>
    <giBoroughCode3>1</giBoroughCode3>
    <giBoroughCode4>1</giBoroughCode4>
    <giBuildingIdentificationNumber1>1079043</giBuildingIdentificationNumber1>
    <giBuildingIdentificationNumber2>1079043</giBuildingIdentificationNumber2>
    <giBuildingIdentificationNumber3>1079043</giBuildingIdentificationNumber3>
    <giBuildingIdentificationNumber4>1079043</giBuildingIdentificationNumber4>
    <giDcpPreferredLgc1>01</giDcpPreferredLgc1>
    <giDcpPreferredLgc2>01</giDcpPreferredLgc2>
    <giDcpPreferredLgc3>01</giDcpPreferredLgc3>
    <giDcpPreferredLgc4>01</giDcpPreferredLgc4>
    <giHighHouseNumber1>68</giHighHouseNumber1>
    <giHighHouseNumber2>65</giHighHouseNumber2>
    <giHighHouseNumber3>99</giHighHouseNumber3>
    <giHighHouseNumber4>105</giHighHouseNumber4>
    <giLowHouseNumber1>50</giLowHouseNumber1>
    <giLowHouseNumber2>41</giLowHouseNumber2>
    <giLowHouseNumber3>85</giLowHouseNumber3>
    <giLowHouseNumber4>101</giLowHouseNumber4>
    <giSideOfStreetIndicator1>R</giSideOfStreetIndicator1>
    <giSideOfStreetIndicator2>L</giSideOfStreetIndicator2>
    <giSideOfStreetIndicator3>L</giSideOfStreetIndicator3>
    <giSideOfStreetIndicator4>L</giSideOfStreetIndicator4>
    <giStreetCode1>12405001</giStreetCode1>
    <giStreetCode2>12563001</giStreetCode2>
    <giStreetCode3>14544001</giStreetCode3>
    <giStreetCode4>14544001</giStreetCode4>
    <highBblOfThisBuildingsCondominiumUnits>1000670001</highBblOfThisBuildingsCondominiumUnits>
    <internalLabelXCoordinate>0982039</internalLabelXCoordinate>
    <internalLabelYCoordinate>0197441</internalLabelYCoordinate>
    <lowBblOfThisBuildingsCondominiumUnits>1000670001</lowBblOfThisBuildingsCondominiumUnits>
    <lowHouseNumberOfDefiningAddressRange>000050000AA</lowHouseNumberOfDefiningAddressRange>
    <numberOfEntriesInListOfGeographicIdentifiers>0004</numberOfEntriesInListOfGeographicIdentifiers>
    <numberOfExistingStructuresOnLot>0001</numberOfExistingStructuresOnLot>
    <numberOfStreetFrontagesOfLot>03</numberOfStreetFrontagesOfLot>
    <rpadBuildingClassificationCode>O3</rpadBuildingClassificationCode>
    <rpadSelfCheckCodeForBbl>7</rpadSelfCheckCodeForBbl>
    <sanbornBoroughCode>1</sanbornBoroughCode>
    <sanbornPageNumber>011</sanbornPageNumber>
    <sanbornVolumeNumber>01</sanbornVolumeNumber>
    <sanbornVolumeNumberSuffix>S</sanbornVolumeNumberSuffix>
    <taxMapNumberSectionAndVolume>10102</taxMapNumberSectionAndVolume>
    <workAreaFormatIndicatorIn>C</workAreaFormatIndicatorIn>
  </bbl>
</geosupportResponse>
```

### <a id="section-1.2.3">1.2.3 - BIN</a>

**Path:** /v1/bin.{format}

**Parameters:**

| Parameter Name | Required/Optional | Comments                        |
| -------------- | ----------------- | ------------------------------- | 
| bin            | required          | Building identification number. |

**Example Requests:**

    /v1/bin.json?bin=1079043&app_id=abc123&app_key=def456
    /v1/bin.xml?bin=1057127&app_id=abc123&app_key=def456

**Example JSON Response**

```json
{
   "bin":
   {
	   "bbl": "1000670001",
	   "bblBoroughCode": "1",
	   "bblTaxBlock": "00067",
	   "bblTaxLot": "0001",
	   "buildingIdentificationNumber": "1079043",
	   "buildingIdentificationNumberIn": "1079043",
	   "condominiumBillingBbl": "0000000000",
	   "cooperativeIdNumber": "0000",
	   "cornerCode": "CR",
	   "crossStreetNamesFlagIn": "E",
	   "dcpCommercialStudyArea": "11004",
	   "firstBoroughName": "MANHATTAN",
	   "geosupportFunctionCode": "BN",
	   "geosupportReturnCode": "00",
	   "gi5DigitStreetCode1": "24050",
	   "gi5DigitStreetCode2": "25630",
	   "gi5DigitStreetCode3": "45440",
	   "gi5DigitStreetCode4": "45440",
	   "giBoroughCode1": "1",
	   "giBoroughCode2": "1",
	   "giBoroughCode3": "1",
	   "giBoroughCode4": "1",
	   "giBuildingIdentificationNumber1": "1079043",
	   "giBuildingIdentificationNumber2": "1079043",
	   "giBuildingIdentificationNumber3": "1079043",
	   "giBuildingIdentificationNumber4": "1079043",
	   "giDcpPreferredLgc1": "01",
	   "giDcpPreferredLgc2": "01",
	   "giDcpPreferredLgc3": "01",
	   "giDcpPreferredLgc4": "01",
	   "giHighHouseNumber1": "68",
	   "giHighHouseNumber2": "65",
	   "giHighHouseNumber3": "99",
	   "giHighHouseNumber4": "105",
	   "giLowHouseNumber1": "50",
	   "giLowHouseNumber2": "41",
	   "giLowHouseNumber3": "85",
	   "giLowHouseNumber4": "101",
	   "giSideOfStreetIndicator1": "R",
	   "giSideOfStreetIndicator2": "L",
	   "giSideOfStreetIndicator3": "L",
	   "giSideOfStreetIndicator4": "L",
	   "giStreetCode1": "12405001",
	   "giStreetCode2": "12563001",
	   "giStreetCode3": "14544001",
	   "giStreetCode4": "14544001",
	   "highBblOfThisBuildingsCondominiumUnits": "1000670001",
	   "internalLabelXCoordinate": "0982039",
	   "internalLabelYCoordinate": "0197441",
	   "lowBblOfThisBuildingsCondominiumUnits": "1000670001",
	   "lowHouseNumberOfDefiningAddressRange": "000050000AA",
	   "numberOfEntriesInListOfGeographicIdentifiers": "0004",
	   "numberOfExistingStructuresOnLot": "0001",
	   "numberOfStreetFrontagesOfLot": "03",
	   "rpadBuildingClassificationCode": "O3",
	   "rpadSelfCheckCodeForBbl": "7",
	   "sanbornBoroughCode": "1",
	   "sanbornPageNumber": "011",
	   "sanbornVolumeNumber": "01",
	   "sanbornVolumeNumberSuffix": "S",
	   "taxMapNumberSectionAndVolume": "10102",
	   "workAreaFormatIndicatorIn": "C"
   }
}
```

**Example XML Response**

```xml
<geosupportResponse>
  <bin>
    <bbl>1018890001</bbl>
    <bblBoroughCode>1</bblBoroughCode>
    <bblTaxBlock>01889</bblTaxBlock>
    <bblTaxLot>0001</bblTaxLot>
    <buildingIdentificationNumber>1057127</buildingIdentificationNumber>
    <buildingIdentificationNumberIn>1057127</buildingIdentificationNumberIn>
    <condominiumBillingBbl>0000000000</condominiumBillingBbl>
    <cooperativeIdNumber>0000</cooperativeIdNumber>
    <cornerCode>NE</cornerCode>
    <crossStreetNamesFlagIn>E</crossStreetNamesFlagIn>
    <firstBoroughName>MANHATTAN</firstBoroughName>
    <geosupportFunctionCode>BN</geosupportFunctionCode>
    <geosupportReturnCode>00</geosupportReturnCode>
    <gi5DigitStreetCode1>29690</gi5DigitStreetCode1>
    <gi5DigitStreetCode2>35770</gi5DigitStreetCode2>
    <giBoroughCode1>1</giBoroughCode1>
    <giBoroughCode2>1</giBoroughCode2>
    <giBuildingIdentificationNumber1>1057127</giBuildingIdentificationNumber1>
    <giBuildingIdentificationNumber2>1057127</giBuildingIdentificationNumber2>
    <giDcpPreferredLgc1>01</giDcpPreferredLgc1>
    <giDcpPreferredLgc2>01</giDcpPreferredLgc2>
    <giGeographicIdentifier1>V</giGeographicIdentifier1>
    <giHighHouseNumber1>280</giHighHouseNumber1>
    <giHighHouseNumber2>337</giHighHouseNumber2>
    <giLowHouseNumber1>280</giLowHouseNumber1>
    <giLowHouseNumber2>327</giLowHouseNumber2>
    <giSideOfStreetIndicator1>R</giSideOfStreetIndicator1>
    <giSideOfStreetIndicator2>R</giSideOfStreetIndicator2>
    <giStreetCode1>12969001</giStreetCode1>
    <giStreetCode2>13577001</giStreetCode2>
    <highBblOfThisBuildingsCondominiumUnits>1018890001</highBblOfThisBuildingsCondominiumUnits>
    <internalLabelXCoordinate>0991817</internalLabelXCoordinate>
    <internalLabelYCoordinate>0230239</internalLabelYCoordinate>
    <lowBblOfThisBuildingsCondominiumUnits>1018890001</lowBblOfThisBuildingsCondominiumUnits>
    <lowHouseNumberOfDefiningAddressRange>000280000AA</lowHouseNumberOfDefiningAddressRange>
    <numberOfEntriesInListOfGeographicIdentifiers>0002</numberOfEntriesInListOfGeographicIdentifiers>
    <numberOfExistingStructuresOnLot>0001</numberOfExistingStructuresOnLot>
    <numberOfStreetFrontagesOfLot>02</numberOfStreetFrontagesOfLot>
    <rpadBuildingClassificationCode>D3</rpadBuildingClassificationCode>
    <rpadSelfCheckCodeForBbl>3</rpadSelfCheckCodeForBbl>
    <sanbornBoroughCode>1</sanbornBoroughCode>
    <sanbornPageNumber>034</sanbornPageNumber>
    <sanbornVolumeNumber>07</sanbornVolumeNumber>
    <sanbornVolumeNumberSuffix>S</sanbornVolumeNumberSuffix>
    <taxMapNumberSectionAndVolume>10703</taxMapNumberSectionAndVolume>
    <workAreaFormatIndicatorIn>C</workAreaFormatIndicatorIn>
  </bin>
</geosupportResponse>
```

### <a id="section-1.2.4">1.2.4 - Blockface</a>

**Path:** /v1/blockface.{format}

**Parameters:**

| Parameter Name        | Required/Optional | Comments                                                                                               |
| --------------------- | ----------------- | ------------------------------------------------------------------------------------------------------ | 
| onStreet              | required          | "On street" (street name of target blockface).                                                         |
| crossStreetOne        | required          | First cross street of blockface.                                                                       |
| crossStreetTwo        | required          | Second cross street of blockface.                                                                      |
| borough               | required          | Borough of `onStreet`. Valid values [section 1.1](#section-1.1).                                       |
| boroughCrossStreetOne | optional          | Borough of first cross street. Defaults to value of `borough` parameter if not supplied.               |
| boroughCrossStreetTwo | optional          | Borough of second cross street. Defaults to value of `borough` parameter if not supplied.              |
| compassDirection      | optional          | Used to request information about only one side of the street. Valid values are: `N`, `S`, `E` or `W`. |

**Example Requests:**

    /v1/blockface.json?onStreet=amsterdam ave&crossStreetOne=w 110 st&crossStreetTwo=w 111 st&borough=manhattan&app_id=abc123&app_key=def456
    /v1/blockface.xml?onStreet=amsterdam ave&crossStreetOne=w 110 st&crossStreetTwo=w 111 st&borough=manhattan&compassDirection=e&app_id=abc123&app_key=def456
    /v1/blockface.xml?onStreet=eldert ln&crossStreetOne=etna street&crossStreetTwo=ridgewood ave&borough=queens&boroughCrossStreetOne=brooklyn&boughCrossStreetTwo=brooklyn&compassDirection=e&app_id=abc123&app_key=def456

**Example JSON Response**

```json
{
   "blockface":
   {
	   "boroughCode1In": "1",
	   "coincidentSegmentCount": "1",
	   "crossStreetNamesFlagIn": "E",
	   "dcpPreferredLgcForStreet1": "01",
	   "dcpPreferredLgcForStreet2": "01",
	   "dcpPreferredLgcForStreet3": "01",
	   "dotStreetLightContractorArea": "1",
	   "firstBoroughName": "MANHATTAN",
	   "firstStreetCode": "11171001010",
	   "firstStreetNameNormalized": "AMSTERDAM AVENUE",
	   "fromNode": "0023924",
	   "generatedRecordFlag": "L",
	   "geosupportFunctionCode": "3",
	   "geosupportReturnCode": "00",
	   "highAddressEndCrossStreet1": "135990",
	   "leftSegment1990CensusTract": "199",
	   "leftSegment2000CensusBlock": "2001",
	   "leftSegment2000CensusTract": "199",
	   "leftSegment2010CensusBlock": "6000",
	   "leftSegment2010CensusTract": "199",
	   "leftSegmentAssemblyDistrict": "69",
	   "leftSegmentCommunityDistrict": "109",
	   "leftSegmentCommunityDistrictBoroughCode": "1",
	   "leftSegmentCommunityDistrictNumber": "09",
	   "leftSegmentCommunitySchoolDistrict": "03",
	   "leftSegmentDynamicBlock": "202",
	   "leftSegmentElectionDistrict": "088",
	   "leftSegmentFireBattalion": "11",
	   "leftSegmentFireCompanyNumber": "047",
	   "leftSegmentFireCompanyType": "E",
	   "leftSegmentFireDivision": "03",
	   "leftSegmentHealthArea": "2310",
	   "leftSegmentHighHouseNumber": "0001034",
	   "leftSegmentInterimAssistanceEligibilityIndicator": "I",
	   "leftSegmentLowHouseNumber": "0001020",
	   "leftSegmentNta": "MN09",
	   "leftSegmentPolicePatrolBoroughCommand": "2",
	   "leftSegmentPolicePrecinct": "026",
	   "leftSegmentZipCode": "10025",
	   "lengthOfSegmentInFeet": "00273",
	   "lionBoroughCode": "1",
	   "lionFaceCode": "0535",
	   "lionSequenceNumber": "02360",
	   "locationalStatusOfSegment": "X",
	   "lowAddressEndCrossStreet1": "114210",
	   "numberOfCrossStreetsHighAddressEnd": "1",
	   "numberOfCrossStreetsLowAddressEnd": "1",
	   "numberOfStreetCodesAndNamesInList": "02",
	   "rightSegment1990CensusTract": "19701",
	   "rightSegment2000CensusBlock": "1003",
	   "rightSegment2000CensusTract": "19701",
	   "rightSegment2010CensusBlock": "1002",
	   "rightSegment2010CensusTract": "19701",
	   "rightSegmentAssemblyDistrict": "69",
	   "rightSegmentCommunityDistrict": "109",
	   "rightSegmentCommunityDistrictBoroughCode": "1",
	   "rightSegmentCommunityDistrictNumber": "09",
	   "rightSegmentCommunitySchoolDistrict": "03",
	   "rightSegmentDynamicBlock": "104",
	   "rightSegmentElectionDistrict": "089",
	   "rightSegmentFireBattalion": "11",
	   "rightSegmentFireCompanyNumber": "047",
	   "rightSegmentFireCompanyType": "E",
	   "rightSegmentFireDivision": "03",
	   "rightSegmentHealthArea": "2320",
	   "rightSegmentHighHouseNumber": "0001035",
	   "rightSegmentInterimAssistanceEligibilityIndicator": "I",
	   "rightSegmentLowHouseNumber": "0001021",
	   "rightSegmentNta": "MN09",
	   "rightSegmentPolicePatrolBoroughCommand": "2",
	   "rightSegmentPolicePrecinct": "026",
	   "rightSegmentZipCode": "10025",
	   "sanitationSnowPriorityCode": "P",
	   "secondStreetCode": "11421003010",
	   "secondStreetNameNormalized": "WEST 110 STREET",
	   "segmentAzimuth": "061",
	   "segmentIdentifier": "0223271",
	   "segmentOrientation": "N",
	   "segmentTypeCode": "U",
	   "streetCode1": "11421003",
	   "streetCode6": "13599001",
	   "streetName1": "WEST 110 STREET",
	   "streetName1In": "amsterdam ave",
	   "streetName2In": "w 110 st",
	   "streetName3In": "w 111 st",
	   "streetName6": "WEST 111 STREET",
	   "thirdStreetCode": "13599001010",
	   "thirdStreetNameNormalized": "WEST 111 STREET",
	   "toNode": "0023926",
	   "workAreaFormatIndicatorIn": "C"
   }
}
```

**Example XML Response**

```xml
<geosupportResponse>
  <blockface>
    <boroughCode1In>1</boroughCode1In>
    <coincidentSegmentCount>1</coincidentSegmentCount>
    <compassDirectionIn>e</compassDirectionIn>
    <crossStreetNamesFlagIn>E</crossStreetNamesFlagIn>
    <dcpPreferredLgcForStreet1>01</dcpPreferredLgcForStreet1>
    <dcpPreferredLgcForStreet2>01</dcpPreferredLgcForStreet2>
    <dcpPreferredLgcForStreet3>01</dcpPreferredLgcForStreet3>
    <dotStreetLightContractorArea>1</dotStreetLightContractorArea>
    <firstBoroughName>MANHATTAN</firstBoroughName>
    <firstStreetCode>11171001010</firstStreetCode>
    <firstStreetNameNormalized>AMSTERDAM AVENUE</firstStreetNameNormalized>
    <fromNode>0023924</fromNode>
    <generatedRecordFlag>L</generatedRecordFlag>
    <geosupportFunctionCode>3</geosupportFunctionCode>
    <geosupportReturnCode>01</geosupportReturnCode>
    <highAddressEndCrossStreet1>135990</highAddressEndCrossStreet1>
    <leftSegment1990CensusTract>199</leftSegment1990CensusTract>
    <leftSegment2000CensusBlock>2001</leftSegment2000CensusBlock>
    <leftSegment2000CensusTract>199</leftSegment2000CensusTract>
    <leftSegment2010CensusBlock>6000</leftSegment2010CensusBlock>
    <leftSegment2010CensusTract>199</leftSegment2010CensusTract>
    <leftSegmentAssemblyDistrict>69</leftSegmentAssemblyDistrict>
    <leftSegmentCommunityDistrict>109</leftSegmentCommunityDistrict>
    <leftSegmentCommunityDistrictBoroughCode>1</leftSegmentCommunityDistrictBoroughCode>
    <leftSegmentCommunityDistrictNumber>09</leftSegmentCommunityDistrictNumber>
    <leftSegmentCommunitySchoolDistrict>03</leftSegmentCommunitySchoolDistrict>
    <leftSegmentDynamicBlock>202</leftSegmentDynamicBlock>
    <leftSegmentElectionDistrict>088</leftSegmentElectionDistrict>
    <leftSegmentFireBattalion>11</leftSegmentFireBattalion>
    <leftSegmentFireCompanyNumber>047</leftSegmentFireCompanyNumber>
    <leftSegmentFireCompanyType>E</leftSegmentFireCompanyType>
    <leftSegmentFireDivision>03</leftSegmentFireDivision>
    <leftSegmentHealthArea>2310</leftSegmentHealthArea>
    <leftSegmentHighHouseNumber>0001034</leftSegmentHighHouseNumber>
    <leftSegmentInterimAssistanceEligibilityIndicator>I</leftSegmentInterimAssistanceEligibilityIndicator>
    <leftSegmentLowHouseNumber>0001020</leftSegmentLowHouseNumber>
    <leftSegmentNta>MN09</leftSegmentNta>
    <leftSegmentPolicePatrolBoroughCommand>2</leftSegmentPolicePatrolBoroughCommand>
    <leftSegmentPolicePrecinct>026</leftSegmentPolicePrecinct>
    <leftSegmentZipCode>10025</leftSegmentZipCode>
    <lengthOfSegmentInFeet>00273</lengthOfSegmentInFeet>
    <lionBoroughCode>1</lionBoroughCode>
    <lionFaceCode>0535</lionFaceCode>
    <lionSequenceNumber>02360</lionSequenceNumber>
    <locationalStatusOfSegment>X</locationalStatusOfSegment>
    <lowAddressEndCrossStreet1>114210</lowAddressEndCrossStreet1>
    <message>THESE STREETS INTERSECT ONCE-COMPASS DIRECTION IGNORED</message>
    <numberOfCrossStreetsHighAddressEnd>1</numberOfCrossStreetsHighAddressEnd>
    <numberOfCrossStreetsLowAddressEnd>1</numberOfCrossStreetsLowAddressEnd>
    <numberOfStreetCodesAndNamesInList>02</numberOfStreetCodesAndNamesInList>
    <reasonCode>H</reasonCode>
    <rightSegment1990CensusTract>19701</rightSegment1990CensusTract>
    <rightSegment2000CensusBlock>1003</rightSegment2000CensusBlock>
    <rightSegment2000CensusTract>19701</rightSegment2000CensusTract>
    <rightSegment2010CensusBlock>1002</rightSegment2010CensusBlock>
    <rightSegment2010CensusTract>19701</rightSegment2010CensusTract>
    <rightSegmentAssemblyDistrict>69</rightSegmentAssemblyDistrict>
    <rightSegmentCommunityDistrict>109</rightSegmentCommunityDistrict>
    <rightSegmentCommunityDistrictBoroughCode>1</rightSegmentCommunityDistrictBoroughCode>
    <rightSegmentCommunityDistrictNumber>09</rightSegmentCommunityDistrictNumber>
    <rightSegmentCommunitySchoolDistrict>03</rightSegmentCommunitySchoolDistrict>
    <rightSegmentDynamicBlock>104</rightSegmentDynamicBlock>
    <rightSegmentElectionDistrict>089</rightSegmentElectionDistrict>
    <rightSegmentFireBattalion>11</rightSegmentFireBattalion>
    <rightSegmentFireCompanyNumber>047</rightSegmentFireCompanyNumber>
    <rightSegmentFireCompanyType>E</rightSegmentFireCompanyType>
    <rightSegmentFireDivision>03</rightSegmentFireDivision>
    <rightSegmentHealthArea>2320</rightSegmentHealthArea>
    <rightSegmentHighHouseNumber>0001035</rightSegmentHighHouseNumber>
    <rightSegmentInterimAssistanceEligibilityIndicator>I</rightSegmentInterimAssistanceEligibilityIndicator>
    <rightSegmentLowHouseNumber>0001021</rightSegmentLowHouseNumber>
    <rightSegmentNta>MN09</rightSegmentNta>
    <rightSegmentPolicePatrolBoroughCommand>2</rightSegmentPolicePatrolBoroughCommand>
    <rightSegmentPolicePrecinct>026</rightSegmentPolicePrecinct>
    <rightSegmentZipCode>10025</rightSegmentZipCode>
    <sanitationSnowPriorityCode>P</sanitationSnowPriorityCode>
    <secondStreetCode>11421003010</secondStreetCode>
    <secondStreetNameNormalized>WEST 110 STREET</secondStreetNameNormalized>
    <segmentAzimuth>061</segmentAzimuth>
    <segmentIdentifier>0223271</segmentIdentifier>
    <segmentOrientation>N</segmentOrientation>
    <segmentTypeCode>U</segmentTypeCode>
    <streetCode1>11421003</streetCode1>
    <streetCode6>13599001</streetCode6>
    <streetName1>WEST 110 STREET</streetName1>
    <streetName1In>amsterdam ave</streetName1In>
    <streetName2In>w 110 st</streetName2In>
    <streetName3In>w 111 st</streetName3In>
    <streetName6>WEST 111 STREET</streetName6>
    <thirdStreetCode>13599001010</thirdStreetCode>
    <thirdStreetNameNormalized>WEST 111 STREET</thirdStreetNameNormalized>
    <toNode>0023926</toNode>
    <workAreaFormatIndicatorIn>C</workAreaFormatIndicatorIn>
  </blockface>
</geosupportResponse>
```

### <a id="section-1.2.5">1.2.5 - Intersection</a>

**Path:** /v1/intersection.{format}

**Parameters:**

| Parameter Name        | Required/Optional          | Comments                                                                                         |
| --------------------- | -------------------------- | ------------------------------------------------------------------------------------------------ | 
| crossStreetOne        | required                   | First cross street                                                                               |
| crossStreetTwo        | required                   | Second cross street                                                                              |
| borough               | required                   | Borough of first cross street or of all cross streets if no other borough parameter is supplied. |
| boroughCrossStreetTwo | optional                   | Borough of second cross street. If not supplied, assumed to be same as `borough` parameter.      |
| compassDirection      | optional for most requests | Required for streets that intersect more than once. Valid values are: `N`, `S`, `E` or `W`.      |

**Example Requests:**

    /v1/intersection.json?crossStreetOne=broadway&crossStreetTwo=w 99 st&borough=manhattan&app_id=abc123&app_key=def456
    /v1/intersection.xml?crossStreetOne=rsd&crossStreetTwo=w 97 st&borough=manhattan&compassDirection=e&app_id=abc123&app_key=def456
    /v1/intersection.json?crossStreetOne=jamaica ave&crossStreetTwo=eldert ln&borough=brooklyn&boroughCrossStreetTwo=queens&app_id=abc123&app_key=def456

**Example JSON Response**

```json
{
   "intersection":
   {
	   "assemblyDistrict": "69",
	   "boroughCode1In": "1",
	   "censusTract1990": "187",
	   "censusTract2000": "187",
	   "censusTract2010": "187",
	   "cityCouncilDistrict": "08",
	   "civilCourtDistrict": "05",
	   "communityDistrict": "107",
	   "communityDistrictBoroughCode": "1",
	   "communityDistrictNumber": "07",
	   "communitySchoolDistrict": "03",
	   "congressionalDistrict": "10",
	   "crossStreetNamesFlagIn": "E",
	   "dcpPreferredLgcForStreet1": "01",
	   "dcpPreferredLgcForStreet2": "01",
	   "dotStreetLightContractorArea": "1",
	   "fireBattalion": "11",
	   "fireCompanyNumber": "022",
	   "fireCompanyType": "L",
	   "fireDivision": "03",
	   "firstBoroughName": "MANHATTAN",
	   "firstStreetCode": "11361001010",
	   "firstStreetNameNormalized": "BROADWAY",
	   "geosupportFunctionCode": "2",
	   "geosupportReturnCode": "00",
	   "healthArea": "3110",
	   "healthCenterDistrict": "16",
	   "interimAssistanceEligibilityIndicator": "I",
	   "intersectingStreet1": "113610",
	   "intersectingStreet2": "135750",
	   "lionNodeNumber": "0023424",
	   "listOfPairsOfLevelCodes": "**MM",
	   "numberOfIntersectingStreets": "2",
	   "numberOfStreetCodesAndNamesInList": "02",
	   "policePatrolBoroughCommand": "2",
	   "policePrecinct": "024",
	   "sanbornBoroughCode1": "1",
	   "sanbornBoroughCode2": "1",
	   "sanbornPageNumber1": "035",
	   "sanbornPageNumber2": "036",
	   "sanbornVolumeNumber1": "07",
	   "sanbornVolumeNumber2": "07",
	   "sanbornVolumeNumberSuffix1": "S",
	   "sanbornVolumeNumberSuffix2": "S",
	   "sanitationCollectionSchedulingSectionAndSubsection": "4B",
	   "sanitationDistrict": "107",
	   "secondStreetCode": "13575001010",
	   "secondStreetNameNormalized": "WEST 99 STREET",
	   "stateSenatorialDistrict": "30",
	   "streetCode1": "11361001",
	   "streetCode2": "13575001",
	   "streetName1": "BROADWAY",
	   "streetName1In": "broadway",
	   "streetName2": "WEST 99 STREET",
	   "streetName2In": "w 99 st",
	   "workAreaFormatIndicatorIn": "C",
	   "xCoordinate": "0992454",
	   "yCoordinate": "0229500",
	   "zipCode": "10025"
   }
}
```

**Example XML Response**

```xml
<geosupportResponse>
  <intersection>
    <assemblyDistrict>69</assemblyDistrict>
    <attributeBytes>M</attributeBytes>
    <boroughCode1In>1</boroughCode1In>
    <censusTract1990>183</censusTract1990>
    <censusTract2000>183</censusTract2000>
    <censusTract2010>183</censusTract2010>
    <cityCouncilDistrict>09</cityCouncilDistrict>
    <civilCourtDistrict>05</civilCourtDistrict>
    <communityDistrict>107</communityDistrict>
    <communityDistrictBoroughCode>1</communityDistrictBoroughCode>
    <communityDistrictNumber>07</communityDistrictNumber>
    <communitySchoolDistrict>03</communitySchoolDistrict>
    <compassDirection>E</compassDirection>
    <compassDirectionIn>e</compassDirectionIn>
    <congressionalDistrict>10</congressionalDistrict>
    <crossStreetNamesFlagIn>E</crossStreetNamesFlagIn>
    <dcpPreferredLgcForStreet1>01</dcpPreferredLgcForStreet1>
    <dcpPreferredLgcForStreet2>01</dcpPreferredLgcForStreet2>
    <dotStreetLightContractorArea>1</dotStreetLightContractorArea>
    <fireBattalion>11</fireBattalion>
    <fireCompanyNumber>022</fireCompanyNumber>
    <fireCompanyType>L</fireCompanyType>
    <fireDivision>03</fireDivision>
    <firstBoroughName>MANHATTAN</firstBoroughName>
    <firstStreetCode>12969001020</firstStreetCode>
    <firstStreetNameNormalized>RSD</firstStreetNameNormalized>
    <geosupportFunctionCode>2</geosupportFunctionCode>
    <geosupportReturnCode>00</geosupportReturnCode>
    <healthArea>3110</healthArea>
    <healthCenterDistrict>16</healthCenterDistrict>
    <interimAssistanceEligibilityIndicator>I</interimAssistanceEligibilityIndicator>
    <intersectingStreet1>129690</intersectingStreet1>
    <intersectingStreet2>135710</intersectingStreet2>
    <intersectionReplicationCounter>2</intersectionReplicationCounter>
    <lionNodeNumber>0066281</lionNodeNumber>
    <listOfPairsOfLevelCodes>MMMM</listOfPairsOfLevelCodes>
    <numberOfIntersectingStreets>2</numberOfIntersectingStreets>
    <numberOfStreetCodesAndNamesInList>02</numberOfStreetCodesAndNamesInList>
    <policePatrolBoroughCommand>2</policePatrolBoroughCommand>
    <policePrecinct>024</policePrecinct>
    <sanbornBoroughCode1>1</sanbornBoroughCode1>
    <sanbornBoroughCode2>1</sanbornBoroughCode2>
    <sanbornPageNumber1>033</sanbornPageNumber1>
    <sanbornPageNumber2>104</sanbornPageNumber2>
    <sanbornVolumeNumber1>07</sanbornVolumeNumber1>
    <sanbornVolumeNumber2>07</sanbornVolumeNumber2>
    <sanbornVolumeNumberSuffix1>S</sanbornVolumeNumberSuffix1>
    <sanbornVolumeNumberSuffix2>N</sanbornVolumeNumberSuffix2>
    <sanitationCollectionSchedulingSectionAndSubsection>4B</sanitationCollectionSchedulingSectionAndSubsection>
    <sanitationDistrict>107</sanitationDistrict>
    <secondStreetCode>13571001010</secondStreetCode>
    <secondStreetNameNormalized>WEST 97 STREET</secondStreetNameNormalized>
    <stateSenatorialDistrict>31</stateSenatorialDistrict>
    <streetAttributeIndicator>M</streetAttributeIndicator>
    <streetCode1>12969001</streetCode1>
    <streetCode2>13571001</streetCode2>
    <streetName1>RSD</streetName1>
    <streetName1In>rsd</streetName1In>
    <streetName2>WEST 97 STREET</streetName2>
    <streetName2In>w 97 st</streetName2In>
    <workAreaFormatIndicatorIn>C</workAreaFormatIndicatorIn>
    <xCoordinate>0991418</xCoordinate>
    <yCoordinate>0229475</yCoordinate>
    <zipCode>10025</zipCode>
  </intersection>
</geosupportResponse>
```

### <a id="section-1.2.6">1.2.6 - Place</a>

**Path:** /v1/place.{format}

**Parameters:**

| Parameter Name | Required/Optional | Comments                                  |
| -------------- | ----------------- | ----------------------------------------- | 
| name           | required          | Place name of well-known NYC location.    |
| borough        | required          | Valid values [section 1.1](#section-1.1). |

**Example Requests:**

    /v1/place.json?name=empire state building&borough=manhattan&app_id=abc123&app_key=def456
    /v1/place.xml?name=rfk bridge&borough=queens&app_id=abc123&app_key=def456

**Example JSON Response**

```json
{
   "place":
   {
	   "assemblyDistrict": "75",
	   "attributeBytes": "N",
	   "bbl": "1008350041",
	   "bblBoroughCode": "1",
	   "bblTaxBlock": "00835",
	   "bblTaxLot": "0041",
	   "boeLgcPointer": "1",
	   "boePreferredStreetName": "EMPIRE STATE BUILDING",
	   "boePreferredstreetCode": "12032001",
	   "boroughCode1In": "1",
	   "buildingIdentificationNumber": "1015862",
	   "businessImprovementDistrict": "113090",
	   "censusBlock2000": "1001",
	   "censusBlock2010": "1001",
	   "censusTract1990": "76",
	   "censusTract2000": "76",
	   "censusTract2010": "76",
	   "cityCouncilDistrict": "03",
	   "civilCourtDistrict": "09",
	   "coincidenceSegmentCount": "1",
	   "communityDistrict": "105",
	   "communityDistrictBoroughCode": "1",
	   "communityDistrictNumber": "05",
	   "communitySchoolDistrict": "02",
	   "condominiumBillingBbl": "0000000000",
	   "congressionalDistrict": "12",
	   "cooperativeIdNumber": "0000",
	   "cornerCode": "CR",
	   "crossStreetNamesFlagIn": "E",
	   "dcpCommercialStudyArea": "11021",
	   "dcpPreferredLgc": "01",
	   "dotStreetLightContractorArea": "1",
	   "dynamicBlock": "110",
	   "electionDistrict": "050",
	   "fireBattalion": "07",
	   "fireCompanyNumber": "024",
	   "fireCompanyType": "L",
	   "fireDivision": "01",
	   "firstBoroughName": "MANHATTAN",
	   "firstStreetCode": "12032001020",
	   "firstStreetNameNormalized": "EMPIRE STATE BUILDING",
	   "fromLionNodeId": "0021443",
	   "fromPreferredLgcsFirstSetOf5": "0101",
	   "genericId": "0001703",
	   "geosupportFunctionCode": "1B",
	   "geosupportReturnCode": "01",
	   "geosupportReturnCode2": "00",
	   "gi5DigitStreetCode1": "20320",
	   "gi5DigitStreetCode2": "10410",
	   "gi5DigitStreetCode3": "34430",
	   "gi5DigitStreetCode4": "34450",
	   "giBoroughCode1": "1",
	   "giBoroughCode2": "1",
	   "giBoroughCode3": "1",
	   "giBoroughCode4": "1",
	   "giBuildingIdentificationNumber1": "1015862",
	   "giBuildingIdentificationNumber2": "1015862",
	   "giBuildingIdentificationNumber3": "1015862",
	   "giBuildingIdentificationNumber4": "1015862",
	   "giDcpPreferredLgc1": "01",
	   "giDcpPreferredLgc2": "01",
	   "giDcpPreferredLgc3": "01",
	   "giDcpPreferredLgc4": "01",
	   "giGeographicIdentifier1": "N",
	   "giHighHouseNumber2": "350",
	   "giHighHouseNumber3": "31",
	   "giHighHouseNumber4": "20",
	   "giLowHouseNumber2": "338",
	   "giLowHouseNumber3": "1",
	   "giLowHouseNumber4": "2",
	   "giSideOfStreetIndicator1": "L",
	   "giSideOfStreetIndicator2": "L",
	   "giSideOfStreetIndicator3": "R",
	   "giSideOfStreetIndicator4": "L",
	   "giStreetCode1": "12032001",
	   "giStreetCode2": "11041001",
	   "giStreetCode3": "13443001",
	   "giStreetCode4": "13445001",
	   "giStreetName1": "EMPIRE STATE BUILDING",
	   "giStreetName2": "5 AVENUE",
	   "giStreetName3": "WEST 33 STREET",
	   "giStreetName4": "WEST 34 STREET",
	   "healthArea": "5200",
	   "healthCenterDistrict": "15",
	   "highBblOfThisBuildingsCondominiumUnits": "1008350041",
	   "highCrossStreetB5SC1": "117670",
	   "highCrossStreetB5SC2": "134450",
	   "highCrossStreetCode1": "11767001",
	   "highCrossStreetCode2": "134450",
	   "highCrossStreetName1": "EAST 34 STREET",
	   "highCrossStreetName2": "WEST 34 STREET",
	   "highHouseNumberOfBlockfaceSortFormat": "000000000AA",
	   "interimAssistanceEligibilityIndicator": "I",
	   "internalLabelXCoordinate": "0988196",
	   "internalLabelYCoordinate": "0211970",
	   "legacySegmentId": "0034023",
	   "lionKeyBoroughCode": "1",
	   "lionKeyFaceCode": "2470",
	   "lionKeyForVanityAddressBoroughCode": "1",
	   "lionKeyForVanityAddressFaceCode": "2470",
	   "lionKeyForVanityAddressSequenceNumber": "01150",
	   "lionKeySequenceNumber": "01150",
	   "listOf4Lgcs": "01",
	   "lowBblOfThisBuildingsCondominiumUnits": "1008350041",
	   "lowCrossStreetB5SC1": "117650",
	   "lowCrossStreetB5SC2": "134430",
	   "lowCrossStreetCode1": "11765001",
	   "lowCrossStreetCode2": "13443001",
	   "lowCrossStreetName1": "EAST 33 STREET",
	   "lowCrossStreetName2": "WEST 33 STREET",
	   "lowHouseNumberOfBlockfaceSortFormat": "000000000AA",
	   "lowHouseNumberOfDefiningAddressRange": "000001000AA",
	   "message": "350 5 AVENUE IS THE UNDERLYING ADDRESS OF EMPIRE STATE BUILDING",
	   "nta": "MN17",
	   "ntaName": "Midtown-Midtown South",
	   "numberOfCrossStreetB5SCsHighAddressEnd": "3",
	   "numberOfCrossStreetB5SCsLowAddressEnd": "2",
	   "numberOfCrossStreetsHighAddressEnd": "2",
	   "numberOfCrossStreetsLowAddressEnd": "2",
	   "numberOfEntriesInListOfGeographicIdentifiers": "0004",
	   "numberOfExistingStructuresOnLot": "0001",
	   "numberOfStreetFrontagesOfLot": "03",
	   "physicalId": "0001934",
	   "policePatrolBoroughCommand": "1",
	   "policePrecinct": "014",
	   "reasonCode": "V",
	   "reasonCode1e": "V",
	   "returnCode1a": "00",
	   "returnCode1e": "01",
	   "roadwayType": "1",
	   "rpadBuildingClassificationCode": "O4",
	   "rpadSelfCheckCodeForBbl": "3",
	   "sanbornBoroughCode": "1",
	   "sanbornPageNumber": "016",
	   "sanbornVolumeNumber": "04",
	   "sanitationCollectionSchedulingSectionAndSubsection": "1A",
	   "sanitationDistrict": "105",
	   "sanitationRecyclingCollectionSchedule": "EF",
	   "sanitationRegularCollectionSchedule": "MWF",
	   "sanitationSnowPriorityCode": "P",
	   "segmentAzimuth": "061",
	   "segmentIdentifier": "0034023",
	   "segmentLengthInFeet": "00277",
	   "segmentOrientation": "N",
	   "segmentTypeCode": "U",
	   "sideOfStreetIndicator": "L",
	   "sideOfStreetOfVanityAddress": "L",
	   "specialAddressGeneratedRecordFlag": "N",
	   "splitLowHouseNumber": "000001000AA",
	   "stateSenatorialDistrict": "27",
	   "streetAttributeIndicator": "N",
	   "streetName1In": "empire state building",
	   "streetStatus": "2",
	   "taxMapNumberSectionAndVolume": "10306",
	   "toLionNodeId": "0021445",
	   "toPreferredLgcsFirstSetOf5": "01",
	   "trafficDirection": "A",
	   "underlyingHnsOnTrueStreet": "000350000AA",
	   "underlyingstreetCode": "11041001",
	   "workAreaFormatIndicatorIn": "C",
	   "xCoordinate": "0988205",
	   "xCoordinateHighAddressEnd": "0988528",
	   "xCoordinateLowAddressEnd": "0988394",
	   "xCoordinateOfCenterofCurvature": "0000000",
	   "yCoordinate": "0211959",
	   "yCoordinateHighAddressEnd": "0211953",
	   "yCoordinateLowAddressEnd": "0211711",
	   "yCoordinateOfCenterofCurvature": "0000000",
	   "zipCode": "10018"
   }
}
```

**Example XML Response**

```xml
<geosupportResponse>
  <place>
    <assemblyDistrict>36</assemblyDistrict>
    <attributeBytes>B</attributeBytes>
    <boeLgcPointer>1</boeLgcPointer>
    <boePreferredStreetName>ROBERT F KENNEDY BRIDGE</boePreferredStreetName>
    <boePreferredstreetCode>49730001</boePreferredstreetCode>
    <boroughCode1In>4</boroughCode1In>
    <censusBlock2000>1004</censusBlock2000>
    <censusBlock2010>1004</censusBlock2010>
    <censusTract1990>99</censusTract1990>
    <censusTract2000>99</censusTract2000>
    <censusTract2010>99</censusTract2010>
    <cityCouncilDistrict>22</cityCouncilDistrict>
    <civilCourtDistrict>01</civilCourtDistrict>
    <coincidenceSegmentCount>1</coincidenceSegmentCount>
    <communityDistrict>401</communityDistrict>
    <communityDistrictBoroughCode>4</communityDistrictBoroughCode>
    <communityDistrictNumber>01</communityDistrictNumber>
    <communitySchoolDistrict>30</communitySchoolDistrict>
    <congressionalDistrict>12</congressionalDistrict>
    <crossStreetNamesFlagIn>E</crossStreetNamesFlagIn>
    <dcpPreferredLgc>01</dcpPreferredLgc>
    <dotStreetLightContractorArea>N</dotStreetLightContractorArea>
    <dynamicBlock>111</dynamicBlock>
    <electionDistrict>014</electionDistrict>
    <fireBattalion>49</fireBattalion>
    <fireCompanyNumber>312</fireCompanyNumber>
    <fireCompanyType>E</fireCompanyType>
    <fireDivision>14</fireDivision>
    <firstBoroughName>QUEENS</firstBoroughName>
    <firstStreetCode>49730001090</firstStreetCode>
    <firstStreetNameNormalized>RFK BRIDGE</firstStreetNameNormalized>
    <fromLionNodeId>0042193</fromLionNodeId>
    <fromPreferredLgcsFirstSetOf5>0176</fromPreferredLgcsFirstSetOf5>
    <genericId>0019558</genericId>
    <geosupportFunctionCode>1B</geosupportFunctionCode>
    <geosupportReturnCode>00</geosupportReturnCode>
    <geosupportReturnCode2>58</geosupportReturnCode2>
    <healthArea>0110</healthArea>
    <healthCenterDistrict>41</healthCenterDistrict>
    <highCrossStreetB5SC1>462090</highCrossStreetB5SC1>
    <highCrossStreetCode1>46209001</highCrossStreetCode1>
    <highCrossStreetName1>SHORE BOULEVARD</highCrossStreetName1>
    <highHouseNumberOfBlockfaceSortFormat>000000000AA</highHouseNumberOfBlockfaceSortFormat>
    <interimAssistanceEligibilityIndicator>I</interimAssistanceEligibilityIndicator>
    <legacySegmentId>0068651</legacySegmentId>
    <lionKeyBoroughCode>4</lionKeyBoroughCode>
    <lionKeyFaceCode>4647</lionKeyFaceCode>
    <lionKeyForVanityAddressBoroughCode>4</lionKeyForVanityAddressBoroughCode>
    <lionKeyForVanityAddressFaceCode>4647</lionKeyForVanityAddressFaceCode>
    <lionKeyForVanityAddressSequenceNumber>04040</lionKeyForVanityAddressSequenceNumber>
    <lionKeySequenceNumber>04040</lionKeySequenceNumber>
    <listOf4Lgcs>01</listOf4Lgcs>
    <lowCrossStreetB5SC1>197720</lowCrossStreetB5SC1>
    <lowCrossStreetB5SC2>462250</lowCrossStreetB5SC2>
    <lowCrossStreetCode1>19772001</lowCrossStreetCode1>
    <lowCrossStreetCode2>46225076</lowCrossStreetCode2>
    <lowCrossStreetName1>ROBERT F KENNEDY BRIDGE</lowCrossStreetName1>
    <lowCrossStreetName2>QN-NY CNTY SHORELINE</lowCrossStreetName2>
    <lowHouseNumberOfBlockfaceSortFormat>000000000AA</lowHouseNumberOfBlockfaceSortFormat>
    <message2>NON-ADDRESSABLE PLACE NAME, BRIDGE, TUNNEL OR MISC STRUCTURE NOT FOUND</message2>
    <nta>QN99</nta>
    <ntaName>park-cemetery-etc-Queens</ntaName>
    <numberOfCrossStreetB5SCsHighAddressEnd>1</numberOfCrossStreetB5SCsHighAddressEnd>
    <numberOfCrossStreetB5SCsLowAddressEnd>2</numberOfCrossStreetB5SCsLowAddressEnd>
    <numberOfCrossStreetsHighAddressEnd>1</numberOfCrossStreetsHighAddressEnd>
    <numberOfCrossStreetsLowAddressEnd>2</numberOfCrossStreetsLowAddressEnd>
    <numberOfStreetCodesAndNamesInList>03</numberOfStreetCodesAndNamesInList>
    <policePatrolBoroughCommand>6</policePatrolBoroughCommand>
    <policePrecinct>114</policePrecinct>
    <returnCode1a>58</returnCode1a>
    <returnCode1e>00</returnCode1e>
    <roadwayType>3</roadwayType>
    <sanitationCollectionSchedulingSectionAndSubsection>1D</sanitationCollectionSchedulingSectionAndSubsection>
    <sanitationDistrict>401</sanitationDistrict>
    <sanitationRecyclingCollectionSchedule>EF</sanitationRecyclingCollectionSchedule>
    <sanitationRegularCollectionSchedule>TF</sanitationRegularCollectionSchedule>
    <sanitationSnowPriorityCode>P</sanitationSnowPriorityCode>
    <segmentAzimuth>299</segmentAzimuth>
    <segmentIdentifier>0068651</segmentIdentifier>
    <segmentLengthInFeet>00047</segmentLengthInFeet>
    <segmentOrientation>4</segmentOrientation>
    <segmentTypeCode>G</segmentTypeCode>
    <sideOfStreetIndicator>R</sideOfStreetIndicator>
    <sideOfStreetOfVanityAddress>R</sideOfStreetOfVanityAddress>
    <specialAddressGeneratedRecordFlag>N</specialAddressGeneratedRecordFlag>
    <splitLowHouseNumber>000001000AA</splitLowHouseNumber>
    <stateSenatorialDistrict>12</stateSenatorialDistrict>
    <streetAttributeIndicator>B</streetAttributeIndicator>
    <streetCode1>19772001</streetCode1>
    <streetCode2>46225001</streetCode2>
    <streetCode6>46209001</streetCode6>
    <streetName1>ROBERT F KENNEDY BRIDGE</streetName1>
    <streetName1In>rfk bridge</streetName1In>
    <streetName2>BODY OF WATER</streetName2>
    <streetName6>SHORE BOULEVARD</streetName6>
    <streetStatus>2</streetStatus>
    <toLionNodeId>0042192</toLionNodeId>
    <toPreferredLgcsFirstSetOf5>01</toPreferredLgcsFirstSetOf5>
    <trafficDirection>T</trafficDirection>
    <underlyingHnsOnTrueStreet>000000000AA</underlyingHnsOnTrueStreet>
    <underlyingstreetCode>49730001</underlyingstreetCode>
    <workAreaFormatIndicatorIn>C</workAreaFormatIndicatorIn>
    <xCoordinate>1004824</xCoordinate>
    <xCoordinateHighAddressEnd>1004838</xCoordinateHighAddressEnd>
    <xCoordinateLowAddressEnd>1004815</xCoordinateLowAddressEnd>
    <xCoordinateOfCenterofCurvature>0000000</xCoordinateOfCenterofCurvature>
    <yCoordinate>0222858</yCoordinate>
    <yCoordinateHighAddressEnd>0222839</yCoordinateHighAddressEnd>
    <yCoordinateLowAddressEnd>0222880</yCoordinateLowAddressEnd>
    <yCoordinateOfCenterofCurvature>0000000</yCoordinateOfCenterofCurvature>
    <zipCode>11102</zipCode>
  </place>
</geosupportResponse>
```

## <a id="section-2.0">2.0 - Understanding Geoclient Return Codes</a>

There are two ways in which the Geoclient service communicates call status
information: HTTP status codes and Geosupport API return codes. The former
will always be provided; the only time in which latter will not be available
is when the service itself is down or system error prevents the application
from returning data to the client.

### <a id="section-2.1">2.1 - HTTP Status Codes</a>

The HTTP protocol implementation used by clients to call the Geoclient 
service will always provide status codes for all requests made to a 
valid Geoclient service URL. Full documentation of possible HTTP status 
codes are beyond the scope of this document, but 
[section 10](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html) of 
[RFC 2616](http://www.w3.org/Protocols/rfc2616/rfc2616.html) provides 
detailed information.

In brief, here are the most commonly returned HTTP status codes:

| HTTP Status Code | Meaning                                                                                                                                                                        |
| ----------------:| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
|              200 | The call successfully reached the Geoclient application. Application-level return codes contain information on success or failure of the call (see [section 2.2](#section-2.2) |
|              400 | A required query parameter is missing. See [section 1.2](#section-1.2) for information on call parameters.                                                                     |
|              404 | An incorrect URL has been used. There is no service mapped to it.                                                                                                              |
|              500 | The Geoclient service could not process the request due to an internal server error.                                                                                           | 

### <a id="section-2.2">2.2 - Geosupport Return Codes</a>

The following table decribes the various Geosupport return codes that are 
returned by the Geoclient service. Note that these codes come directly from 
the**Geosupport** application and they are documented in the UPG Section II.2.

<table>
<tr><th colspan="4">GEOSUPPORT SYSTEM RETURN CODES, REASON CODES AND MESSAGES</th></tr>
<tr><th>GRC</th><th>REASON CODE</th><th>FUNCTION (* = wildcard)</th><th>MESSAGE (LITERAL TEXT IN UPPERCASE, &lt;Variable values in angled brackets&gt;, [Comments in Square Brackets &amp; Mixed Case])</th></tr>
<tr><td colspan="4"></td></tr>
<tr><td>00</td><td></td><td>All</td><td>[Processing was unconditionally successful-no message issued]</td></tr>
<tr><th colspan="4">[GRC 01s are warnings]</th></tr>
<tr><td rowspan="35">01</td><td>1</td><td>1, 1A, 1B, 1E</td><td>ADDR NUMBER ALTERED: RANGE ASSUMED.USING DIGITS BEFORE DASH ONLY</td></tr>
<tr><td>2</td><td>1, 1A, 1B, 1E</td><td>ADDR NUMBER ALTERED: HYPHEN INSERTED</td></tr>
<tr><td>3</td><td>1, 1A, 1B, 1E</td><td>ADDR NUMBER ALTERED: HYPHEN DELETED</td></tr>
<tr><td>4</td><td>BB, BF</td><td>YOU HAVE REACHED THE &lt;FIRST or LAST&gt; STREET NAME IN THE BOROUGH OF &lt;boro. name&gt;</td></tr>
<tr><td>5</td><td>1, 1A, 1B, 1E</td><td>INPUT IS A COMPLEX. OUTPUT DATA MAY PERTAIN TO ONLY PART OF THE COMPLEX</td></tr>
<tr><td>6</td><td>1, 1A, 1B, 1E</td><td>OUTPUT STREET NAME/CODE DIFFER FROM INPUT</td></tr>
<tr><td>7</td><td>1, 1A, 1B, 1E</td><td>OUTPUT STREET NAME/CODE DIFFER FROM INPUT. ADDR NUMBER ALTERED: RANGE ASSUMED<br/>OUTPUT STREET NAME/CODE DIFFER FROM INPUT. ADDR NUMBER ALTERED: HYPHEN INSERTED<br/>OUTPUT STREET NAME/CODE DIFFER FROM INPUT. ADDR NUMBER ALTERED: HYPHEN DELETED<br/></td></tr>
<tr><td>8</td><td>1A, 1B</td><td>INPUT ADDRESS IS A PSEUDO-ADDRESS</td></tr>
<tr><td>9</td><td>1A, 1B</td><td>INPUT ADDRESS IS A PSEUDO-ADDRESS. ADDR NUMBER ALTERED: RANGE ASSUMED<br/>INPUT ADDRESS IS A PSEUDO-ADDRESS. ADDR NUMBER ALTERED: HYPHEN INSERTED<br/>INPUT ADDRESS IS A PSEUDO-ADDRESS. ADDR NUMBER ALTERED: HYPHEN DELETED<br/></td></tr>
<tr><td>A</td><td>1A, 1B, BL</td><td>LOT HAS MORE ITEMS THAN LISTED</td></tr>
<tr><td>B</td><td>1A, 1B</td><td>LOT HAS MORE ITEMS THAN LISTED.ADDR NUMBER ALTERED: RANGE ASSUMED<br/>LOT HAS MORE ITEMS THAN LISTED.ADDR NUMBER ALTERED: HYPHEN INSERTED<br/>LOT HAS MORE ITEMS THAN LISTED.ADDR NUMBER ALTERED: HYPHEN DELETED<br/></td></tr>
<tr><td rowspan="2">C</td><td rowspan="2">1, 1A, 1B, 1E</td><td>IN MARBLE HILL B LEGAL BORO IS MANHATTAN<br/>IN MARBLE HILL - LEGAL BORO IS MANHATTAN. ADDR NUMBER ALTERED: RANGE ASSUMED<br/>IN MARBLE HILL - LEGAL BORO IS MANHATTAN. ADDR NUMBER ALTERED: HYPHEN INSERTED<br/>IN MARBLE HILL - LEGAL BORO IS MANHATTAN. ADDR NUMBER ALTERED: HYPHEN DELETED<br/></td></tr>
<tr><td>ON RIKERS ISL - LEGAL BORO IS THE BRONX<br/>ON RIKERS ISL - LEGAL BORO IS THE BRONX. ADDR NUMBER ALTERED: RANGE ASSUMED<br/>ON RIKERS ISL - LEGAL BORO IS THE BRONX. ADDR NUMBER ALTERED: HYPHEN INSERTED<br/>ON RIKERS ISL - LEGAL BORO IS THE BRONX. ADDR NUMBER ALTERED: HYPHEN DELETED<br/></td></tr>
<tr><td>D</td><td>1*, 2, 3*</td><td>PARTIAL STREET NAME USED TO MEET SNL REQUIREMENT</td></tr>
<tr><td rowspan="3">E</td><td>1, 1B, 1E</td><td>OUTPUT ADDRESS RANGE IS SPLIT BY SCHOOL DISTRICT BOUNDARY</td></tr>
<tr><td rowspan="2">1B, 1E</td><td>OUTPUT ADDRESS RANGE IS SPLIT BY ELECTION DISTRICT BOUNDARY</td></tr>
<tr><td>OUTPUT ADDRESS RANGE IS SPLIT BY SCHOOL &amp; ELECTION DISTRICT BOUNDARIES</td></tr>
<tr><td>F</td><td>BN</td><td>THIS BIN IS TEMPORARY AND WILL BE REPLACED IN THE FUTURE</td></tr>
<tr><td>G</td><td>1, 1A, 1B, 1E</td><td>ADDR NUMBER ALTERED: RANGE ASSUMED. NOTE: INCONSISTENT ODD/EVEN ADDR RANGE</td></tr>
<tr><td>H</td><td>2, 3S</td><td>THESE STREETS INTERSECT ONCE-COMPASS DIRECTION IGNORED</td></tr>
<tr><td>I</td><td>1, 1A, 1B, 1E</td><td>INPUT IS NON-ADDRESSABLE PLACE NAME - ADDRESS NUMBER IGNORED</td></tr>
<tr><td rowspan="3">J [not impl.]</td><td>1, 1A, 1B, 1E, 2, 3*</td><td>&lt;Full street name including EAST or WEST as first word&gt; ASSUMED<br/>[An input Bronx or Manhattan street name is missing EAST or WEST as its first word, and the intended full street name is unambiguous]</td></tr>
<tr><td>2, 3*</td><td>&lt;Full street name&gt; AND &lt;other full street name&gt; ASSUMED<br/>[Two input Bronx or Manhattan street names are missing EAST or WEST as their first words, and the intended names are unambiguous]</td></tr>
<tr><td>3*</td><td> &lt;Full street name&gt;, &lt;second full street name&gt; AND &lt;third full street name&gt; ASSUMED<br/>[Three input Bronx or Manhattan street names are missing EAST or WEST as their first words, and the intended names are unambiguous]</td></tr>
<tr><td>K</td><td>1, 1A, 1B, 1E</td><td>EMBEDDED BLANK IN ADDRESS NUMBER HAS BEEN REPLACED WITH A HYPHEN</td></tr>
<tr><td>L or R</td><td>3, 3C</td><td>&lt;LEFT or RIGHT&gt; SIDE OF SEGMENT IS IN &lt;BROOKLYN or QUEENS&gt;<br/>or<br/>&lt;LEFT or RIGHT&gt; SIDE OF SEGMENT IS IN &lt;NASSAU or WESTCHESTER&gt; - NO INFO RETURNED FOR THAT SIDE</td></tr>
<tr><td>M</td><td>1, 1A, 1B, 1E</td><td>INPUT ADDRESS NUMBER IS ZERO</td></tr>
<tr><td>N</td><td>1, 1A, 1B, 1E, 2, 3*, D*</td><td>STREET NAME(S) AND STREET CODE(S) BOTH SPECIFIED AS INPUT - &lt;CODE(S) or NAMES&gt; IGNORED</td></tr>
<tr><td>O</td><td>1, 1A, 1B, 1E</td><td>CAUTION: &lt;BLOCK FACE or ADDR RANGE&gt; CONTAINS OUT-OF-SEQUENCE AND/OR OPPOSITE PARITY ADDRESSES</td></tr>
<tr><td>P</td><td>1, 1B, 1E</td><td>IRREGULARLY CURVED STREET SEGMENT - SPATIAL COORDINATES RETURNED AS BLANKS</td></tr>
<tr><td>Q</td><td>3</td><td>THESE STREETS INVOLVE A `DOGLEG' - SHORTEST STRETCH PROVIDED</td></tr>
<tr><td>S</td><td>1, 1A, 1B, 1E</td><td>&lt;HNI or HNS&gt; DISPLAY ADDRESS NUMBER BOTH SPECIFIED AS INPUT-&lt;HNI or HNS&gt; IGNORED</td></tr>
<tr><td>T</td><td>2</td><td>NON-INTERSECTION NAME IGNORED</td></tr>
<tr><td>U</td><td>3S</td><td>STRETCH HAS MORE ITEMS THAN LISTED</td></tr>
<tr><td>V</td><td>1, 1B, 1E</td><td>&lt;Normalized input address number&gt; &lt;Norm'd input street name&gt; IS ON &lt;LEFT or RIGHT&gt; SIDE OF &lt;True street name&gt;<br/>[This warning is issued for vanity addresses, NAPs other than complexes (for which an underlying address is not available), and certain alternative addresses known as type `B' addresses.]<br/>or<br/>&lt;Address number&gt; &lt;Street name&gt; IS THE UNDERLYING ADDRESS OF &lt;Normalized input NAP&gt;<br/>[This warning is issued for NAPs other than complexes, for which an underlying address is available.]</td></tr>
<tr><th colspan="4">[GRCs greater than 01 are rejects or errors]</th></tr>
<tr><td>02</td><td></td><td>2</td><td>THESE STREETS INTERSECT TWICE-COMPASS DIRECTION REQUIRED</td></tr>
<tr><td>03</td><td>3 thru 9</td><td>2</td><td>THESE STREETS INTERSECT MORE THAN TWICE-CANNOT BE PROCESSED<br/>[Reason Code value indicates number of times the streets intersect. The value `9' signifies 9 or more.]</td></tr>
<tr><td>04</td><td></td><td>1A, 1B, BL</td><td>1A/BL VERSION SWITCH INVALID - MUST BE S. ONLY STANDARD IS SUPPORTED</td></tr>
<tr><td>05</td><td></td><td>3S</td><td>FOR FUNCTION 3S, ONLY FIRST BOROUGH CODE IS PERMITTED</td></tr>
<tr><td>07</td><td></td><td>1, 1A, 1B, 1E</td><td>FOR A NAME OF A COMPLEX, 5-DIGIT STREET CODE INPUT IS NOT PERMITTED</td></tr>
<tr><td>08</td><td></td><td>All but B*</td><td>INVALID STREET NAME NORMALIZATION FORMAT FLAG - MUST BE BLANK, C OR S</td></tr>
<tr><td>09</td><td></td><td>3C</td><td>&lt;Compass direction&gt; SIDE OF STREET SEGMENT IS NOT IN &lt;borough name&gt;</td></tr>
<tr><td>10</td><td></td><td>All but B*</td><td>INVALID SNL VALUE - MUST BE BETWEEN 4 AND 32 INCLUSIVE</td></tr>
<tr><td>11</td><td></td><td>1*, 2, 3*</td><td>&lt;Street name&gt; NOT RECOGNIZED. THERE ARE NO SIMILAR NAMES<br/><em><u>As of Version 10.0 this message is used for batch in addition to CICS</u></em></td></tr>
<tr><td>12</td><td></td><td>2</td><td>INTERSECTION NAME NOT FOUND</td></tr>
<tr><td rowspan="15">13</td><td>1</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; CONTAINS AN INVALID CHARACTER &lt;character&gt; IN POSITION &lt;position number&gt;</td></tr>
<tr><td>2</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; HAS MORE THAN 3 DIGITS AFTER DASH</td></tr>
<tr><td>3</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; HAS TOO MANY DASHES</td></tr>
<tr><td>4</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; HAS NO DIGITS AFTER THE DASH</td></tr>
<tr><td>6</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; HAS TOO MANY DIGITS (MORE THAN 5)</td></tr>
<tr><td>7</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; IS NOT COMPLETE AS ENTERED</td></tr>
<tr><td>8</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; - PORTION AFTER HYPHEN EXCEEDS ALLOWABLE MAXIMUM</td></tr>
<tr><td>9</td><td>1, 1A, 1B, 1E, D*</td><td>ADDRESS NBR &lt;hse nr value&gt; INVALID INTERNAL FORMAT</td></tr>
<tr><td>A</td><td>1, 1A, 1B, 1E, D*</td><td>ADDRESS NBR &lt;value&gt; HAS AN UNKNOWN OR INVALID SUFFIX/ENDING</td></tr>
<tr><td>B</td><td>1, 1A, 1B, 1E</td><td>INPUT CONTAINS NO ADDRESS NUMBER</td></tr>
<tr><td>C</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR &lt;value&gt; HAS AN EMBEDDED BLANK</td></tr>
<tr><td>D</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NBR HAS INVALID FORMAT FOR EDGEWATER PARK</td></tr>
<tr><td>E</td><td>1, 1A, 1B, 1E</td><td>THIS STREET HAS HYPHENATED ADDRESS NBRS ONLY. TRY &lt;address nbr with hyphen inserted to left of penultimate digit&gt; OR &lt;address nbr with hyphen inserted to left of plusquepenultimate digit&gt;</td></tr>
<tr><td>F</td><td>1, 1A, 1B, 1E</td><td>THIS STREET HAS UNHYPHENATED ADDRESS NBRS ONLY. TRY &lt;digits of address number to left of dash only&gt; OR &lt;digits to left and right of dash concatenated without the dash&gt;</td></tr>
<tr><td>G</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NUMBER HAS INVALID HYPHENATION FOR THIS STREET<br/>[Input address number is an unhyphenated 2-digit number, but the input street has hyphenated address numbers only.]</td></tr>
<tr><td>14</td><td></td><td>3S</td><td>INPUT DOES NOT DEFINE A STREET STRETCH, SINCE INPUT INTERSECTIONS ARE IDENTICAL</td></tr>
<tr><td>15</td><td></td><td>All but B*</td><td>STREET NAME CANNOT BE NORMALIZED</td></tr>
<tr><td>16</td><td></td><td>1*</td><td>STREET NAME IS MISSING</td></tr>
<tr><td>17</td><td></td><td>All</td><td>BOROUGH CODE IS MISSING</td></tr>
<tr><td>18</td><td></td><td>BL</td><td>TAX BLOCK NOT NUMERIC</td></tr>
<tr><td>19</td><td></td><td>BL</td><td>TAX LOT NOT NUMERIC</td></tr>
<tr><td>20</td><td></td><td>BN</td><td>BUILDING IDENTIFICATION NUMBER (BIN) IS MISSING</td></tr>
<tr><td>21</td><td></td><td>BN</td><td>BUILDING IDENTIFICATION NUMBER (BIN) NOT FOUND</td></tr>
<tr><td>22</td><td></td><td>BN</td><td>INVALID BIN FORMAT: NON-NUMERIC, FIRST DIGIT NOT 1-5 OR REST OF DIGITS ALL ZERO</td></tr>
<tr><td>23</td><td></td><td>BN</td><td>TEMPORARY DEPARTMENT OF BUILDINGS BIN: EXISTS ONLY IN D.O.B FILES</td></tr>
<tr><td>24</td><td></td><td>3*</td><td>ON STREET IS MISSING</td></tr>
<tr><td>25</td><td></td><td>2, 3*</td><td>CROSS STREET 1 IS MISSING </td></tr>
<tr><td>26</td><td></td><td>2, 3*</td><td>CROSS STREET 2 IS MISSING </td></tr>
<tr><td>27</td><td></td><td>All</td><td>INVALID WORK AREA FORMAT INDICATOR - MUST BE C OR BLANK </td></tr>
<tr><td>28</td><td></td><td>1,1A, 1B, 1E</td><td>A PARTIAL STREET NAME MAY NOT BE USED IN A FREE-FORM ADDRESS</td></tr>
<tr><td>29</td><td></td><td>1,1A, 1B, 1E, 3*</td><td>INTERSECTION &lt;INTERSECTION NAME&gt; MAY NOT SERVE AS ON-STREET</td></tr>
<tr><td>30</td><td></td><td>2</td><td>&lt;STREET NAME&gt; IS NOT PART OF &lt;INTERSECTION NAME&gt;</td></tr>
<tr><td>31-37</td><td></td><td></td><td><em><u>As of Version 10.0 GRC 31 through GRC 37 are replaced by GRC 11 and GRC EE. See descriptions of GRC 11 and GRC EE.</u></em></td></tr>
<tr><td>38</td><td></td><td>3S</td><td>&lt;Compass direction value&gt; IS AN INVALID COMPASS DIRECTION VALUE FOR &lt;FIRST or SECOND&gt; INPUT INTERSECTION</td></tr>
<tr><td>39</td><td></td><td>2, 3C</td><td>INVALID COMPASS DIRECTION VALUE - MUST BE N, S, E OR W</td></tr>
<tr><td>40</td><td></td><td>2, 3C</td><td>COMPASS DIRECTION VALUE IS INVALID FOR THIS INPUT LOCATION</td></tr>
<tr><td>41</td><td></td><td>1, 1A, 1B, 1E</td><td>THIS STREET HAS NO ADDRESSES</td></tr>
<tr><td rowspan="3">42</td><td>blank</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NUMBER OUT OF RANGE</td>  </tr>
<tr><td>1</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NUMBER OUT OF RANGE. CORRECT DIGITS OR INSERT HYPHEN AS &lt;AB-CD&gt; OR &lt;A-BCD&gt;<br/>[where input was of the form ABCD]</td>  </tr>
<tr><td>2</td><td>1, 1A, 1B, 1E</td><td>ADDRESS NUMBER OUT OF RANGE. CORRECT DIGITS OR TRY &lt;AB&gt; OR &lt;ABCD&gt;<br/>[where input was of the form AB-CD]</td>  </tr>
<tr><td>44</td><td></td><td>3C</td><td>INPUT DOES NOT DEFINE A BLOCK FACE</td></tr>
<tr><td>45</td><td></td><td>3</td><td>INPUT DOES NOT DEFINE A STREET SEGMENT</td></tr>
<tr><td>46</td><td></td><td>3, 3C </td><td>STREET COMBINATION NOT UNIQUE<br/>[The input is ambiguous, i.e., it describes more than one valid street segment.]</td>  </tr>
<tr><td>47</td><td></td><td>1, 1A, 1B, 1E</td><td>INVALID HNL VALUE - MUST BE BETWEEN 12 AND 16 INCLUSIVE</td></tr>
<tr><td>48</td><td></td><td>1, 1A, 1B, 1E</td><td>INVALID HOUSE NUMBER JUSTIFICATION VALUE - MUST BE L, R OR BLANK</td></tr>
<tr><td>49</td><td></td><td>1, 1A, 1B, 1E</td><td>ADDRESS NUMBER CANNOT BE NORMALIZED WITHIN REQUESTED HNL</td></tr>
<tr><td>50</td><td>1 thru 4</td><td>1, 1A, 1B, 1E, 2, 3*</td><td>&lt;Input street name&gt; IS AN INVALID STREET NAME FOR THIS LOCATION<br/>[The Reason Code indicates the number of valid street names returned in the Similar Names list.]</td></tr>
<tr><td>51</td><td></td><td>1, 1B, 1E, 2, 3, 3C</td><td>CROSS STREET NAMES FLAG MUST BE E OR BLANK</td></tr>
<tr><td>52</td><td></td><td>All but 1, 1E, 2, 3, 3C</td><td>CROSS STREET NAMES OPTION IS INVALID FOR THIS FUNCTION</td></tr>
<tr><td>55</td><td></td><td>2,3*</td><td>NON-ADDRESSABLE PLACE NAME PROCESSING IS NOT AVAILABLE FOR THIS FUNCTION</td></tr>
<tr><td>56</td><td></td><td>1B, 1E</td><td>ADDRESS IS SPLIT AMONG MULTIPLE ELECTION DISTRICTS. ADDRESS NBR SUFFIX REQUIRED<br/>[The input address is associated with more than one Election District (ED). Function 1E requires an address number suffix to be included with this address to identify a portion of the building specific to one ED.]</td></tr>
<tr><td>57</td><td></td><td></td><td><em><u>As of Version 10.0 GRC 57 is replaced by GRC 67. See description of GRC 67.</u></em></td></tr>
<tr><td>58</td><td></td><td>1, 1A, 1B, 1E</td><td>NON-ADDRESSABLE PLACE NAME NOT FOUND</td></tr>
<tr><td>59</td><td></td><td>1*,2,3*</td><td>STREET NAME CANNOT BE NORMALIZED WITHIN REQUESTED SNL</td></tr>
<tr><td>61</td><td></td><td>3S</td><td>STREET STRETCH NOT FOUND</td></tr>
<tr><td>62</td><td></td><td>2, 3S</td><td>&lt;Street name&gt; & &lt;other street name&gt; DO NOT INTERSECT</td></tr>
<tr><td>64</td><td></td><td>1, 1A, 1B, 1E, 2, 3*, D*</td><td>STREET CODE NOT FOUND</td></tr>
<tr><td>65</td><td></td><td>1,1E</td><td>INVALID ROADBED REQUEST SWITCH. MUST BE R OR BLANK</td></tr>
<tr><td>66</td><td></td><td>3S</td><td>&lt;Street name&gt; & &lt;other street name&gt; INTERSECT MORE THAN TWICE-CANNOT BE PROCESSED</td></tr>
<tr><td>67</td><td>E, G, P, R, S, T</td><td>All batch only</td><td>ERROR ACCESSING GEOSUPPORT FILE: &lt;file name&gt; NOTIFY SYSTEM SUPPORT<br/>[This can be an installation error or a system error Notify System Support.]<br/><em><u>As of Version 10.0 this message is used for batch in addition to CICS</u></em></td></tr>
<tr><td>68</td><td></td><td>3S</td><td>&lt;Street name&gt; & &lt;other street name&gt; INTERSECT TWICE-COMPASS DIRECTION REQ'D</td></tr>
<tr><td>73</td><td></td><td>1A, 1B, BL</td><td>LEGACY VERSION OF FUNCTIONS 1A AND BL IS DISCONTINUED. SEE TECH BULLETIN 05-1 </td></tr>
<tr><td>75</td><td></td><td>1, 1A, 1B, 1E</td><td>DUPLICATE ADDRESS-USE &lt;pseudo-streetname1&gt; OR &lt;pseudo-streetname2&gt;</td></tr>
<tr><td>76</td><td></td><td>All but 1, 1E</td><td>ROADBED REQUEST SWITCH NOT IMPLEMENTED FOR THIS FUNCTION </td></tr>
<tr><td>77</td><td></td><td>BL</td><td>TAX LOT NOT FOUND</td></tr>
<tr><td rowspan="5">88</td><td>blank</td><td>All</td><td>GEOSUPPORT SYSTEM ERROR. NOTIFY DCP/GSS USER SUPPORT<br/>[An internal Geosupport problem, not a user error.]</td></tr>
<tr><td>1-8, C-H</td><td>All</td><td>GEOSUPPORT SYSTEM ERROR. NOTIFY DCP/GSS USER SUPPORT &amp; REPORT REASON CODE = &lt;value&gt;<br/>[An internal Geosupport problem, not a user error.]</td></tr>
<tr><td>9</td><td>All, CICS only</td><td>CICS ERROR. NOTIFY DATA CENTER TECHNICAL SUPPORT<br/>[A system error, not a user error.]</td></tr>
<tr><td>A</td><td>All</td><td>MODULE HAS NOT LOADED. NOTIFY TECHNICAL SUPPORT<br/>[A system error, not a user error.]</td></tr>
<tr><td>B</td><td>All</td><td>SYSTEM ERROR. NOTIFY TECHNICAL SUPPORT &amp; REPORT REASON CODE = B<br/>[An internal Geosupport problem or a system error, not user error.]</td></tr>
<tr><td>89</td><td></td><td>1, 1B, 1E, 2, 3, 3C, 3S, BN</td><td>LONG WORK-AREA-2 OPTION IS INVALID FOR THIS FUNCTION</td></tr>
<tr><td>90</td><td></td><td>1, 1A, 1B, 1E, 3, BL</td><td>LONG WORK-AREA-2 FLAG MUST BE L OR BLANK</td></tr>
<tr><td>96</td><td></td><td>All, CICS only</td><td>AN I/O ERROR HAS OCCURRED. TRY AGAIN</td></tr>
<tr><td>97</td><td></td><td>BB, BF</td><td>INPUT IS BEYOND THE LAST STREET NAME IN THE BOROUGH OF &lt;borough name&gt;</td></tr>
<tr><td>98</td><td></td><td>All</td><td>NO INPUT DATA RECEIVED</td></tr>
<tr><td>99</td><td></td><td>All</td><td>INVALID BOROUGH CODE. MUST BE 1, 2, 3, 4 OR 5</td></tr>
<tr><td rowspan="2">EE</td><td>1</td><td>1*, 2, 3*</td><td>&lt;Street name&gt; NOT RECOGNIZED. IS IT &lt;similar street name&gt;?<br/>[Issued when there is precisely one similar name.]<br/><em><u>As of Version 10.0 this message is used for batch in addition to CICS</u></em></td></tr>
<tr><td>2 thru 9, A</td><td>1*, 2, 3*</td><td>&lt;Street name&gt; NOT RECOGNIZED. THERE ARE &lt;number&gt; SIMILAR NAMES<br/>[Issued when there is more than one similar name. Reason Code indicates number of similar names. Reason Code 'A' signifies 10 similar names. The similar names are returned in WA1.]<br/><em><u>As of Version 10.0 this message is used for batch in addition to CICS</u></em></td></tr>
<tr><td>??</td><td></td><td>N/A</td><td>INVALID FUNCTION CODE</td></tr>
</table>  	


## <a id="section-3.0">3.0 - Geoclient Function Reference</a>

This section describes the data returned by each function.

## <a id="section-4.0">4.0 - Geoclient Data Dictionary</a> 

This section describes the set of possible data elements that are returned
by each call type. It also provides a brief description of each element which
includes format and coded value information.


