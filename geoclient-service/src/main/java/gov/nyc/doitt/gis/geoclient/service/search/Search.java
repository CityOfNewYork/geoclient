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

import java.util.List;

import gov.nyc.doitt.gis.geoclient.service.search.request.Request;

public class Search
{
    private final Request request;
    private final Response response;
    public Search(Request request, Response response)
    {
        super();
        this.request = request;
        this.response = response;
    }
    public Request getRequest()
    {
        return request;
    }
    public Response getResponse()
    {
        return response;
    }

    public boolean isRejected()
    {
        return response.isRejected();
    }

    public List<String> getSimilarNames()
    {
        return response.getSimilarNames();
    }

    public boolean responseMessageAppliesTo(String streetName)
    {
        return this.response.messageAppliesTo(streetName);
    }

    public ResponseStatus getResponseStatus()
    {
        return response.getResponseStatus();
    }
    public int getLevel()
    {
        return request.getLevel();
    }
    public boolean lessThanOrEqualTo(int level)
    {
        return getLevel() <= level;
    }

    @Override
    public String toString()
    {
        return "Search [request=" + request + ", responseIsRejected? " + response.isRejected() + "]";
    }
}
