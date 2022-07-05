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

import java.util.ArrayList;
import java.util.List;

public class ValueResolution
{
    private final List<InputValue> resolved = new ArrayList<>();
    private final List<InputValue> unresolved = new ArrayList<>();

    public void add(InputValue inputValue)
    {
        if (inputValue.isResolved())
        {
            resolved.add(inputValue);
        } else
        {
            unresolved.add(inputValue);
        }
    }

    public List<InputValue> resolved()
    {
        return this.resolved;
    }

    public InputValue resolvedValue(int index)
    {
        return this.resolved.get(index);
    }

    public InputValue unresolvedValue(int index)
    {
        return this.unresolved.get(index);
    }

    public int resolvedCount()
    {
        return this.resolved.size();
    }

    public int unresolvedCount()
    {
        return this.unresolved.size();
    }

    public int totalCount()
    {
        return this.resolvedCount() + this.unresolvedCount();
    }
}
