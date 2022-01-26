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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.mapper.Mapper;
import gov.nyc.doitt.gis.geoclient.service.search.CountyResolver;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.service.search.ValueResolution;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.BblRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.BinRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.BlockfaceRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.IntersectionRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.PlaceRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.Request;
import gov.nyc.doitt.gis.geoclient.service.search.request.RequestUtils;

public class DefaultInitialSearchTaskBuilder extends TaskBuilderSupport implements InitialSearchTaskBuilder {
    public DefaultInitialSearchTaskBuilder(CountyResolver countyResolver, GeosupportService geosupportService,
            Mapper<ResponseStatus> mapper) {
        super(countyResolver, geosupportService, mapper);
    }

    @Override
    public List<SearchTask> getSearchTasks(SearchPolicy searchPolicy, LocationTokens locationTokens) {
        List<SearchTask> searches = new ArrayList<>();
        for (Chunk chunk : locationTokens.getChunks()) {
            switch (chunk.getType()) {
            case ADDRESS:
                return initialSearchTasks(AddressRequest.class, AddressSearchTask.class, locationTokens);
            case BBL:
                return initialSearchTasks(BblRequest.class, BblSearchTask.class, locationTokens);
            case BIN:
                return initialSearchTasks(BinRequest.class, BinSearchTask.class, locationTokens);
            case BLOCKFACE:
                return initialSearchTasks(BlockfaceRequest.class, BlockfaceSearchTask.class, locationTokens);
            case INTERSECTION:
                return initialSearchTasks(IntersectionRequest.class, IntersectionSearchTask.class, locationTokens);
            case UNRECOGNIZED:
                return initialSearchTasks(PlaceRequest.class, PlaceSearchTask.class, locationTokens);
            default:
                break;
            }
        }
        return searches;
    }

    protected <R extends Request, T extends SearchTask> List<SearchTask> initialSearchTasks(Class<R> requestType,
            Class<T> taskType, LocationTokens locationTokens) {
        List<SearchTask> tasks = new ArrayList<>();
        if (requestType.equals(BinRequest.class)) {
            // BIN request which does not require a borough
            tasks.add(new BinSearchTask(RequestUtils.initialRequest(requestType, locationTokens, null),
                    geosupportService, mapper));

        } else {
            // All other requests
            try {
                ValueResolution countyResolution = this.countyResolver.resolve(locationTokens);
                for (InputValue countyInputValue : countyResolution.resolved()) {
                    tasks.add(ConstructorUtils.invokeConstructor(taskType,
                            RequestUtils.initialRequest(requestType, locationTokens, countyInputValue),
                            geosupportService, mapper));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e) {
                // TODO Need a more meaningful exception here
                throw new RuntimeException(e.getCause());
            }
        }
        return tasks;
    }
}
