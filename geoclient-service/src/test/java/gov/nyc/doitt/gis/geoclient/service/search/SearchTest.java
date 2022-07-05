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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;

public class SearchTest {
    private Fixtures fix;
    private Search search;

    @BeforeEach
    public void setUp() throws Exception {
        fix = new Fixtures();
        search = new Search(fix.requestLevelOne, fix.responseSuccess);
    }

    @Test
    public void testConstructor() {
        assertThat((AddressRequest) search.getRequest()).isSameAs(fix.requestLevelOne);
        assertThat(search.getResponse()).isSameAs(fix.responseSuccess);
    }

    @Test
    public void testIsRejected() {
        assertFalse(search.isRejected());
        assertTrue(new Search(fix.requestLevelFour, fix.responseReject).isRejected());
    }

    @Test
    public void testIsRejected_nullResponse() {
        assertThrows(NullPointerException.class, () -> {
            new Search(fix.requestLevelFour, null).isRejected();
        });
    }

    @Test
    public void testGetSimilarNames() {
        assertThat(search.getSimilarNames()).isSameAs(search.getResponse().getSimilarNames());
    }

    @Test
    public void testgetSimilarNames_nullResponse() {
        assertThrows(NullPointerException.class, () -> {
            new Search(fix.requestLevelFour, null).getSimilarNames();
        });
    }

    @Test
    public void testResponseMessageAppliesTo() {
        String streetName = "FOO";
        assertThat(search.responseMessageAppliesTo(streetName))
                .isEqualTo(search.getResponse().messageAppliesTo(streetName));
        assertFalse(search.responseMessageAppliesTo(streetName));
        search.getResponse().getResponseStatus().getGeosupportReturnCode().setMessage(streetName);
        assertThat(search.responseMessageAppliesTo(streetName))
                .isEqualTo(search.getResponse().messageAppliesTo(streetName));
        assertTrue(search.responseMessageAppliesTo(streetName));
    }

    @Test
    public void testResponseMessageAppliesTo_nullResponse() {
        assertThrows(NullPointerException.class, () -> {
            new Search(fix.requestLevelFour, null).responseMessageAppliesTo("foo");
        });
    }

    @Test
    public void testGetResponseStatus() {
        assertThat(search.getResponseStatus()).isSameAs(search.getResponse().getResponseStatus());
    }

    @Test
    public void testGetLevel() {
        search.getRequest().setLevel(128);
        assertThat(search.getLevel()).isEqualTo(search.getRequest().getLevel());
        assertThat(search.getLevel()).isEqualTo(128);
    }

    @Test
    public void testLessThanOrEqualTo() {
        search.getRequest().setLevel(128);
        assertThat(search.getLevel()).isEqualTo(128);
        assertTrue(search.lessThanOrEqualTo(256));
        assertTrue(search.lessThanOrEqualTo(128));
        assertFalse(search.lessThanOrEqualTo(12));
    }

}
