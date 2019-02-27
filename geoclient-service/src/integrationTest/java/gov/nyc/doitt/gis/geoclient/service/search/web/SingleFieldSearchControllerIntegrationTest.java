package gov.nyc.doitt.gis.geoclient.service.search.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gov.nyc.doitt.gis.geoclient.service.search.web.response.MatchStatus;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResponse;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.Status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SingleFieldSearchControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSearch() {
        Map<String, Object> params = new HashMap<>();
        params.put("input", "120 Broadway");
        SearchResponse searchResponse = this.restTemplate.getForObject("/search.json", SearchResponse.class, params);
        Status status = searchResponse.getStatus();
        assertThat(status.equals(Status.OK));
        assertThat(
                searchResponse.getResults().stream().anyMatch(s -> s.getStatus().equals(MatchStatus.POSSIBLE_MATCH)));
    }

}
