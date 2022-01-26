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
package gov.nyc.doitt.gis.geoclient.service.search.web.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultExactMatchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultSearchDepthPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultSimilarNamesPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;

public class SearchParametersTest {
    private SearchParameters params;

    @BeforeEach
    public void setUp() throws Exception {
        this.params = new SearchParameters();
    }

    @Test
    public void testInputConstructor() {
        assertDefaultSettings("foo", new SearchParameters("foo"));
    }

    @Test
    public void testDefaultConstructor() {
        assertDefaultSettings(null, params);
    }

    @Test
    public void testBuildSearchPolicy() {
        params.setMaxDepth(5);
        params.setSimilarNamesDistance(2);
        params.setExactMatchMaxLevel(3);
        params.setExactMatchForSingleSuccess(true);
        SearchPolicy searchPolicy = params.buildSearchPolicy();
        assertThat(searchPolicy).isNotNull();
        assertThat(((DefaultSearchDepthPolicy) searchPolicy.getSearchDepthPolicy()).getMaximumDepth()).isEqualTo(5);
        assertThat(((DefaultSimilarNamesPolicy) searchPolicy.getSimilarNamesPolicy()).getSimilarNamesDistance())
                .isEqualTo(2);
        assertThat(((DefaultExactMatchPolicy) searchPolicy.getExactMatchPolicy()).getExactMatchMaxLevel()).isEqualTo(3);
        assertThat(((DefaultExactMatchPolicy) searchPolicy.getExactMatchPolicy()).isExactMatchForSingleSuccess())
                .isEqualTo(true);
    }

    private void assertDefaultSettings(String input, SearchParameters searchParams) {
        if (input != null) {
            assertThat(searchParams.getInput()).isEqualTo(input);
        } else {
            assertThat(searchParams.getInput()).isNull();
        }

        assertFalse(searchParams.isReturnPolicy());
        assertFalse(searchParams.isReturnTokens());
        assertFalse(searchParams.isReturnPossiblesWithExactMatch());
        assertFalse(searchParams.isReturnRejections());
        assertThat(searchParams.getMaxDepth()).isEqualTo(DefaultSearchDepthPolicy.DEFAULT_MAX_DEPTH);
        assertThat(searchParams.getSimilarNamesDistance())
                .isEqualTo(DefaultSimilarNamesPolicy.DEFAULT_SIMILAR_NAMES_DISTANCE);
        assertThat(searchParams.isExactMatchForSingleSuccess())
                .isEqualTo(DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_FOR_SINGLE_SUCCESS);
        assertThat(searchParams.getExactMatchMaxLevel())
                .isEqualTo(DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_MAX_LEVEL);
    }
}
