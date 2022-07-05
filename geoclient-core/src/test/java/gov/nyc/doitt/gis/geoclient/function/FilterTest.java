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
package gov.nyc.doitt.gis.geoclient.function;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FilterTest {
    @Test
    public void testNullPatternInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Filter(null);
        });
    }

    @Test
    public void testMatches() {
        Filter filter = new Filter("\\w*[fF]iller\\d*\\w*");
        assertTrue(filter.matches(field("filler")));
        assertTrue(filter.matches(field("Filler")));
        assertTrue(filter.matches(field("filler3")));
        assertTrue(filter.matches(field("filler13")));
        assertTrue(filter.matches(field("filler3In")));
        assertTrue(filter.matches(field("filler3")));
        assertTrue(filter.matches(field("filler5")));
        assertTrue(filter.matches(field("fillerOut")));
        assertTrue(filter.matches(field("myFiller")));
        assertTrue(filter.matches(field("myFiller1")));
        assertTrue(filter.matches(field("myFiller10")));
        assertTrue(filter.matches(field("fillerForTaxLotVersionNumber")));
        assertFalse(filter.matches(field("filer")));
    }

    @Test
    public void testMatchesLowHouseNumber() {
        Filter filter = new Filter("lowHouseNumber(In|SortFormat|SortFormatIn)?");
        assertTrue(filter.matches(field("lowHouseNumber")));
        assertTrue(filter.matches(field("lowHouseNumberIn")));
        assertTrue(filter.matches(field("lowHouseNumberSortFormat")));
        assertTrue(filter.matches(field("lowHouseNumberSortFormatIn")));
        assertTrue(filter.matches(field("lowHouseNumberSortFormat")));
        assertFalse(filter.matches(field("lowHouseNumberOfDefiningAddressRange")));
        assertFalse(filter.matches(field("lowHouseNumberOfBlockfaceSortFormat")));
    }

    @Test
    public void testMatchesHighLowCrossStreetB5SC() {
        Filter filter = new Filter("(low|high)CrossStreetB5SC\\d\\d?");
        assertTrue(filter.matches(field("lowCrossStreetB5SC1")));
        assertTrue(filter.matches(field("lowCrossStreetB5SC10")));
        assertTrue(filter.matches(field("highCrossStreetB5SC1")));
        assertTrue(filter.matches(field("highCrossStreetB5SC10")));
        assertFalse(filter.matches(field("highCrossStreetB5SC")));
    }

    @Test
    public void testMatchesNin() {
        Filter filter = new Filter("nin");
        assertTrue(filter.matches(field("nin")));
        assertFalse(filter.matches(field("lowHouseNumberOfDefiningAddressRange")));
    }

    private Field field(String id) {
        return new Field(id, 0, 1);
    }
}
