/*
 * Copyright 2013-2023 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.service.search.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;

public class PlaceRequestTest {
    @Test
    void testSummarize() {
        PlaceRequest request = new PlaceRequest();
        assertThat(request.summarize()).isEqualTo("place [name=null, borough=null, zip=null]");
        request.setStreetInputValue(new InputValue(TokenType.STREET_NAME, "Empire State Building"));
        request.setBoroughInputValue(new InputValue(TokenType.BOROUGH_NAME,"manhattan"));
        request.setZipInputValue(new InputValue(TokenType.ZIP,"10118"));
        assertThat(request.summarize()).isEqualTo("place [name=Empire State Building, borough=manhattan, zip=10118]");
    }
}
