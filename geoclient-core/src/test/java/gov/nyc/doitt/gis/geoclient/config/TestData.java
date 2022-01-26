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
package gov.nyc.doitt.gis.geoclient.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gov.nyc.doitt.gis.geoclient.function.Field;

public class TestData
{
    public static Field fieldOne = new Field("one", 1, 6);
    public static Field fieldTwo = new Field("two", 7, 6);
    public static Field fieldThree = new Field("three", 1, 12, true);
    public static Field fieldDuplicateIdOfOne = new Field("one", 13, 4);

    public static List<Field> newFieldList(Field... fields)
    {
        // Arrays.asList(fields) creates an unmodifiable List
        List<Field> result = new ArrayList<Field>();
        result.addAll(Arrays.asList(fields));
        return result;
    }

    public static List<Field> newFieldList(Comparator<Field> comparator, Field... fields)
    {
        // Arrays.asList(fields) creates an unmodifiable List
        List<Field> result = Arrays.asList(fields);
        Collections.sort(result);
        return new ArrayList<Field>(result);
    }

    public static Field makeField(String id, int start, int length)
    {
        return new Field(id, start, length);
    }
}
