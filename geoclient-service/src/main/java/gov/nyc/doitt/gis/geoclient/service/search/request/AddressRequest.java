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


public class AddressRequest extends PlaceRequest
{
    private InputValue basicHouseNumberInputValue;
    private InputValue houseNumberSuffixInputValue;

    public AddressRequest()
    {
        super();
    }

    // Copy constructor
    public AddressRequest(AddressRequest anotherRequest)
    {
        super(anotherRequest);
        this.basicHouseNumberInputValue = anotherRequest.getBasicHouseNumberInputValue();
        this.houseNumberSuffixInputValue = anotherRequest.getHouseNumberSuffixInputValue();
    }

    public String getHouseNumber()
    {
        String basic = stringValueOrNull(basicHouseNumberInputValue);
        String suffix = stringValueOrNull(houseNumberSuffixInputValue);
        if(basic == null && suffix == null)
        {
            return null;
        }
        return (basic != null ? basic : "") + (suffix != null ? suffix : "");
    }

    public InputValue getBasicHouseNumberInputValue()
    {
        return basicHouseNumberInputValue;
    }

    public void setBasicHouseNumberInputValue(InputValue basicHouseNumberInputValue)
    {
        this.basicHouseNumberInputValue = basicHouseNumberInputValue;
    }

    public InputValue getHouseNumberSuffixInputValue()
    {
        return houseNumberSuffixInputValue;
    }

    public void setHouseNumberSuffixInputValue(InputValue houseNumberSuffixInputValue)
    {
        this.houseNumberSuffixInputValue = houseNumberSuffixInputValue;
    }

    @Override
    public String toString()
    {
        return "AddressRequest [level=" + getLevel() + ", houseNumber=" + getHouseNumber() + ", street=" + getStreet() + ", borough=" + getBorough() + ", zip=" + getZip() + "]";
    }

    @Override
    public boolean containsAssignedValue()
    {
        return super.containsAssignedValue() ||
                isAssigned(basicHouseNumberInputValue) || isAssigned(houseNumberSuffixInputValue);
    }

    // TODO TESTME
    @Override
    public String summarize()
    {
        return String.format("address [houseNumber=%s, street=%s, borough=%s, zip=%s]", getHouseNumber(), getStreet(), getBorough(), getZip());
    }
}
