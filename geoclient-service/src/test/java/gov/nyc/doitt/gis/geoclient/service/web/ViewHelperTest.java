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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewHelperTest {
    private ViewHelper vh;

    @BeforeEach
    public void setUp() {
        this.vh = new ViewHelper();
    }

    @Test
    public void testSectionAnchor() {
        assertEquals("section-10.2", vh.sectionAnchor(10, 2));
    }

    @Test
    public void testSectionNumber() {
        assertEquals("10.2", vh.sectionNumber(10, 2));
    }

    @Test
    public void testHrefStringArgument() {
        assertEquals("#null", vh.href((String) null));
        assertEquals("#xyz", vh.href("xyz"));
        assertEquals("http://xyz", vh.href("http://xyz"));
        assertEquals("hTtp://Xyz.com", vh.href("hTtp://Xyz.com"));
        assertEquals("https://xyz", vh.href("https://xyz"));
        assertEquals("HTTPS://xyz", vh.href("HTTPS://xyz"));
    }
}
