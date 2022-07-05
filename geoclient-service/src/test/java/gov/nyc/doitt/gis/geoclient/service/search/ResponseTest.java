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
package gov.nyc.doitt.gis.geoclient.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue;

public class ResponseTest {
    private Fixtures fix;
    private Response response;

    @BeforeEach
    public void setUp() throws Exception {
        fix = new Fixtures();
        response = new Response(fix.successStatus, fix.geocodes);
    }

    @Test
    public void testConstructor() {
        assertThat(response.getGeocodes()).isSameAs(fix.geocodes);
        assertThat(response.getResponseStatus()).isSameAs(fix.successStatus);
        assertThat(response.getTimestamp()).isNotNull();
    }

    @Test
    public void testMessageAppliesTo() {
        assertFalse(response.messageAppliesTo(null));
        assertFalse(response.messageAppliesTo("truck"));
        response.getResponseStatus().getGeosupportReturnCode().setMessage("Street 'TRUCK STREET' not recognized");
        assertTrue(response.messageAppliesTo("truck"));
        assertFalse(response.messageAppliesTo("duck"));
        assertFalse(response.messageAppliesTo(null));
    }

    @Test
    public void testIsCompassDirectionRequired() {
        assertFalse(response.isCompassDirectionRequired());
        response.getResponseStatus().getGeosupportReturnCode()
                .setReturnCode(ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.value());
        assertTrue(response.isCompassDirectionRequired());
        response.getResponseStatus().getGeosupportReturnCode().setReturnCode(null);
        assertFalse(response.isCompassDirectionRequired());
    }

    @Test
    public void testIsRejected() {
        assertFalse(response.isCompassDirectionRequired());
        assertTrue(new Response(fix.rejectStatus, fix.geocodes).isRejected());
    }

    @Test
    public void testSimilarNamesCount() {
        assertThat(response.similarNamesCount()).isEqualTo(0);
        response.getResponseStatus().getSimilarNames().add("abc");
        assertThat(response.similarNamesCount()).isEqualTo(1);
    }

    @Test
    public void testGetSimilarNames() {
        assertFalse(response.getSimilarNames().contains("abc"));
        assertThat(response.getSimilarNames()).isSameAs(response.getResponseStatus().getSimilarNames());
        response.getResponseStatus().getSimilarNames().add("abc");
        assertTrue(response.getSimilarNames().contains("abc"));
    }

}
