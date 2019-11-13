/*
 * Copyright 2013-2019 the original author or authors.
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

import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.B10SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.B5SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.B7SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType._10SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType._5SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType._7SC;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class StreetCodeTypeTest {
    
    private StreetCodeFixture fixture = new StreetCodeFixture();

    @Test
    public void testIsValid() {
        String streetCode = fixture.getValid5SC();
        assertTrue(String.format("%s is a valid 5-digit street code", streetCode), _5SC.isValid(streetCode));
        
        streetCode = fixture.getValid7SC();
        assertTrue(String.format("%s is a valid 7-digit street code", streetCode), _7SC.isValid(streetCode));
        
        streetCode = fixture.getValid10SC();
        assertTrue(String.format("%s is a valid 10-digit street code", streetCode), _10SC.isValid(streetCode));
        
        streetCode = fixture.getValidB5SC();
        assertTrue(String.format("%s is a valid B5SC", streetCode), B5SC.isValid(streetCode));
        
        streetCode = fixture.getValidB7SC();
        assertTrue(String.format("%s is a valid B7SC", streetCode), B7SC.isValid(streetCode));
        
        streetCode = fixture.getValidB10SC();
        assertTrue(String.format("%s is a valid B10SC", streetCode), B10SC.isValid(streetCode));
    }

    @Test
    public void testIsValidNoBorough() {
        String streetCode = fixture.getValid5SC();
        assertTrue(String.format("%s is a valid 5-digit street code", streetCode), StreetCodeType.isValidNoBorough(streetCode));
        
        streetCode = fixture.getValid7SC();
        assertTrue(String.format("%s is a valid 7-digit street code", streetCode), StreetCodeType.isValidNoBorough(streetCode));
        
        streetCode = fixture.getValid10SC();
        assertTrue(String.format("%s is a valid 10-digit street code", streetCode), StreetCodeType.isValidNoBorough(streetCode));        
    }

    @Test
    public void testFromCode() {
//        fail("Not yet implemented");
    }

    public static class StreetCodeFixture {
        
        private final IntStream randomBoroughCodes = new Random().ints(1, 5 + 1); // 1..5 inclusive
        private final IntStream randomSingleDigitInteger = new Random().ints(0, 10); // 0..9 inclusive

        public String getValid5SC() {
            return getRandomStreetCode(5);
        }

        public String getValid7SC() {
            return getRandomStreetCode(7);
        }

        public String getValid10SC() {
            return getRandomStreetCode(10);
        }

        public String getValidB5SC() {
            return String.format("%s%s", getRandomBoroughCode(), getValid5SC());
        }

        public String getValidB7SC() {
            return String.format("%s%s", getRandomBoroughCode(), getValid7SC());
        }

        public String getValidB10SC() {
            return String.format("%s%s", getRandomBoroughCode(), getValid10SC());
        }

        public String getRandomBoroughCode() {
            return String.valueOf(this.randomBoroughCodes.findFirst().getAsInt());
        }

        public String getRandomStreetCode(int length) {
            StringBuffer buff = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                buff.append(this.randomSingleDigitInteger.findFirst().getAsInt());
            }
            return buff.toString();
        }
    }

}
