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
package gov.nyc.doitt.gis.geoclient.service.street.web;

import gov.nyc.doitt.gis.geoclient.api.StreetCode;

/**
 * POJO for encapsulating street code requests
 *
 * @author mlipper
 * @since 2.0
 */
public class StreetCodeRequest {
    private String format;
    private Integer length;
    private StreetCode streetCode;
    private StreetCode streetCodeTwo;
    private StreetCode streetCodeThree;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public StreetCode getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(StreetCode streetCode) {
        this.streetCode = streetCode;
    }

    public StreetCode getStreetCodeThree() {
        return streetCodeThree;
    }

    public void setStreetCodeThree(StreetCode streetCodeThree) {
        this.streetCodeThree = streetCodeThree;
    }

    public StreetCode getStreetCodeTwo() {
        return streetCodeTwo;
    }

    public void setStreetCodeTwo(StreetCode streetCodeTwo) {
        this.streetCodeTwo = streetCodeTwo;
    }

}
