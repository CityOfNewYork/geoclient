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
        
        // This method courtesy of:
        //
        // https://www.mkyong.com/java/java-generate-random-integers-in-a-range/ 
        //
        private int getRandomNumberInRange(int min, int max) {

            if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
            }

            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }

        private String lpad(int numberOfLeadingZeros, int value) {
            return String.format(("%0" + numberOfLeadingZeros + "d"), value);
        }

        public String getRandomBoroughCode() {
            return String.valueOf(getRandomNumberInRange(1,5));
        }

        public String getValid5SC() {
            return lpad(5, getRandomNumberInRange(0,99999));
        }

        public String getValid7SC() {
            return lpad(7, getRandomNumberInRange(0,9999999));
        }

        public String getValid10SC() {
            return lpad(10, getRandomNumberInRange(0,99999999));
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
    }

}
