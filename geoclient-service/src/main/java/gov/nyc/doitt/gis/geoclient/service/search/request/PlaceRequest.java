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
package gov.nyc.doitt.gis.geoclient.service.search.request;

import gov.nyc.doitt.gis.geoclient.service.search.InputValue;



public class PlaceRequest extends CountyRequest
{
    private InputValue streetInputValue;

    public PlaceRequest()
    {
        super();
    }

    // Copy constructor
    public PlaceRequest(PlaceRequest anotherRequest)
    {
        super(anotherRequest.getLevel(), anotherRequest.getBoroughInputValue(), anotherRequest.getZipInputValue());
        this.streetInputValue = anotherRequest.getStreetInputValue();
    }

    public String getStreet()
    {
        return stringValueOrNull(streetInputValue);
    }

    public InputValue getStreetInputValue()
    {
        return streetInputValue;
    }

    public void setStreetInputValue(InputValue streetInputValue)
    {
        this.streetInputValue = streetInputValue;
    }

    @Override
    public String toString()
    {
        return "PlaceRequest [level= " + getLevel() + ", street=" + getStreet() + ", borough=" + getBorough() + ", zip=" + getZip() + "]";
    }

    @Override
    public boolean containsAssignedValue()
    {
        return super.containsAssignedValue() || isAssigned(streetInputValue);
    }

    // TODO TESTME
    @Override
    public String summarize()
    {
        return String.format("place [name=%s, borough=%s, zip=%s]", getStreet(), getBorough(), getZip());
    }
}