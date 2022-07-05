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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.service.search.Fixtures;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;

public class SearchTaskFactoryTest {
    @InjectMocks
    private SearchTaskFactory searchTaskFactory;

    @Mock
    private InitialSearchTaskBuilder initialSearchTaskBuilder;

    @Mock
    private SpawnedSearchTaskBuilder spawnedSearchTaskBuilder;

    private SearchPolicy searchPolicy;

    private LocationTokens locationTokens;

    private List<SearchTask> expectedTasks;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        locationTokens = new Fixtures().locationTokens;
        searchPolicy = new SearchPolicy();
        expectedTasks = new ArrayList<>();
    }

    @Test
    public void testBuildInitialSearchTasks() {
        Mockito.when(initialSearchTaskBuilder.getSearchTasks(searchPolicy, locationTokens)).thenReturn(expectedTasks);
        List<SearchTask> result = searchTaskFactory.buildInitialSearchTasks(searchPolicy, locationTokens);
        assertThat(result).isSameAs(expectedTasks);
    }

    @Test
    public void testBuildSubsearchTasks() {
        SearchResult searchResult = new SearchResult(searchPolicy, locationTokens);
        Mockito.when(spawnedSearchTaskBuilder.getSearchTasks(searchResult)).thenReturn(expectedTasks);
        List<SearchTask> result = searchTaskFactory.buildSubsearchTasks(searchResult);
        assertThat(result).isSameAs(expectedTasks);
    }

}
