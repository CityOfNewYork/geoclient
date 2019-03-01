package gov.nyc.doitt.gis.geoclient.service.search.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SingleFieldSearchControllerIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(SingleFieldSearchController.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testSearch() {
        Map<String, Object> params = new HashMap<>();
        params.put("input", "120 Broadway");
        String searchResponse = this.restTemplate.getForObject("/search", String.class, params);
        logger.info("response--->{}", searchResponse);
        // SearchParameters searchParameters = new SearchParameters("120 Broadway");
        // HttpHeaders headers = new HttpHeaders();
        // headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        // HttpEntity<SearchParameters> request = new
        // HttpEntity<SearchParameters>(searchParameters, headers);
        // ResponseEntity<String> httpResponse =
        // this.restTemplate.exchange("/search.json", HttpMethod.GET, request,
        // String.class, params);
        // assertThat(httpResponse.getStatusCodeValue() == 200);
        // String searchResponse = httpResponse.getBody();
        // assertThat(searchResponse.contains("POSSIBLE_MATCH"));
        // Status status = searchResponse.getStatus();
        // assertThat(status.equals(Status.OK));
        // assertThat(
        // searchResponse.getResults().stream().anyMatch(s ->
        // s.getStatus().equals(MatchStatus.POSSIBLE_MATCH)));
    }

}
