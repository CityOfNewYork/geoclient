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

public abstract class Request
{
    private int level = 0;

    public Request()
    {
        super();
    }

    public Request(int level)
    {
        super();
        this.level = level;
    }

    public abstract boolean containsAssignedValue();
    public abstract String summarize();

    public void incrementLevel()
    {
        level++;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    protected boolean isAssigned(InputValue inputValue)
    {
        if(inputValue == null)
        {
            return false;
        }
        return inputValue.isAssigned();
    }

    protected String stringValueOrNull(InputValue inputValue)
    {
        return inputValue != null ? inputValue.getValue() : null;
    }
}
