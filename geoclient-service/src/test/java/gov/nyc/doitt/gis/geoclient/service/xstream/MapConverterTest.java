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
package gov.nyc.doitt.gis.geoclient.service.xstream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MapConverterTest {
    private HierarchicalStreamWriter writerMock;
    private MapConverter mapConverter;
    private Map<String, Object> data;

    @BeforeEach
    public void setUp() throws Exception {
        this.writerMock = Mockito.mock(HierarchicalStreamWriter.class);
        this.mapConverter = new MapConverter();
        this.data = new HashMap<String, Object>();
        this.data.put("ONE", 1.0);
        this.data.put("TWO", "2");
        this.data.put("THREE", null);

    }

    @Test
    public void testCanConvert() {
        assertTrue(this.mapConverter.canConvert(Map.class));
        assertTrue(this.mapConverter.canConvert(SortedMap.class));
        assertTrue(this.mapConverter.canConvert(HashMap.class));
        assertTrue(this.mapConverter.canConvert(TreeMap.class));
        assertFalse(this.mapConverter.canConvert(String.class));
    }

    @Test
    public void testMarshal() {
        this.mapConverter.marshal(this.data, this.writerMock, null);
        Mockito.verify(this.writerMock).startNode("ONE");
        Mockito.verify(this.writerMock).setValue("1.0");
        Mockito.verify(this.writerMock).startNode("TWO");
        Mockito.verify(this.writerMock).setValue("2");
        Mockito.verify(this.writerMock).startNode("THREE");
        Mockito.verify(this.writerMock).setValue(null);
        Mockito.verify(this.writerMock, Mockito.times(3)).endNode();
    }

    @Test
    public void testMarshalNestedMap() {
        Map<String, Object> outerMap = new HashMap<String, Object>();
        outerMap.put("Foo", this.data);
        this.mapConverter.marshal(outerMap, this.writerMock, null);
        Mockito.verify(this.writerMock).startNode("Foo");
        Mockito.verify(this.writerMock).startNode("ONE");
        Mockito.verify(this.writerMock).setValue("1.0");
        Mockito.verify(this.writerMock).startNode("TWO");
        Mockito.verify(this.writerMock).setValue("2");
        Mockito.verify(this.writerMock).startNode("THREE");
        Mockito.verify(this.writerMock).setValue(null);
        Mockito.verify(this.writerMock, Mockito.times(4)).endNode();
    }

    @Test
    public void testUnmarshal() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.mapConverter.unmarshal(null, null);
        });
    }

}
