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



public class BblRequest extends CountyRequest
{
    private InputValue blockInputValue;
    private InputValue lotInputValue;

    public BblRequest()
    {
        super();
    }

    // Copy constructor
    public BblRequest(BblRequest anotherRequest)
    {
        super(anotherRequest.getLevel(), anotherRequest.getBoroughInputValue(), null);
        this.blockInputValue = anotherRequest.getBlockInputValue();
        this.lotInputValue = anotherRequest.getLotInputValue();
    }

    public String getBlock()
    {
        return stringValueOrNull(blockInputValue);
    }

    public String getLot()
    {
        return stringValueOrNull(lotInputValue);
    }

    public InputValue getBlockInputValue()
    {
        return blockInputValue;
    }

    public void setBlockInputValue(InputValue blockInputValue)
    {
        this.blockInputValue = blockInputValue;
    }

    public InputValue getLotInputValue()
    {
        return lotInputValue;
    }

    public void setLotInputValue(InputValue lotInputValue)
    {
        this.lotInputValue = lotInputValue;
    }

    @Override
    public void setZipInputValue(InputValue zipInputValue)
    {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support zip input.");
    }

    @Override
    public String toString()
    {
        return "BblRequest [level= " + getLevel() + ", borough=" + getBorough() + ", block=" + getBlock() + ", lot=" + getLot() + "]";
    }

    @Override
    public boolean containsAssignedValue()
    {
        return super.containsAssignedValue() ||
                isAssigned(blockInputValue) || isAssigned(lotInputValue);
    }

    // TODO TESTME
    @Override
    public String summarize()
    {
        return String.format("bbl [borough=%s, block=%s, lot=%s]", getBorough(), getBlock(), getLot());
    }

}
