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

import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.B10SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.B5SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.B7SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType.UNRECOGNIZED;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType._10SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType._5SC;
import static gov.nyc.doitt.gis.geoclient.api.StreetCodeType._7SC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StreetCodeTypeTest {

    private String validB5SC;
    private String validB7SC;
    private String validB10SC;
    private String valid5SC;
    private String valid7SC;
    private String valid10SC;

    @BeforeEach
    public void setUp() {
        StreetCodeFixture fixture = new StreetCodeFixture();
        this.valid5SC = fixture.getValid5SC();
        this.valid7SC = fixture.getValid7SC();
        this.valid10SC = fixture.getValid10SC();
        this.validB5SC = fixture.getValidB5SC();
        this.validB7SC = fixture.getValidB7SC();
        this.validB10SC = fixture.getValidB10SC();
    }

    @AfterEach
    public void tearDown() {
        this.valid5SC = null;
        this.valid7SC = null;
        this.valid10SC = null;
        this.validB5SC = null;
        this.validB7SC = null;
        this.validB10SC = null;
    }

    @Test
    public void testHasBorough() {
        assertTrue(B5SC.hasBorough(), "B5SC has a borough");
        assertTrue(B7SC.hasBorough(), "B7SC has a borough");
        assertTrue(B10SC.hasBorough(), "B10SC has a borough");
        assertFalse(_5SC.hasBorough(), "5-digit street code does have a borough");
        assertFalse(_7SC.hasBorough(), "7-digit street code does have a borough");
        assertFalse(_10SC.hasBorough(), "10-digit street code does have a borough");
        assertFalse(UNRECOGNIZED.hasBorough(), "Unrecognized street code does have a borough");
    }

    @Test
    public void testIsValid() {
        assertTrue(_5SC.isValid(valid5SC), String.format("%s is a valid 5-digit street code", valid5SC));
        assertFalse(_5SC.isValid("1212"), String.format("%s is not a valid 5-digit street code", "1212"));
        assertTrue(_7SC.isValid(valid7SC), String.format("%s is a valid 7-digit street code", valid7SC));
        assertFalse(_7SC.isValid("1212"), String.format("%s is not a valid 7-digit street code", "1212"));
        assertTrue(_10SC.isValid(valid10SC), String.format("%s is a valid 10-digit street code", valid10SC));
        assertFalse(_10SC.isValid("1212"), String.format("%s is not a valid 10-digit street code", "1212"));
        assertFalse(B7SC.isValid("1212"), String.format("%s is not a valid B7SC", "1212"));

        assertTrue(B5SC.isValid(validB5SC), String.format("%s is a valid B5SC", validB5SC));
        assertFalse(B5SC.isValid("1212"), String.format("%s is not a valid B5SC", "1212"));
        assertTrue(B7SC.isValid(validB7SC), String.format("%s is a valid B7SC", validB7SC));
        assertFalse(B7SC.isValid("1212"), String.format("%s is not a valid B7SC", "1212"));
        assertTrue(B10SC.isValid(validB10SC), String.format("%s is a valid B10SC", validB10SC));
        assertFalse(B10SC.isValid("1212"), String.format("%s is not a valid B10SC", "1212"));
    }

    @Test
    public void testIsValidNoBorough() {
        assertTrue(StreetCodeType.isValidNoBorough(valid5SC), String.format("%s is a valid 5-digit street code", valid5SC));
        assertTrue(StreetCodeType.isValidNoBorough(valid7SC), String.format("%s is a valid 7-digit street code", valid7SC));
        assertTrue(StreetCodeType.isValidNoBorough(valid10SC), String.format("%s is a valid 10-digit street code", valid10SC));
    }

    @Test
    public void testIsValidWithBorough() {
        assertTrue(StreetCodeType.isValidWithBorough(validB5SC), String.format("%s is a valid B5SC", validB5SC));
        assertTrue(StreetCodeType.isValidWithBorough(validB7SC), String.format("%s is a valid B7SC", validB7SC));
        assertTrue(StreetCodeType.isValidWithBorough(validB10SC), String.format("%s is a valid B10SC", validB10SC));
    }

    @Test
    public void testFromCode() {
        assertSame(_5SC,StreetCodeType.fromCode(valid5SC), String.format("StreetCodeType %s resolves to the same instance as the type derived from 5-digit street code literal %s",_5SC, valid5SC));
        assertSame(_7SC,StreetCodeType.fromCode(valid7SC), String.format("StreetCodeType %s resolves to the same instance as the type derived from 7-digit street code literal %s",_7SC, valid7SC));
        assertSame(_10SC,StreetCodeType.fromCode(valid10SC), String.format("StreetCodeType %s resolves to the same instance as the type derived from 10-digit street code literal %s",_10SC, valid10SC));

        assertSame(B5SC,StreetCodeType.fromCode(validB5SC),  String.format("StreetCodeType %s resolves to the same instance as the type derived from B5SC literal %s",B5SC, validB5SC));
        assertSame(B7SC,StreetCodeType.fromCode(validB7SC),  String.format("StreetCodeType %s resolves to the same instance as the type derived from B7SC literal %s",B7SC, validB7SC));
        assertSame(B10SC,StreetCodeType.fromCode(validB10SC), String.format("StreetCodeType %s resolves to the same instance as the type derived from B10SC literal %s",B10SC, validB10SC));

        assertSame(UNRECOGNIZED,StreetCodeType.fromCode("1212"), String.format("StreetCodeType %s resolves to the same instance as the type derived from invalid literal %s",UNRECOGNIZED, "1212"));
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
