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
package gov.nyc.doitt.gis.geoclient.service.search.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.mapper.Mapper;
import gov.nyc.doitt.gis.geoclient.service.search.CountyResolver;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.BlockfaceRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.IntersectionRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.PlaceRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.Request;

public class SimilarNamesTaskBuilder extends TaskBuilderSupport {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimilarNamesTaskBuilder.class);

    public SimilarNamesTaskBuilder(CountyResolver countyResolver, GeosupportService geosupportService, Mapper<ResponseStatus> mapper) {
        super(countyResolver, geosupportService, mapper);
    }

    public List<SearchTask> getSearchTasks(SearchResult searchResult) {
        List<SearchTask> searchTasks = new ArrayList<>();
        List<Search> previousSearches = searchResult.inputForSubSearches();
        for (Search search : previousSearches) {
            searchTasks.addAll(buildSearchTasks(searchResult.getSearchPolicy(), search));
        }
        return searchTasks;
    }

    protected List<SearchTask> buildSearchTasks(SearchPolicy searchPolicy, Search search) {
        List<SearchTask> searchTasks = new ArrayList<>();
        for (String similarName : search.getSimilarNames()) {

            SearchTask task = null;
            Request req = search.getRequest();
            if (AddressRequest.class.equals(req.getClass())) {
                task = buildSearchTask(searchPolicy, (AddressRequest) req, similarName);
            } else if (BlockfaceRequest.class.equals(req.getClass())) {
                task = buildSearchTask(searchPolicy, search, (BlockfaceRequest) req, similarName);
            } else if (IntersectionRequest.class.equals(req.getClass())) {
                task = buildSearchTask(searchPolicy, search, (IntersectionRequest) req, similarName);
            } else if (PlaceRequest.class.equals(req.getClass())) {
                task = buildSearchTask(searchPolicy, (PlaceRequest) req, similarName);
            }

            if (task != null) {
                searchTasks.add(task);
            }
        }
        return searchTasks;
    }

    protected SearchTask buildSearchTask(SearchPolicy searchPolicy, AddressRequest request, String similarName) {
        if (searchPolicy.isSimilarName(request.getStreet(), similarName)) {
            AddressRequest newRequest = new AddressRequest(request);
            newRequest.incrementLevel();
            newRequest.setStreetInputValue(new InputValue(TokenType.STREET_NAME, similarName));
            LOGGER.debug("Created {} from {}.", newRequest, request);
            return new AddressSearchTask(newRequest, geosupportService, mapper);
        }
        return null;
    }

    protected SearchTask buildSearchTask(SearchPolicy searchPolicy, Search search, BlockfaceRequest request,
            String similarName) {
        String onStreet = request.getOnStreet();
        BlockfaceRequest newRequest = new BlockfaceRequest(request);
        newRequest.incrementLevel();
        if (search.responseMessageAppliesTo(onStreet) && searchPolicy.isSimilarName(onStreet, similarName)) {
            newRequest.setOnStreetInputValue(new InputValue(TokenType.ON_STREET, similarName));
            return new BlockfaceSearchTask(newRequest, geosupportService, mapper);
        }
        String crossStreetOne = request.getCrossStreetOne();
        if (search.responseMessageAppliesTo(crossStreetOne)
                && searchPolicy.isSimilarName(crossStreetOne, similarName)) {
            newRequest.setCrossStreetOneInputValue(new InputValue(TokenType.CROSS_STREET_ONE, similarName));
            return new BlockfaceSearchTask(newRequest, geosupportService, mapper);
        }
        String crossStreetTwo = request.getCrossStreetTwo();
        if (search.responseMessageAppliesTo(crossStreetTwo)
                && searchPolicy.isSimilarName(crossStreetTwo, similarName)) {
            newRequest.setCrossStreetTwoInputValue(new InputValue(TokenType.CROSS_STREET_TWO, similarName));
            return new BlockfaceSearchTask(newRequest, geosupportService, mapper);
        }
        return null;
    }

    protected SearchTask buildSearchTask(SearchPolicy searchPolicy, Search search, IntersectionRequest request,
            String similarName) {
        IntersectionRequest newRequest = new IntersectionRequest(request);
        newRequest.incrementLevel();
        String crossStreetOne = request.getCrossStreetOne();
        if (search.responseMessageAppliesTo(crossStreetOne)
                && searchPolicy.isSimilarName(crossStreetOne, similarName)) {
            newRequest.setCrossStreetOneInputValue(new InputValue(TokenType.CROSS_STREET_ONE, similarName));
            return new IntersectionSearchTask(newRequest, geosupportService, mapper);
        }
        String crossStreetTwo = request.getCrossStreetTwo();
        if (search.responseMessageAppliesTo(crossStreetTwo)
                && searchPolicy.isSimilarName(crossStreetTwo, similarName)) {
            newRequest.setCrossStreetTwoInputValue(new InputValue(TokenType.CROSS_STREET_TWO, similarName));
            return new IntersectionSearchTask(newRequest, geosupportService, mapper);
        }
        return null;
    }

    protected SearchTask buildSearchTask(SearchPolicy searchPolicy, PlaceRequest request, String similarName) {
        if (searchPolicy.isSimilarName(request.getStreet(), similarName)) {
            PlaceRequest newRequest = new PlaceRequest(request);
            newRequest.incrementLevel();
            newRequest.setStreetInputValue(new InputValue(TokenType.STREET_NAME, similarName));
            LOGGER.debug("Created {} from {}.", newRequest, request);
            return new PlaceSearchTask(newRequest, geosupportService, mapper);
        }
        return null;
    }
}
