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
package gov.nyc.doitt.gis.geoclient.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue;
import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;

public class Fixtures
{
    public final Input input;
    public final List<Chunk> chunks;
    public final LocationTokens locationTokens;
    public final SearchPolicy searchPolicy;
    public final SearchResult searchResult;
    public final AddressRequest requestLevelZero;
    public final AddressRequest requestLevelOne;
    public final AddressRequest requestLevelTwo;
    public final AddressRequest requestLevelThree;
    public final AddressRequest requestLevelFour;
    public final Response responseSuccess;
    public final Response responseReject;
    public final ResponseStatus successStatus;
    public final ResponseStatus rejectStatus;
    public final Map<String, Object> geocodes;

    public Fixtures()
    {
        super();
        this.input = new Input("1-junit-test","59 Maiden Lane, Manhattan");
        this.chunks = new ArrayList<>();
        this.searchPolicy = new SearchPolicy();
        this.locationTokens = new LocationTokens(input, chunks);
        this.searchResult = new SearchResult(this.searchPolicy, locationTokens);
        this.requestLevelZero = new AddressRequest();
        this.requestLevelZero.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL);
        this.requestLevelOne = new AddressRequest();
        this.requestLevelOne.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 1);
        this.requestLevelTwo = new AddressRequest();
        this.requestLevelTwo.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 2);
        this.requestLevelThree = new AddressRequest();
        this.requestLevelThree.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 3);
        this.requestLevelFour = new AddressRequest();
        this.requestLevelFour.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 3);

        GeosupportReturnCode grc00 = new GeosupportReturnCode();
        grc00.setReturnCode(ReturnCodeValue.SUCCESS.value());
        successStatus = new ResponseStatus();
        successStatus.setGeosupportReturnCode(grc00);
        this.responseSuccess = new Response(successStatus, null);

        GeosupportReturnCode grcEE = new GeosupportReturnCode();
        grcEE.setReturnCode("EE");
        rejectStatus = new ResponseStatus();
        rejectStatus.setGeosupportReturnCode(grcEE);
        this.responseReject = new Response(rejectStatus, null);
        geocodes = new TreeMap<>();
    }

}
