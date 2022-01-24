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
package gov.nyc.doitt.gis.geoclient.service.web;


import static gov.nyc.doitt.gis.geoclient.service.web.RestController.VERSION_URI;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.test.WebContainerIntegrationTest;

public class VersionIntegrationTest extends WebContainerIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionIntegrationTest.class);

    private URI uri;

    @BeforeEach
    public void setUp() {
        uri = jsonResource(VERSION_URI);
    }

  @Test
  public void testVersion() throws IOException {
      ResponseEntity<Version> httpResponse = restTemplate().exchange(uri, HttpMethod.GET,
              jsonRequest(), Version.class);
      assertThat(httpResponse.getStatusCodeValue() == 200);
      Version actual = httpResponse.getBody();
      LOGGER.info("{} -> {}", uri.toASCIIString(), actual);
      //versionContent.assertThat().
  }

}
