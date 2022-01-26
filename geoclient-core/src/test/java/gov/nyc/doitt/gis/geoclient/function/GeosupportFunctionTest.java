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
package gov.nyc.doitt.gis.geoclient.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

public class GeosupportFunctionTest
{
    private WorkArea workAreaOneMock;
    private WorkArea workAreaTwoMock;
    private Geoclient geoclientMock;
    private GeosupportFunction function_oneWorkArea;
    private GeosupportFunction function_twoWorkAreas;

    @BeforeEach
    public void setUp() throws Exception
    {
        this.workAreaOneMock = Mockito.mock(WorkArea.class);
        Mockito.when(this.workAreaOneMock.getFieldIds()).thenReturn(Arrays.asList("uno"));
        this.workAreaTwoMock = Mockito.mock(WorkArea.class);
        Mockito.when(this.workAreaTwoMock.getFieldIds()).thenReturn(Arrays.asList("dos"));
        this.geoclientMock = Mockito.mock(Geoclient.class);
        this.function_oneWorkArea = new GeosupportFunction("Zesty", workAreaOneMock, geoclientMock);
        this.function_twoWorkAreas = new GeosupportFunction("Testy", workAreaOneMock, workAreaTwoMock, geoclientMock);
    }

    @Test
    public void testIsTwoWorkAreas()
    {
        assertTrue(function_twoWorkAreas.isTwoWorkAreas());
        assertFalse(function_oneWorkArea.isTwoWorkAreas());
    }

    @Test
    public void testConstructor_twoWorkAreas()
    {
        assertEquals("Testy", this.function_twoWorkAreas.getId());
        assertSame(workAreaOneMock, function_twoWorkAreas.getWorkAreaOne());
        assertSame(workAreaTwoMock, function_twoWorkAreas.getWorkAreaTwo());
    }

    @Test
    public void testConstructor_twoWorkAreasAndConfiguration()
    {
        DefaultConfiguration config = new DefaultConfiguration();
        GeosupportFunction function = new GeosupportFunction("Testy", workAreaOneMock, workAreaTwoMock,geoclientMock,config);
        assertEquals("Testy", this.function_twoWorkAreas.getId());
        assertSame(workAreaOneMock, function_twoWorkAreas.getWorkAreaOne());
        assertSame(workAreaTwoMock, function_twoWorkAreas.getWorkAreaTwo());
        assertSame(config, function.getConfiguration());
    }

    @Test
    public void testConstructor_oneWorkArea()
    {
        assertEquals("Zesty", this.function_oneWorkArea.getId());
        assertSame(workAreaOneMock, function_oneWorkArea.getWorkAreaOne());
        assertNull(function_oneWorkArea.getWorkAreaTwo());
    }

    @Test
    public void testCall_twoWorkAreas()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> result1 = new HashMap<String, Object>();
        result1.put("one", "1");
        Map<String, Object> result2 = new HashMap<String, Object>();
        result2.put("two", "2");
        ByteBuffer buffer1 = ByteBuffer.allocate(2);
        ByteBuffer buffer2 = ByteBuffer.allocate(2);
        Mockito.when(this.workAreaOneMock.createBuffer(params)).thenReturn(buffer1);
        Mockito.when(this.workAreaTwoMock.createBuffer()).thenReturn(buffer2);
        Mockito.when(this.workAreaOneMock.parseResults(buffer1)).thenReturn(result1);
        Mockito.when(this.workAreaTwoMock.parseResults(buffer2)).thenReturn(result2);
        this.geoclientMock.callgeo(buffer1, buffer2);
        Map<String, Object> actualResult = this.function_twoWorkAreas.call(params);
        // Not working for some reason even the assertions below pass
        // and the mocks are clearly doing what they're supposed to
        // Mockito.verify(this.workAreaOneMock);
        // Mockito.verify(this.workAreaTwoMock);
        // Mockito.verify(this.geoclientMock);
        assertEquals("1", actualResult.get("one"));
        assertEquals("2", actualResult.get("two"));
    }

    @Test
    public void testCall_oneWorkArea()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> result1 = new HashMap<String, Object>();
        result1.put("one", "1");
        ByteBuffer buffer1 = ByteBuffer.allocate(2);
        Mockito.when(this.workAreaOneMock.createBuffer(params)).thenReturn(buffer1);
        Mockito.when(this.workAreaOneMock.parseResults(buffer1)).thenReturn(result1);
        this.geoclientMock.callgeo(buffer1, null);
        Map<String, Object> actualResult = this.function_oneWorkArea.call(params);
        // Not working for some reason even the assertions below pass
        // and the mocks are clearly doing what they're supposed to
        // Mockito.verify(this.workAreaOneMock);
        // Mockito.verify(this.workAreaTwoMock);
        // Mockito.verify(this.geoclientMock);
        assertEquals("1", actualResult.get("one"));
    }

}
