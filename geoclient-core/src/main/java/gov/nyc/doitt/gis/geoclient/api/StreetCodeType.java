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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Constants representing Geosupport street codes.
 *
 * @author mlipper
 *
 */
public enum StreetCodeType {

    B5SC(1 + 5), B7SC(1 + 7), B10SC(1 + 10), _5SC(5), _7SC(7), _10SC(10), UNRECOGNIZED(-1);

    private final int length;
    private final Pattern pattern;

    private StreetCodeType(int length) {
        this.length = length;
        switch (this.length) {
        case -1:
            this.pattern = null;
            break;
        case 5:
        case 7:
        case 10:
            this.pattern = Pattern.compile("[0-9]{" + this.length + "}");
            break;
        case 6:
        case 8:
        case 11:
            this.pattern = Pattern.compile("[1-5][0-9]{" + (this.length - 1) + "}");
            break;
        default:
            throw new IllegalArgumentException("Invalid street code length: " + this.length);
        }
    }

    public int length() {
        return this.length;
    }

    public boolean hasBorough() {
        return this.equals(B5SC) || this.equals(B7SC) || this.equals(B10SC);
    }

    public boolean isValid(String streetCode) {
        return this.pattern != null && this.pattern.matcher(streetCode).matches();
    }

    public static boolean isValidNoBorough(String streetCode) {
        if (streetCode == null) {
            return false;
        }
        return _5SC.isValid(streetCode) || _7SC.isValid(streetCode) || _10SC.isValid(streetCode);
    }

    public static boolean isValidWithBorough(String streetCode) {
        if (streetCode == null) {
            return false;
        }

        return B5SC.isValid(streetCode) || B7SC.isValid(streetCode)
                || B10SC.isValid(streetCode);
    }

    public static StreetCodeType fromCode(String streetCode)
            throws NullPointerException {
        if (streetCode == null) {
            throw new NullPointerException("street code argument cannot be null");
        }

        for (StreetCodeType type : StreetCodeType.types()) {
            if (type.isValid(streetCode)) {
                return type;
            }
        }
        return UNRECOGNIZED;
    }

    private static List<StreetCodeType> types() {
        List<StreetCodeType> allTypes = new ArrayList<StreetCodeType>();
        allTypes.add(B5SC);
        allTypes.add(B7SC);
        allTypes.add(B10SC);
        allTypes.add(_5SC);
        allTypes.add(_7SC);
        allTypes.add(_10SC);
        return allTypes;
    }

}
