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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SingleFieldSearchHandlerTest {

    @Autowired
    private SingleFieldSearchHandler singleFieldSearchHandler;

    @Test
    public void testFindLocationDefaultPolicy_exactAddressMatch() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("59 Maiden Ln Manhattan");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        Search search = searchResult.getSearches().get(0);
        assertThat(search.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_addressWithoutBorough() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("948 Jamaica Ave");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertThat(searchResult.getExactMatch()).isNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(5);
        for (Search search : searchResult.getSearches()) {
            assertThat(search.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL + 1);
        }
    }

    @Test
    public void testFindLocationDefaultPolicy_addressWithZipAndNoBorough() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("280 RSD 10025");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        Search search = searchResult.getSearches().get(0);
        assertThat(search.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_addressWithTwoValidSimilarNames() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("314 100 St Manhattan");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertThat(searchResult.getExactMatch()).isNull();
        assertThat(searchResult.getSearches().size() >= 3).isTrue();
        // First result
        Search initialSearch = searchResult.getSearches().get(0);
        assertThat(initialSearch.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
        assertThat(initialSearch.getResponse().isRejected()).isTrue();
        assertThat(initialSearch.getResponse().getResponseStatus().similarNamesCount() >= 2).isTrue();
        List<String> similarNames = initialSearch.getSimilarNames();
        String east100Street = "EAST  100 STREET";
        assertThat(similarNames.contains(east100Street)).isTrue();
        String west100Street = "WEST  100 STREET";
        assertThat(similarNames.contains(west100Street)).isTrue();
        boolean east100IsFirst = similarNames.indexOf(east100Street) < similarNames.indexOf(west100Street);
        String firstSimilarName = east100IsFirst ? east100Street : west100Street;
        String secondSimilarName = east100IsFirst ? west100Street : east100Street;
        // Second result
        Search secondSearch = searchResult.getSearches().get(1);
        assertThat(secondSearch.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL + 1);
        AddressRequest secondRequest = (AddressRequest) secondSearch.getRequest();
        assertThat(secondRequest.getHouseNumber()).isEqualTo("314");
        assertThat(secondRequest.getStreet()).isEqualTo(firstSimilarName);
        assertThat(secondRequest.getBorough()).isEqualTo("MANHATTAN");
        assertThat(secondSearch.getResponseStatus().isRejected()).isFalse();
        // Third result
        Search thirdSearch = searchResult.getSearches().get(2);
        assertThat(thirdSearch.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL + 1);
        AddressRequest thirdRequest = (AddressRequest) thirdSearch.getRequest();
        assertThat(thirdRequest.getHouseNumber()).isEqualTo("314");
        assertThat(thirdRequest.getStreet()).isEqualTo(secondSimilarName);
        assertThat(thirdRequest.getBorough()).isEqualTo("MANHATTAN");
        assertThat(thirdSearch.getResponseStatus().isRejected()).isFalse();
    }

    @Test
    public void testFindLocationDefaultPolicy_exactPlaceMatch() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("Empire State Building, Manhattan");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        Search search = searchResult.getSearches().get(0);
        assertThat(search.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_exactBblMatch() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("1018890001");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        Search search = searchResult.getSearches().get(0);
        assertThat(search.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_exactBinMatch() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("1057127");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        Search search = searchResult.getSearches().get(0);
        assertThat(search.getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_blockfaceExactMatch() {
        SearchResult searchResult = this.singleFieldSearchHandler
                .findLocation("broadway between w 100 st and w 101 st, manhattan");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        assertThat(searchResult.getExactMatch().getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_blockfaceOnStreetValidSimilarName() {
        SearchResult searchResult = this.singleFieldSearchHandler
                .findLocation("bro between w 100 st and w 101 st, manhattan");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertTrue(searchResult.getSearches().size() > 2);
        assertThat(searchResult.successCount()).isEqualTo(1);
    }

    @Test
    public void testFindLocationDefaultPolicy_blockfaceCrossStreetOneValidSimilarName() {
        SearchResult searchResult = this.singleFieldSearchHandler
                .findLocation("maiden ln between nassau and broadway, manhattan");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertTrue(searchResult.getSearches().size() > 1);
        assertThat(searchResult.successCount()).isEqualTo(1);
    }

    @Test
    public void testFindLocationDefaultPolicy_blockfaceCrossStreetTwoValidSimilarName() {
        SearchResult searchResult = this.singleFieldSearchHandler
                .findLocation("maiden ln between broadway & nassau, manhattan");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertTrue(searchResult.getSearches().size() > 1);
        assertThat(searchResult.successCount()).isEqualTo(1);
    }

    @Test
    public void testFindLocationDefaultPolicy_intersectionExactMatch() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("broadway & w 100 st nyc");
        assertThat(searchResult.isExactMatch()).isTrue();
        assertThat(searchResult.getExactMatch()).isNotNull();
        assertThat(searchResult.getSearches().size()).isEqualTo(1);
        assertThat(searchResult.getExactMatch().getLevel()).isEqualTo(SearchPolicy.INITIAL_SEARCH_LEVEL);
    }

    @Test
    public void testFindLocationDefaultPolicy_intersectionWithOneValidSimilarName() {
        SearchResult searchResult = this.singleFieldSearchHandler.findLocation("bro & w 100 st nyc");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertTrue(searchResult.getSearches().size() > 2);
        assertThat(searchResult.successCount()).isEqualTo(1);
    }

    @Test
    public void testFindLocationDefaultPolicy_intersectionWithCompassDirectionRequired() {
        // 2015-04-22: "w 97 st & rsd manhattan" no longer requires a compass direction!
        SearchResult searchResult = this.singleFieldSearchHandler
                .findLocation("Cromwell Crescent and Alderton Street queens");
        assertThat(searchResult.isExactMatch()).isFalse();
        assertThat(searchResult.getSearches().size()).isEqualTo(5);
        // Yup! All four compassDirections can be geocoded.
        assertThat(searchResult.successCount()).isEqualTo(4);
    }
}
