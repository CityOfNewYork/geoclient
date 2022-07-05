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
package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.service.search.GeosupportReturnCode;

public class GeosupportReturnCodeFixture {
    private final GeosupportReturnCode grc;
    private final boolean one;

    public GeosupportReturnCodeFixture(GeosupportReturnCode grc, boolean one) {
        super();
        this.grc = grc;
        this.one = one;
    }

    public GeosupportReturnCodeFixture(GeosupportReturnCode grc) {
        this(grc, true);
    }

    public GeosupportReturnCodeFixture(boolean isOne) {
        this(new GeosupportReturnCode(), isOne);
    }

    public GeosupportReturnCodeFixture() {
        this(new GeosupportReturnCode(), true);
    }

    public GeosupportReturnCode getGeosupportReturnCode() {
        return this.grc;
    }

    public synchronized void reset() {
        // Does NOT alter this.isOne
        this.grc.setReturnCode(null);
        this.grc.setReasonCode(null);
        this.grc.setMessage(null);
    }

    public GeosupportReturnCodeFixture returnCode(String returnCode) {
        this.grc.setReturnCode(returnCode);
        return this;
    }

    public GeosupportReturnCodeFixture reasonCode(String reasonCode) {
        this.grc.setReasonCode(reasonCode);
        return this;
    }

    public GeosupportReturnCodeFixture message(String message) {
        this.grc.setMessage(message);
        return this;
    }

    public boolean isOne() {
        return one;
    }

    public boolean isTwo() {
        return !one;
    }

    public synchronized void resetAndPopulate(MapWrapper mapWrap) {
        reset();
        if (this.isOne()) {
            returnCode(mapWrap.getReturnCode1()).reasonCode(mapWrap.getReasonCode1()).message(mapWrap.getMessage1());
        } else {
            returnCode(mapWrap.getReturnCode2()).reasonCode(mapWrap.getReasonCode2()).message(mapWrap.getMessage2());
        }
    }

    // @formatter:off
    public static GeosupportReturnCodeFixture fromMap(Map<String, Object> map, boolean isRc1) {
        GeosupportReturnCodeFixture result = new GeosupportReturnCodeFixture(isRc1);
        MapWrapper mapWrap = new MapWrapper(map);
        if (isRc1) {
            result.returnCode(mapWrap.getReturnCode1())
                    .reasonCode(mapWrap.getReasonCode1())
                    .message(mapWrap.getMessage1());
        } else {
            result.returnCode(mapWrap.getReturnCode2())
                    .reasonCode(mapWrap.getReasonCode2())
                    .message(mapWrap.getMessage2());
        }
        return result;
    }
    // @formatter:on
}