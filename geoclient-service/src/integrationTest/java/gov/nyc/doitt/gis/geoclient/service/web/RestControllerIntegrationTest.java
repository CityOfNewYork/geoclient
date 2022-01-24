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

import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE;
import static gov.nyc.doitt.gis.geoclient.api.OutputParam.GEOSUPPORT_RETURN_CODE;
import static gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue.SUCCESS;
import static gov.nyc.doitt.gis.geoclient.function.Function.FDG;
import static gov.nyc.doitt.gis.geoclient.service.web.RestController.STREETCODE_URI;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

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

    @SuppressWarnings("unchecked")
    @Test
    public void testStreetcode_FDG() {
        String streetCodeUriTemplate = String.format("%s.%s?%s={%s}", STREETCODE_URI, "json", STREET_CODE, STREET_CODE);
        LOGGER.info("URI=" + streetCodeUriTemplate);
        Map<String, Object> body = (Map<String, Object>) this.restTemplate.getForObject(streetCodeUriTemplate, Map.class, "110610");
        LOGGER.info("/streetcode response body: {}",body);
        Map<String, Object> result = (Map<String, Object>) body.get(STREET_CODE.toLowerCase());
        assertThat(result.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(result.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(result.containsKey("geosupportFunctionCode"));
        assertThat(result.get("geosupportFunctionCode").equals(FDG));
    }
}
