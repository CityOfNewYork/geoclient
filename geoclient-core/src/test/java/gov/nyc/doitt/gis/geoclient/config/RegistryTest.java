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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

public class RegistryTest
{
    private static final String WA_NAME = "WA1";
    private WorkArea workArea = new WorkArea(WA_NAME, new TreeSet<Field>());
    private String functionId = Function.F1B;

    @Test
    public void testWorkAreaRegistry()
    {
        Registry.clearWorkAreas();
        assertFalse(Registry.containsWorkArea(WA_NAME));
        assertNull(Registry.getWorkArea(WA_NAME));
        Registry.addWorkArea(workArea);
        assertTrue(Registry.containsWorkArea(WA_NAME));
        assertSame(workArea, Registry.getWorkArea(WA_NAME));
        Registry.clearWorkAreas();
        assertFalse(Registry.containsWorkArea(WA_NAME));
        assertNull(Registry.getWorkArea(WA_NAME));
    }

    @Test
    public void testFunctionRegistry()
    {
        Registry.clearFunctions();
        Function function = new Function()
        {
            @Override
            public String getId() { return functionId; }
            @Override
            public Map<String, Object> call(Map<String, Object> parameters){ return null; }
            @Override
            public WorkArea getWorkAreaOne(){ return null; }
            @Override
            public WorkArea getWorkAreaTwo() { return null; }
            @Override
            public boolean isTwoWorkAreas() { return false; }
            @Override
            public Configuration getConfiguration(){ return null; }
        };
        assertFalse(Registry.containsFunction(functionId));
        assertNull(Registry.getFunction(functionId));
        Registry.addFunction(function);
        assertTrue(Registry.containsFunction(functionId));
        assertSame(function, Registry.getFunction(functionId));
        Registry.clearFunctions();
        assertFalse(Registry.containsFunction(functionId));
        assertNull(Registry.getFunction(functionId));
    }

}
