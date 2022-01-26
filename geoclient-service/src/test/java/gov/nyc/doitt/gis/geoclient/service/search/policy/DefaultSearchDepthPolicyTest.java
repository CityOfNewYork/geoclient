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
package gov.nyc.doitt.gis.geoclient.service.search.policy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.service.search.CountyResolver;
import gov.nyc.doitt.gis.geoclient.service.search.Fixtures;
import gov.nyc.doitt.gis.geoclient.service.search.Search;

public class DefaultSearchDepthPolicyTest {
    private Fixtures fix;
    private DefaultSearchDepthPolicy policy;

    @BeforeEach
    public void setUp() throws Exception {
        this.fix = new Fixtures();
        this.policy = new DefaultSearchDepthPolicy();
    }

    @Test
    public void testLevelEnabled() {
        int maxLevel = this.policy.getMaximumDepth();
        assertTrue(this.policy.levelEnabled(maxLevel - 1));
        assertTrue(this.policy.levelEnabled(maxLevel));
        assertFalse(this.policy.levelEnabled(maxLevel + 1));
    }

    @Test
    public void testNextLevelEnabled() {
        assertTrue(this.policy.nextLevelEnabled(fix.requestLevelZero));
        assertTrue(this.policy.nextLevelEnabled(fix.requestLevelOne));
        assertFalse(this.policy.nextLevelEnabled(fix.requestLevelFour));
    }

    @Test
    public void testNextLevelEnabled_falseForAssignedValue() {
        assertFalse(fix.requestLevelOne.containsAssignedValue());
        assertTrue(this.policy.nextLevelEnabled(fix.requestLevelOne));
        fix.requestLevelOne.setBoroughInputValue(CountyResolver.BRONX);
        assertTrue(fix.requestLevelOne.containsAssignedValue());
        assertFalse(this.policy.nextLevelEnabled(fix.requestLevelOne));
    }

    @Test
    public void testContinueSearch_emptySearchResult() {
        assertFalse(this.policy.continueSearch(fix.searchResult));
    }

    @Test
    public void testContinueSearch_levelZeroRejected() {
        fix.searchResult.add(new Search(fix.requestLevelZero, fix.responseReject));
        assertTrue(this.policy.continueSearch(fix.searchResult));
    }

    @Test
    public void testContinueSearch_levelFourRejected() {
        fix.searchResult.add(new Search(fix.requestLevelFour, fix.responseReject));
        assertFalse(this.policy.continueSearch(fix.searchResult));
    }

    @Test
    public void testInputForSubSearches_emptySearchResultProducesNoSubSearches() {
        assertTrue(this.policy.inputForSubSearches(fix.searchResult).isEmpty());
    }

    @Test
    public void testInputForSubSearches_currentLevelContainsOnlySuccess() {
        fix.searchResult.add(new Search(fix.requestLevelOne, fix.responseSuccess));
        assertTrue(this.policy.inputForSubSearches(fix.searchResult).isEmpty());
    }

    @Test
    public void testInputForSubSearches_currentLevelFailureButNextLevelDisabled() {
        fix.searchResult.add(new Search(fix.requestLevelFour, fix.responseSuccess));
        fix.searchResult.add(new Search(fix.requestLevelFour, fix.responseReject));
        assertTrue(this.policy.inputForSubSearches(fix.searchResult).isEmpty());
    }

    @Test
    public void testInputForSubSearches_currentLevelContainsSuccessAndFailure() {
        fix.searchResult.add(new Search(fix.requestLevelOne, fix.responseSuccess));
        Search rejectedLevelOneSearch = new Search(fix.requestLevelOne, fix.responseReject);
        fix.searchResult.add(rejectedLevelOneSearch);
        assertThat(this.policy.inputForSubSearches(fix.searchResult).size()).isEqualTo(1);
        assertTrue(this.policy.inputForSubSearches(fix.searchResult).contains(rejectedLevelOneSearch));
    }

}
