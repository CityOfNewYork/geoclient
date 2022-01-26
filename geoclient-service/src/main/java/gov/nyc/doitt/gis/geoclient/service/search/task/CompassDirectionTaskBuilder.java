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

import gov.nyc.doitt.gis.geoclient.api.InputParam;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.mapper.Mapper;
import gov.nyc.doitt.gis.geoclient.service.search.CountyResolver;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.request.IntersectionRequest;

public class CompassDirectionTaskBuilder extends TaskBuilderSupport implements SpawnedSearchTaskBuilder {
    public CompassDirectionTaskBuilder(CountyResolver countyResolver, GeosupportService geosupportService,
            Mapper<ResponseStatus> mapper) {
        super(countyResolver, geosupportService, mapper);
    }

    @Override
    public List<SearchTask> getSearchTasks(SearchResult searchResult) {
        List<SearchTask> tasks = new ArrayList<>();
        for (Search search : searchResult.inputForSubSearches()) {
            if (search.getResponse().isCompassDirectionRequired()) {
                tasks.add(new IntersectionSearchTask(newIntersectionRequest((IntersectionRequest) search.getRequest(),
                        InputParam.COMPASS_DIR_NORTH_VALUE), geosupportService, mapper));
                tasks.add(new IntersectionSearchTask(newIntersectionRequest((IntersectionRequest) search.getRequest(),
                        InputParam.COMPASS_DIR_SOUTH_VALUE), geosupportService, mapper));
                tasks.add(new IntersectionSearchTask(newIntersectionRequest((IntersectionRequest) search.getRequest(),
                        InputParam.COMPASS_DIR_EAST_VALUE), geosupportService, mapper));
                tasks.add(new IntersectionSearchTask(newIntersectionRequest((IntersectionRequest) search.getRequest(),
                        InputParam.COMPASS_DIR_WEST_VALUE), geosupportService, mapper));
            }
        }
        return tasks;
    }

    private IntersectionRequest newIntersectionRequest(IntersectionRequest request, String compassDirection) {
        IntersectionRequest newRequest = new IntersectionRequest(request);
        newRequest.incrementLevel();
        newRequest.setCompassDirectionInputValue(new InputValue(TokenType.COMPASS_DIRECTION, compassDirection));
        return newRequest;
    }
}
