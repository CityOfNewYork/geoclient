/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.doc;

public class MissingDocumentation extends ItemDocumentation
{
    public static final String MESSAGE = "Needs documentation.";

    public MissingDocumentation(String id)
    {
        super(id);
        setDescription(new Description(MESSAGE));
    }

    public boolean isDocumented()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return "MissingDocumentation [id=" + getId() + "]";
    }

}
