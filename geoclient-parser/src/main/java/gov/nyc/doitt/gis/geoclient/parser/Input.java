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
package gov.nyc.doitt.gis.geoclient.parser;

import gov.nyc.doitt.gis.geoclient.parser.token.TextUtils;
import gov.nyc.doitt.gis.geoclient.parser.util.Assert;


/**
 * @author mlipper
 *
 */
public class Input
{
    private final String id;
    private final String value;
    private final String unsanitizedValue;

    public Input(String id, String inputString)
    {
        super();
        Assert.hasText(id, "id argument cannot be empty or null.");
        this.id = id;
        Assert.hasText(inputString, "inputString argument cannot be empty or null.");
        this.unsanitizedValue = inputString;
        this.value = TextUtils.sanitize(this.unsanitizedValue);
        Assert.hasText(this.value, "inputString cannot be empty or null after sanitization.");
    }

    public String getId()
    {
        return id;
    }

    public String getValue()
    {
        return value;
    }

    public String getUnsanitizedValue()
    {
        return unsanitizedValue;
    }

    @Override
    public String toString()
    {
        return "Input [id=" + id + ", value=" + value + "]";
    }

}
