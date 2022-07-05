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
package gov.nyc.doitt.gis.geoclient.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;

class BoroughsTest {

    @Test
    void testFromCode() {
        assertSame(Boroughs.MANHATTAN, Boroughs.fromCode("1"));
        assertSame(Boroughs.BRONX, Boroughs.fromCode("2"));
        assertSame(Boroughs.BROOKLYN, Boroughs.fromCode("3"));
        assertSame(Boroughs.QUEENS, Boroughs.fromCode("4"));
        assertSame(Boroughs.STATEN_ISLAND, Boroughs.fromCode("5"));

        assertNull(Boroughs.fromCode(" 1"));
        assertNull(Boroughs.fromCode("8"));
        assertNull(Boroughs.fromCode("woof"));

    }

    @Test
    void testManhattanFromName() {
        List<String> manhattanAKA = Boroughs.MANHATTAN.getAliases();
        for (String name : manhattanAKA) {
            assertSame(Boroughs.MANHATTAN, Boroughs.fromName(name));
            assertSame(Boroughs.MANHATTAN, Boroughs.fromName(name.toLowerCase()));
            assertSame(Boroughs.MANHATTAN, Boroughs.fromName(name.toUpperCase()));
        }
        assertNull(Boroughs.fromName("New Yawk"));
    }

    @Test
    void testBronxFromName() {
        List<String> bronxAKA = Boroughs.BRONX.getAliases();
        for (String name : bronxAKA) {
            assertSame(Boroughs.BRONX, Boroughs.fromName(name));
            assertSame(Boroughs.BRONX, Boroughs.fromName(name.toLowerCase()));
            assertSame(Boroughs.BRONX, Boroughs.fromName(name.toUpperCase()));
        }
        assertNull(Boroughs.fromName("Boogie Down"));
    }

    @Test
    void testBrooklynFromName() {
        List<String> brooklynAKA = Boroughs.BROOKLYN.getAliases();
        for (String name : brooklynAKA) {
            assertSame(Boroughs.BROOKLYN, Boroughs.fromName(name));
            assertSame(Boroughs.BROOKLYN, Boroughs.fromName(name.toLowerCase()));
            assertSame(Boroughs.BROOKLYN, Boroughs.fromName(name.toUpperCase()));
        }
        assertNull(Boroughs.fromName("Brooklyn Zoo"));
    }

    @Test
    void testQueensFromName() {
        List<String> queensAKA = Boroughs.QUEENS.getAliases();
        for (String name : queensAKA) {
            assertSame(Boroughs.QUEENS, Boroughs.fromName(name));
            assertSame(Boroughs.QUEENS, Boroughs.fromName(name.toLowerCase()));
            assertSame(Boroughs.QUEENS, Boroughs.fromName(name.toUpperCase()));
        }
        assertNull(Boroughs.fromName("Queensland"));
    }

    @Test
    void testStatenIslandFromName() {
        List<String> statenIslandAKA = Boroughs.STATEN_ISLAND.getAliases();
        for (String name : statenIslandAKA) {
            assertSame(Boroughs.STATEN_ISLAND, Boroughs.fromName(name));
            assertSame(Boroughs.STATEN_ISLAND, Boroughs.fromName(name.toLowerCase()));
            assertSame(Boroughs.STATEN_ISLAND, Boroughs.fromName(name.toUpperCase()));
        }
        assertNull(Boroughs.fromName("Shaolin"));
    }

    @Test
    public void testParseInt() {
        assertThat(Boroughs.parseInt("1")).isEqualTo(1);
        assertThat(Boroughs.parseInt("2")).isEqualTo(2);
        assertThat(Boroughs.parseInt("3")).isEqualTo(3);
        assertThat(Boroughs.parseInt("4")).isEqualTo(4);
        assertThat(Boroughs.parseInt("5")).isEqualTo(5);
        assertThat(Boroughs.parseInt("12")).isEqualTo(12);
        assertThat(Boroughs.parseInt("-78")).isEqualTo(-78);
        assertThat(Boroughs.parseInt("Manhattan")).isEqualTo(1);
        assertThat(Boroughs.parseInt("MN")).isEqualTo(1);
        assertThat(Boroughs.parseInt("BRONX")).isEqualTo(2);
        assertThat(Boroughs.parseInt("bx")).isEqualTo(2);
        assertThat(Boroughs.parseInt("brooklyn")).isEqualTo(3);
        assertThat(Boroughs.parseInt("bk")).isEqualTo(3);
        assertThat(Boroughs.parseInt("qUeeNs")).isEqualTo(4);
        assertThat(Boroughs.parseInt("qN")).isEqualTo(4);
        assertThat(Boroughs.parseInt("staten island")).isEqualTo(5);
        assertThat(Boroughs.parseInt("STATEN IS")).isEqualTo(5);
        assertThat(Boroughs.parseInt("si")).isEqualTo(5);
        assertThat(Boroughs.parseInt("man")).isEqualTo(Boroughs.UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE);
    }

    @Test
    void testFindFirst() {
        // fail("Not yet implemented");
    }

}
