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
package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FieldSet} class.
 */
public class FieldSetTest {

    private String functionId = "fubar";
    private String[] fieldNames = new String[] { "apple", "banana", "cantaloupe" };

    @Test
    void testFieldSet() {
        FieldSet fs = new FieldSet(functionId, fieldNames);
        assertEquals(functionId, fs.getFunctionId());
        assertEquals(fieldNames.length, fs.getFieldNames().size());
        for (int i = 0; i < fieldNames.length; i++) {
            assertTrue(fs.getFieldNames().contains(fieldNames[i]));
        }
        FieldSet equalFs = new FieldSet(functionId, fieldNames);
        FieldSet anotherEqualFs = new FieldSet(functionId, fieldNames);
        assertAll("equals and hashCode",
                () -> {
                    // equals is reflexive
                    assertEquals(fs, fs);
                    // hashCode is consistent
                    assertEquals(fs.hashCode(), fs.hashCode());
                    // equals is symetric
                    assertEquals(fs, equalFs);
                    assertEquals(equalFs, fs);
                    // hashCode is consistent
                    assertEquals(fs.hashCode(), equalFs.hashCode());
                    // equals is transitive
                    assertEquals(fs, anotherEqualFs);
                    assertEquals(anotherEqualFs, equalFs);
                    // hashCode is consistent
                    assertEquals(fs.hashCode(), anotherEqualFs.hashCode());
                    assertEquals(anotherEqualFs.hashCode(), equalFs.hashCode());
                    // Null is not equal
                    assertFalse(fs.equals(null));
                    // Unequal
                    assertFalse(fs.equals(new FieldSet("blah", fieldNames)));
                    assertFalse(fs.equals(new FieldSet(functionId, new String[] {})));
                });
                // Null fieldNames causes NullPointerException
                String[] nullArray = null;
                assertThrows(IllegalArgumentException.class, () -> fs.equals(new FieldSet(functionId, nullArray)));
                // Null functionId causes NullPointerException
                assertThrows(IllegalArgumentException.class, () -> fs.equals(new FieldSet(null, fieldNames)));
    }
}
