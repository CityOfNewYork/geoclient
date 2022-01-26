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
package gov.nyc.doitt.gis.geoclient.parser.regex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PatternUtilsTest {

    @Test
    public void testLiteralMatchGroup_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            PatternUtils.literalMatchGroup(null);
        });
    }

    @Test
    public void testLiteralMatchGroup_empty() {
        assertThrows(IllegalArgumentException.class, () -> {
            PatternUtils.literalMatchGroup(new ArrayList<String>());
        });
    }

    @Test
    public void testLiteralMatchGroup() {
        List<String> list = new ArrayList<String>();
        list.add("cat");
        assertEquals("(cat)", PatternUtils.literalMatchGroup(list));
        list.add("dog");
        assertEquals("(cat|dog)", PatternUtils.literalMatchGroup(list));
        list.add("rat");
        assertEquals("(cat|dog|rat)", PatternUtils.literalMatchGroup(list));
    }

}
