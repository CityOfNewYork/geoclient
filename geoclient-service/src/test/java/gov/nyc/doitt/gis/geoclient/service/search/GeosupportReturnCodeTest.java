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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue;

public class GeosupportReturnCodeTest
{
    private GeosupportReturnCode grc;

    @BeforeEach
    public void setUp()
    {
        this.grc = new GeosupportReturnCode();
    }
    @Test
    public void testIsCompassDirectionRequired()
    {
        assertFalse(this.grc.isCompassDirectionRequired());
        this.grc.setReturnCode(ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.value());
        assertTrue(this.grc.isCompassDirectionRequired());
    }

    @Test
    public void testHasSimilarNames()
    {
        assertFalse(this.grc.hasSimilarNames());
        this.grc.setReturnCode(ReturnCodeValue.NOT_RECOGNIZED_WITH_SIMILAR_NAMES.value());
        assertTrue(this.grc.hasSimilarNames());
        this.grc.setReturnCode(ReturnCodeValue.NOT_RECOGNIZED_NO_SIMILAR_NAMES.value());
        assertFalse(this.grc.hasSimilarNames());
    }

    @Test
    public void testIsRejected()
    {
        assertTrue(this.grc.isRejected());
        this.grc.setReturnCode("00");
        assertFalse(this.grc.isRejected());
        this.grc.setReturnCode("EE");
        assertTrue(this.grc.isRejected());
        this.grc.setReturnCode("01");
        assertFalse(this.grc.isRejected());
    }

    @Test
    public void testHasReasonCode()
    {
        assertFalse(this.grc.hasReasonCode());
        this.grc.setReasonCode("V");
        assertTrue(this.grc.hasReasonCode());
    }

    @Test
    public void testHasMessage()
    {
        assertFalse(this.grc.hasMessage());
        this.grc.setMessage("Hello");
        assertTrue(this.grc.hasMessage());
    }

}
