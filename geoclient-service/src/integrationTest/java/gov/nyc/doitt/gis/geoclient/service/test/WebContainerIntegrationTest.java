package gov.nyc.doitt.gis.geoclient.service.test;
import java.net.URI;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class  WebContainerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    protected TestRestTemplate restTemplate() {
        return this.restTemplate;
    }

    protected HttpEntity<?> jsonRequest() {
        return new HttpEntity<>(jsonHeaders());
    }

    protected HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    protected URI jsonResource(String pathWithoutMediaType) {
        return uriComponentsBuilder(pathWithoutMediaType + ".json").build().toUri();
    }
    protected UriComponentsBuilder uriComponentsBuilder(String path) {
        return UriComponentsBuilder.fromPath(path);
    }
}
