/*
 * Copyright 2013-2024 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.docs;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Calls the Geoclient REST service.
 *
 * Provides a simple abstraction over Spring's web and REST client APIs.
 *
 * @author mlipper
 */
public class ServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);
    private final String baseUrl;

    /**
     * Creates a new builder with a common base URI.
     *
     * @param baseUrl the common base URI
     */
    public ServiceClient(String baseUrl) {
        if (baseUrl == null) {
            throw new IllegalArgumentException("Constructor argument baseUrl cannot be null.");
        }
        logger.debug("Using baseUrl: {}", baseUrl);
        this.baseUrl = baseUrl;
    }

    public Response get(Sample sample) {
        URI uri = buildUri(sample);
        logger.debug("Calling uri {}", uri);
        ResponseEntity<String> httpResponse = restTemplate().exchange(uri, HttpMethod.GET, httpRequest(), String.class);
        return createResponse(uri, httpResponse);
    }

    URI buildUri(Sample sample) {
        URI uri = getUri(sample);
        logger.debug("URI w/out query string: {}", uri);
        UriComponents components = UriComponentsBuilder.fromUri(uri).queryParams(queryParams(sample)).build();
        logger.debug("UriComponents.toUriString(): {}", components.toUriString());
        URI builtUri = components.toUri();
        logger.debug("UriComponents.toUri()      : {}", builtUri);
        return builtUri;
    }

    private Response createResponse(URI uri, ResponseEntity<String> httpResponse) {
        return new Response(uri, httpResponse.getStatusCode(), httpResponse.getBody());
    }

    private MultiValueMap<String, String> queryParams(Sample sample) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(sample.getQueryString());
        return params;
    }

    private URI getUri(Sample sample) {
        String uriString = this.baseUrl + "/" + sample.getPathVariable();
        try {
            return new URI(uriString);
        } catch (URISyntaxException e) {
            logger.error("Exception creating URI from String: " + uriString, e);
            throw new RuntimeException(e);
        }
    }

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private HttpEntity<?> httpRequest() {
        return new HttpEntity<>(httpHeaders());
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
