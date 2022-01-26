/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.service.invoker;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.service.domain.Version;

public interface GeosupportService
{
    Map<String, Object> callFunctionAP(String houseNumber, String street, String borough, String zip);
    Map<String, Object> callFunction1B(String houseNumber, String street, String borough, String zip);
    Map<String, Object> callFunction2(String crossStreetOne, String boroughCrossStreetOne, String crossStreetTwo, String boroughCrossStreetTwo, String compassDirection);
    Map<String, Object> callFunction3(String onStreet, String boroughOnStreet, String crossStreetOne, String boroughCrossStreetOne, String crossStreetTwo, String boroughCrossStreetTwo, String compassDirection);
    Map<String, Object> callFunctionBL(String borough, String block, String lot);
    Map<String, Object> callFunctionBN(String bin);
    Map<String, Object> callFunctionD(String streetCodeOne, String streetCodeTwo, String streetCodeThree, Integer length, String format);
    Map<String, Object> callFunctionDG(String streetCodeOne, String streetCodeTwo, String streetCodeThree, Integer length, String format);
    Map<String, Object> callFunctionDN(String streetCodeOne, String streetCodeTwo, String streetCodeThree, Integer length, String format);
    Map<String, Object> callFunctionHR();
    Map<String, Object> callGeosupport(Map<String, Object> params);
    Map<String, Object> callFunctionN(String name, Integer length, String format);
    Map<String, Object> callStreetNameFunction(String streetCodeOne, String streetCodeTwo, String streetCodeThree, Integer length, String format);
    Version version();
}
