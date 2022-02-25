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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.service.domain.FieldSet;

/**
 * Tests the {@link DoubleFieldSetConverterTest} class.
 */
public class DoubleFieldSetConverterTest {

    @Test
    void testConvert() {
        String latString = "40.798502";
        String lonString = "-73.972709";
        FieldSet fooFieldSet = new FieldSet("foo", "lat", "lon");
        FieldSet barFieldSet = new FieldSet("bar", "amount", "lon");
        Double latDouble = Double.valueOf(latString);
        Double lonDouble = Double.valueOf(lonString);
        FieldSetConverter converter = new DoubleFieldSetConverter(Arrays.asList(fooFieldSet, barFieldSet));
        Map<String, Object> args = new HashMap<>();
        // Test foo FieldSet
        args.put("lat", latString);
        args.put("lon", lonString);
        args.put("amount", "12.24");
        converter.convert("foo", args);
        assertEquals(3, args.size());
        assertEquals(latDouble, args.get("lat"));
        assertEquals(lonDouble, args.get("lon"));
        assertEquals("12.24", args.get("amount"));
        // Test bar FieldSet
        args.clear();
        args.put("lat", latString);
        args.put("lon", lonString);
        converter.convert("bar", args);
        assertEquals(2, args.size());
        assertEquals(latString, args.get("lat"));
        assertEquals(lonDouble, args.get("lon"));
    }
}
