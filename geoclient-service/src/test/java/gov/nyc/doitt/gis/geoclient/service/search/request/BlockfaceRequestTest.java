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
package gov.nyc.doitt.gis.geoclient.service.search.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;

public class BlockfaceRequestTest
{

    @Test
    public void testSummarize()
    {
        BlockfaceRequest request = new BlockfaceRequest();
        assertThat(request.summarize()).isEqualTo("blockface [onStreet=null, crossStreetOne=null, crossStreetTwo=null, borough=null]");
        request.setOnStreetInputValue(new InputValue(TokenType.ON_STREET, "bway"));
        request.setCrossStreetOneInputValue(new InputValue(TokenType.ON_STREET, "w 100 st"));
        request.setCrossStreetTwoInputValue(new InputValue(TokenType.ON_STREET, "w 101 st"));
        request.setBoroughInputValue(new InputValue(TokenType.BOROUGH_NAME,"manhattan"));
        assertThat(request.summarize()).isEqualTo("blockface [onStreet=bway, crossStreetOne=w 100 st, crossStreetTwo=w 101 st, borough=manhattan]");
    }

}
