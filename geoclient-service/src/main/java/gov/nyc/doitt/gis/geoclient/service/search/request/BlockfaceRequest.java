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

public class BlockfaceRequest extends CountyRequest
{
    private InputValue onStreetInputValue;
    private InputValue crossStreetOneInputValue;
    private InputValue crossStreetTwoInputValue;

    public BlockfaceRequest()
    {
        super();
    }

    public BlockfaceRequest(BlockfaceRequest anotherRequest)
    {
        super(anotherRequest.getLevel(), anotherRequest.getBoroughInputValue(), anotherRequest.getZipInputValue());
        this.onStreetInputValue = anotherRequest.getOnStreetInputValue();
        this.crossStreetOneInputValue = anotherRequest.getCrossStreetOneInputValue();
        this.crossStreetTwoInputValue = anotherRequest.getCrossStreetTwoInputValue();
    }

    public String getOnStreet()
    {
        return stringValueOrNull(onStreetInputValue);
    }

    public String getCrossStreetOne()
    {
        return stringValueOrNull(crossStreetOneInputValue);
    }

    public String getCrossStreetTwo()
    {
        return stringValueOrNull(crossStreetTwoInputValue);
    }

    public InputValue getOnStreetInputValue()
    {
        return onStreetInputValue;
    }

    public void setOnStreetInputValue(InputValue onStreetInputValue)
    {
        this.onStreetInputValue = onStreetInputValue;
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

    @Override
    public String toString()
    {
        return "BlockfaceRequest [level= " + getLevel() + ", onStreet=" + getOnStreet() + ", crossStreetOne=" + getCrossStreetOne()
                + ", crossStreetTwo=" + getCrossStreetTwo() + ", borough=" + getBorough() + ", zip="
                + getZip() + "]";
    }

    @Override
    public boolean containsAssignedValue()
    {
        return super.containsAssignedValue() ||
                isAssigned(onStreetInputValue) ||
                isAssigned(crossStreetOneInputValue) ||
                isAssigned(crossStreetTwoInputValue);
    }
    // TODO TESTME
    @Override
    public String summarize()
    {
        return String.format("blockface [onStreet=%s, crossStreetOne=%s, crossStreetTwo=%s, borough=%s]", getOnStreet(), getCrossStreetOne(), getCrossStreetTwo(), getBorough());
    }

}
