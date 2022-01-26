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
package gov.nyc.doitt.gis.geoclient.service.search.web.response;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import gov.nyc.doitt.gis.geoclient.service.xstream.MapConverter;

public class SearchSummary
{
    @XStreamAsAttribute
    private String level;
    @XStreamAsAttribute
    private MatchStatus status;
    @XStreamAsAttribute
    private String request;
    @XStreamAlias("geosupportResponse")
    @XStreamConverter(MapConverter.class)
    private Map<String, Object> response;

    public MatchStatus getStatus()
    {
        return status;
    }
    public void setStatus(MatchStatus status)
    {
        this.status = status;
    }
    public String getRequest()
    {
        return request;
    }
    public void setRequest(String request)
    {
        this.request = request;
    }
    public Map<String, Object> getResponse()
    {
        return response;
    }
    public void setResponse(Map<String, Object> response)
    {
        this.response = response;
    }
    public String getLevel()
    {
        return level;
    }
    public void setLevel(String level)
    {
        this.level = level;
    }
}
