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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import gov.nyc.doitt.gis.geoclient.service.search.Fixtures;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.SingleFieldSearchHandler;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.MatchStatus;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchParameters;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResponse;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchSummary;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.Status;

@SpringBootTest
@AutoConfigureMockMvc
public class SingleFieldSearchControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SingleFieldSearchController controller;

    @Mock
    private SingleFieldSearchHandler searchHandlerMock;

    private Fixtures fix;

    @BeforeEach
    public void setUp() throws Exception
    {
        MockitoAnnotations.openMocks(this);
        this.fix = new Fixtures();
    }

    @Test
    public void testSearch_acceptsJsonWithValidRequestAndDefaultPolicy()throws Exception
    {
        final String input = "59 Maiden Ln";
        final SearchParameters expectedParams = new SearchParameters(input);
        final SearchPolicy expectedSearchPolicy = expectedParams.buildSearchPolicy();
        final SearchResult expectedSearchResult = new SearchResult(expectedSearchPolicy, fix.locationTokens);
        final SearchResponse expectedJsonResponse = new SearchResponse();
        expectedJsonResponse.setId(expectedSearchResult.getId());
        expectedJsonResponse.setStatus(Status.OK);
        expectedJsonResponse.setInput(input);
        expectedJsonResponse.setResults(new ArrayList<SearchSummary>());
        // This relies on SearchPolicy#equals being implemented so that the
        // search policy built by expectedParams.buildSearchPolicy() above will
        // be qual to the actual instance created at runtime which calls the
        // same method (but returns a different instance of SearchPolicy)
        when(this.searchHandlerMock.findLocation(expectedSearchPolicy, input)).thenReturn(expectedSearchResult);
        this.mockMvc.perform(
                get("/search.json")
                .param("input", input)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.input").value(input))
                .andExpect(jsonPath("$.results").exists())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    public void testSearch_acceptsXmlWithValidRequestAndDefaultPolicy()throws Exception
    {
        final String input = "59 Maiden Ln";
        final SearchParameters expectedParams = new SearchParameters(input);
        final SearchPolicy expectedSearchPolicy = expectedParams.buildSearchPolicy();
        final SearchResult expectedSearchResult = new SearchResult(expectedSearchPolicy, fix.locationTokens);
        final SearchSummary searchSummary = new SearchSummary();
        searchSummary.setLevel("1");
        searchSummary.setStatus(MatchStatus.POSSIBLE_MATCH);
        searchSummary.setRequest("address [houseNumber=59, street=Maiden ln, borough=MANHATTAN, zip=null]");
        searchSummary.setResponse(new HashMap<>());
        final List<SearchSummary> results = new ArrayList<>();
        results.add(searchSummary);
        final SearchResponse expectedXmlResponse = new SearchResponse();
        expectedXmlResponse.setId(expectedSearchResult.getId());
        expectedXmlResponse.setStatus(Status.OK);
        expectedXmlResponse.setInput(input);
        expectedXmlResponse.setResults(results);
        this.mockMvc.perform(
                get("/search.xml")
                .param("input", input)
                .accept(MediaType.APPLICATION_XML))
                .andDo(print())
                .andExpect(xpath("/searchResponse[@id]").exists())
                .andExpect(xpath("/searchResponse/status").string("OK"))
                .andExpect(xpath("/searchResponse/input").string(input))
                .andExpect(xpath("/searchResponse/result").exists())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void testSearch_acceptsJsonWithMissingInputParameter()throws Exception
    {
        this.mockMvc.perform(get("/search").accept(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andDo(print());
    }

}
