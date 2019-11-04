package gov.nyc.doitt.gis.geoclient.service.search.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SingleFieldSearchControllerIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(SingleFieldSearchController.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // restTemplate
    }

    @Test
    public void testSearch() {
        UriComponents uriComponents = UriComponentsBuilder.fromPath("/search.json").queryParam("input", "120 broadway")
                .build();
        ResponseEntity<SearchResponse> httpResponse = this.restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET,
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
