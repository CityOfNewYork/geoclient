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

public class IntersectionRequest extends CountyRequest
{
    private InputValue crossStreetOneInputValue;
    private InputValue crossStreetTwoInputValue;
    private InputValue compassDirectionInputValue;

    public IntersectionRequest()
    {
        super();
    }

    public IntersectionRequest(IntersectionRequest anotherRequest)
    {
        super(anotherRequest.getLevel(), anotherRequest.getBoroughInputValue(), anotherRequest.getZipInputValue());
        this.crossStreetOneInputValue = anotherRequest.getCrossStreetOneInputValue();
        this.crossStreetTwoInputValue = anotherRequest.getCrossStreetTwoInputValue();
        this.compassDirectionInputValue = anotherRequest.getCompassDirectionInputValue();
    }

    public String getCrossStreetOne()
    {
        return stringValueOrNull(crossStreetOneInputValue);
    }

    public String getCrossStreetTwo()
    {
        return stringValueOrNull(crossStreetTwoInputValue);
    }

    public String getCompassDirection()
    {
        return stringValueOrNull(compassDirectionInputValue);
    }

    public InputValue getCrossStreetOneInputValue()
    {
        return crossStreetOneInputValue;
    }

    public void setCrossStreetOneInputValue(InputValue crossStreetOneInputValue)
    {
        this.crossStreetOneInputValue = crossStreetOneInputValue;
    }

    public InputValue getCrossStreetTwoInputValue()
    {
        return crossStreetTwoInputValue;
    }

    public void setCrossStreetTwoInputValue(InputValue crossStreetTwoInputValue)
    {
        this.crossStreetTwoInputValue = crossStreetTwoInputValue;
    }

    public InputValue getCompassDirectionInputValue()
    {
        return compassDirectionInputValue;
    }

    public void setCompassDirectionInputValue(InputValue compassDirectionInputValue)
    {
        this.compassDirectionInputValue = compassDirectionInputValue;
    }

    @Override
    public String toString()
    {
        return "IntersectionRequest [level= " + getLevel() + ", crossStreetOne=" + getCrossStreetOne() + ", crossStreetTwo=" + getCrossStreetTwo() + ", compassDirection=" + getCompassDirection() + ", borough=" + getBorough() + ", zip=" + getZip() + "]";
    }

    @Override
    public boolean containsAssignedValue()
    {
        return super.containsAssignedValue() ||
                isAssigned(crossStreetOneInputValue) ||
                isAssigned(crossStreetTwoInputValue) ||
                isAssigned(compassDirectionInputValue);
    }
    // TODO TESTME
    @Override
    public String summarize()
    {
        return String.format("intersection [crossStreetOne=%s, crossStreetTwo=%s, borough=%s, compassDirection=%s]", getCrossStreetOne(), getCrossStreetTwo(), getBorough(), getCompassDirection());
    }
}
