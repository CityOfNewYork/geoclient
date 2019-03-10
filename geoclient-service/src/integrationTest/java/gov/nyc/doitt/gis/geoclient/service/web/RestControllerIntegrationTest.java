package gov.nyc.doitt.gis.geoclient.service.web;

import static org.assertj.core.api.Assertions.assertThat;

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
public class RestControllerIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDoc() {
        String body = this.restTemplate.getForObject("/doc", String.class);
        LOGGER.debug("/doc response body: {}",body);
        assertThat(body.contains("geosupportVersion"));
        assertThat(body.contains("geosupportRelease"));
    }
}
