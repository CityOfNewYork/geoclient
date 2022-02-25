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
package gov.nyc.doitt.gis.geoclient.service.search;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Response
{
    private final ResponseStatus responseStatus;
    private final Map<String, Object> geocodes;
    private final Instant timestamp;

    public Response(ResponseStatus responseStatus, Map<String, Object> geocodes)
    {
        super();
        this.responseStatus = responseStatus;
        this.geocodes = geocodes;
        this.timestamp = Instant.now();
    }

    public boolean messageAppliesTo(String streetName)
    {
        GeosupportReturnCode grc = this.responseStatus.getGeosupportReturnCode();
        return StringUtils.containsIgnoreCase(grc.getMessage(), streetName);
    }

    public boolean isCompassDirectionRequired()
    {
        return responseStatus.isCompassDirectionRequired();
    }

    public boolean isRejected()
    {
        return this.responseStatus.isRejected();
    }

    public int similarNamesCount()
    {
        return responseStatus.similarNamesCount();
    }

    public List<String> getSimilarNames()
    {
        return responseStatus.getSimilarNames();
    }

    public ResponseStatus getResponseStatus()
    {
        return responseStatus;
    }

    public Map<String, Object> getGeocodes()
    {
        return geocodes;
    }

    public Instant getTimestamp()
    {
        return timestamp;
    }

    @Override
    public String toString()
    {
        return "Response [responseStatus=" + responseStatus + "]";
    }

}
