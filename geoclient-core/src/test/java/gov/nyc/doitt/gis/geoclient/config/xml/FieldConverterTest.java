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
package gov.nyc.doitt.gis.geoclient.config.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.test.Fixtures;

public class FieldConverterTest {
    private FieldConverter.Metadata metadata;
    private FieldConverter converter;
    private HierarchicalStreamReader readerMock;
    private UnmarshallingContext contextMock;
    private String id;
    private String start;
    private String length;
    private String type;
    private String isInput;
    private String alias;
    private String whitespace;

    @BeforeEach
    public void setUp() throws Exception {
        this.metadata = new Fixtures().fieldConverterMetadata();
        this.converter = new FieldConverter(this.metadata);
        this.readerMock = Mockito.mock(HierarchicalStreamReader.class);
        this.id = "returnCodeId";
        this.start = "12";
        this.length = "6";
        this.type = metadata.xmlFieldValueCompositeType;
        this.isInput = "trUe";
        this.alias = "returnCodeAlias";
        this.whitespace = "TRUE";
        contextMock = Mockito.mock(UnmarshallingContext.class);
    }

    @Test
    public void testCanConvert() {
        assertTrue(this.converter.canConvert(Field.class));
        assertFalse(this.converter.canConvert(Object.class));
    }

    @Test
    public void testMarshal() {

        assertThrows(UnsupportedOperationException.class, () -> {
            this.converter.marshal(null, null, null);
        });
    }

    @Test
    public void testUnmarshal_compositeType() {
        prepareReaderMock(this.type);
        Field result = (Field) this.converter.unmarshal(readerMock, contextMock);
        assertFieldResult(true, result);
    }

    @Test
    public void testUnmarshal_regularType() {
        prepareReaderMock("REG");
        Field result = (Field) this.converter.unmarshal(readerMock, contextMock);
        assertFieldResult(false, result);
    }

    @Test
    public void testUnmarshal_nullType() {
        prepareReaderMock(null);
        Field result = (Field) this.converter.unmarshal(readerMock, contextMock);
        assertFieldResult(false, result);
    }

    private void prepareReaderMock(String typeToUse) {
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeId)).thenReturn(id);
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeStart)).thenReturn(start);
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeLength)).thenReturn(this.length);
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeType)).thenReturn(typeToUse);
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeInput)).thenReturn(this.isInput);
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeAlias)).thenReturn(this.alias);
        Mockito.when(this.readerMock.getAttribute(metadata.xmlFieldAttributeWhitespace)).thenReturn(this.whitespace);
    }

    private void assertFieldResult(boolean isComposite, Field field) {
        assertEquals(this.id, field.getId());
        Integer startInt = Integer.valueOf(start);
        assertEquals(new Integer(startInt - 1), field.getStart());
        Integer lengthInt = Integer.valueOf(this.length);
        assertEquals(lengthInt, field.getLength());
        assertEquals(isComposite, field.isComposite());
        boolean impBool = Boolean.parseBoolean(this.isInput);
        assertEquals(impBool, field.isInput());
        assertEquals(this.alias, field.getAlias());
        boolean whitespaceBool = Boolean.parseBoolean(this.whitespace);
        assertEquals(whitespaceBool, field.isWhitespaceSignificant());
    }

}
