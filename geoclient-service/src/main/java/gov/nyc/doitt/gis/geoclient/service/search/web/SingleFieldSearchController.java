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
package gov.nyc.doitt.gis.geoclient.service.search.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.SingleFieldSearchHandler;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.ParamsAndResult;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchParameters;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResponse;

@CrossOrigin
@RestController
public class SingleFieldSearchController {

    @Autowired
    private SingleFieldSearchHandler singleFieldSearchHandler;

    @Autowired
    private ConversionService conversionService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody SearchResponse search(@Valid SearchParameters params) {
        ParamsAndResult paramsAndResult = new ParamsAndResult(params,
                this.singleFieldSearchHandler.findLocation(params.buildSearchPolicy(), params.getInput()));
        SearchResponse searchResponse = this.conversionService.convert(paramsAndResult, SearchResponse.class);
        return searchResponse;
    }

    @RequestMapping(value = "/search/debug", method = RequestMethod.GET)
    public @ResponseBody SearchResult searchDebug(SearchParameters params) {
        return this.singleFieldSearchHandler.findLocation(params.buildSearchPolicy(), params.getInput());
    }

}
