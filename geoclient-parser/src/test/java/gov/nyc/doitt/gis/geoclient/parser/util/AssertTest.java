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
package gov.nyc.doitt.gis.geoclient.parser.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

class AssertTest {

    @Test
    void testHasText() {
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText(" o ", "bar");
    }

    @Test
    void testHasText_null() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText((String)null, "bar"), "bar");
    }

    @Test
    void testHasText_empty() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText("", "bar"), "bar");
    }

    @Test
    void testHasText_whitespaceOnly() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.hasText("  ", "bar"), "bar");
    }

    @Test
    void testIsTrue() {
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.isTrue(2 > 1, "bar");
    }

    @Test
    void testIsTrue_false() {
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.isTrue(1 > 1, "bar"), "bar");
    }

    @Test
    void testNotEmpty() {
        Collection<String> collection = new ArrayList<>();
        collection.add("foo");
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.notEmpty(collection, "bar");
    }

    @Test
    void testNotEmpty_null() {
        Collection<String> collection = null;
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.notEmpty(collection, "bar"), "bar");

    }

    @Test
    void testNotEmpty_empty() {
        Collection<String> collection = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,
                () -> gov.nyc.doitt.gis.geoclient.parser.util.Assert.notEmpty(collection, "bar"), "bar");
    }

    @Test
    void testNotNull() {
        gov.nyc.doitt.gis.geoclient.parser.util.Assert.notNull(new Object(), "bar");
    }

    @Test
    void testNotNull_null() {
        assertThrows(IllegalArgumentException.class,
                () ->  gov.nyc.doitt.gis.geoclient.parser.util.Assert.notNull((Object)null, "bar"), "bar");
    }

}
