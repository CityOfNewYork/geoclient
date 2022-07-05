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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import gov.nyc.doitt.gis.geoclient.service.search.web.response.MatchStatus;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResponse;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.Status;
import gov.nyc.doitt.gis.geoclient.service.test.WebContainerIntegrationTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SingleFieldSearchControllerIntegrationTest extends WebContainerIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(SingleFieldSearchController.class);

    @Test
    public void testSearch() {
        UriComponents uriComponents = UriComponentsBuilder.fromPath("/search.json").queryParam("input", "120 broadway")
                .build();
        ResponseEntity<SearchResponse> httpResponse = restTemplate().exchange(uriComponents.toUri(), HttpMethod.GET,
                getRequest(), SearchResponse.class);
        SearchResponse searchResponse = httpResponse.getBody();
        logger.debug("Response: {}", searchResponse);
        assertThat(httpResponse.getStatusCodeValue() == 200);
        Status status = searchResponse.getStatus();
        assertThat(status.equals(Status.OK));
        assertThat(
                searchResponse.getResults().stream().anyMatch(s -> s.getStatus().equals(MatchStatus.POSSIBLE_MATCH)));
    }

    private HttpEntity<?> getRequest() {
        return new HttpEntity<>(getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
