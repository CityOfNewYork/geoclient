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

/**
 * Similar to a {@link Street} except adds additional semantics around code
 * length and the expectation of the first character representing a
 * {@link Borough} code.
 *
 * Internally, this class uses {@link StreetCodeType} to determine potential
 * validity of a given code represented as a string.
 *
 * @see StreetCodeType
 *
 * @author mlipper
 *
 */
public class StreetCode extends CodeNamedValue {

    private final StreetCodeType streetCodeType;

    /**
     * Creates a new instance by calling parent {@link CodeNamedValue} constructor
     * with the given code, a null street name.
     *
     * @param code street code
     *
     * @throws NullPointerException       if the given code is null
     *
     * @see CodeNamedValue
     * @see StreetCodeType
     */
    public StreetCode(String code) {
        super(code, null);
        this.streetCodeType = StreetCodeType.fromCode(code);
    }

    /**
     * Creates a new instance by calling parent {@link CodeNamedValue} constructor.
     *
     * @param code street code
     * @param name street name (may be null)
     *
     * @throws NullPointerException       if the given code is null
     *
     * @see CodeNamedValue
     * @see StreetCodeType
     *
     */
    public StreetCode(String code, String name) {
        super(code, name);
        this.streetCodeType = StreetCodeType.fromCode(code);
    }

    /**
     * Creates a new instance by calling parent {@link CodeNamedValue} constructor.
     *
     * @param code          street code
     * @param name          street name (may be null)
     * @param caseSensitive whether the street name is considered case sensitive
     *
     * @throws NullPointerException       if the given code is null
     *
     * @see CodeNamedValue
     * @see StreetCodeType
     */
    public StreetCode(String code, String name, boolean caseSensitive, StreetCodeType streetCodeType) {
        super(code, name, caseSensitive);
        this.streetCodeType = StreetCodeType.fromCode(code);
    }

    public boolean hasBorough() {
        return this.streetCodeType.hasBorough();
    }

    public String getBoroughCode() {
        if(hasBorough()) {
            return Boroughs.fromCode(getCode().substring(0, 1)).getCode();
        }
        return null;
    }

    public int getBoroughCodeAsInt() {
        return Boroughs.parseInt(getBoroughCode());
    }

    public String getStreetCodeWithoutBorough() {
        if(hasBorough()) {
           return getCode().substring(1);
        }
        return getCode();
    }

    public StreetCodeType getStreetCodeType() {
        return this.streetCodeType;
    }

}
