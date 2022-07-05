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
package gov.nyc.doitt.gis.geoclient.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class InputTest {

    @Test
    public void testConstructor_idNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Input(null, "value");
        });
    }

    @Test
    public void testConstructor_idEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Input("", "value");
        });
    }

    @Test
    public void testConstructor_valueNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Input("id", null);
        });
    }

    @Test
    public void testConstructor_valueEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Input("id", "");
        });
    }

    @Test
    public void testConstructor() {
        String id = "123";
        String value = " $abc";
        Input input = new Input(id, value);
        assertThat(input.getId()).isSameAs(id);
        assertThat(input.getValue()).isEqualTo("abc");
        assertThat(input.getUnsanitizedValue()).isSameAs(value);
    }

}
