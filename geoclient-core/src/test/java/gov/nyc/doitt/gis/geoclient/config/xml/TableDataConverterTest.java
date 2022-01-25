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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.doc.TableData;

public class TableDataConverterTest {
    private TableDataConverter converter;
    private HierarchicalStreamReader readerMock;
    private String text;

    @BeforeEach
    public void setUp() {
        converter = new TableDataConverter();
        readerMock = mock(HierarchicalStreamReader.class);
        text = "I'm too texty for my shirt!";
    }

    @Test
    public void testCanConvert() {
        assertTrue(this.converter.canConvert(TableData.class));
        assertFalse(this.converter.canConvert(String.class));
    }

    @Test
    public void testMarshal() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.converter.marshal(null, null, null);
        });
    }

    @Test
    public void testUnmarshal_withColspan() {
        setUpReaderMock("2", text, null, null, DocumentationXmlReader.XML_TD_ELEMENT);
        TableData td = (TableData) this.converter.unmarshal(readerMock, null);
        assertEquals(2, td.getColspan());
        assertEquals(text, td.getText());
        assertNull(td.getFootnote());
        assertFalse(td.isHeader());
    }

    @Test
    public void testUnmarshal_isHeader() {
        setUpReaderMock(null, text, null, null, DocumentationXmlReader.XML_TH_ELEMENT);
        TableData td = (TableData) this.converter.unmarshal(readerMock, null);
        assertEquals(1, td.getColspan());
        assertEquals(text, td.getText());
        assertNull(td.getFootnote());
        assertTrue(td.isHeader());
    }

    @Test
    public void testUnmarshal_textWithFootnote() {
        setUpReaderMock(null, text, "*", "8", DocumentationXmlReader.XML_TD_ELEMENT);
        TableData td = (TableData) this.converter.unmarshal(readerMock, null);
        assertEquals(1, td.getColspan());
        assertEquals(text, td.getText());
        assertEquals("*", td.getFootnote().getSymbol());
        assertEquals(8, td.getFootnote().getPosition());
        assertFalse(td.isHeader());
    }

    @Test
    public void testUnmarshal_footnoteOnly() {
        setUpReaderMock(null, null, "*", "0", DocumentationXmlReader.XML_TD_ELEMENT);
        TableData td = (TableData) this.converter.unmarshal(readerMock, null);
        assertEquals(1, td.getColspan());
        assertEquals("", td.getText());
        assertEquals("*", td.getFootnote().getSymbol());
        assertEquals(0, td.getFootnote().getPosition());
        assertFalse(td.isHeader());
    }

    @Test
    public void testUnmarshal_emptyElement() {
        setUpReaderMock(null, null, null, null, DocumentationXmlReader.XML_TD_ELEMENT);
        TableData td = (TableData) this.converter.unmarshal(readerMock, null);
        assertEquals(1, td.getColspan());
        assertEquals("", td.getText());
        assertNull(td.getFootnote());
        assertFalse(td.isHeader());
    }

    private void setUpReaderMock(String colspan, String text, String footnote, String footnotePosition,
            String nodeName) {
        when(this.readerMock.getAttribute(DocumentationXmlReader.XML_TD_ATTRIBUTE_COLSPAN)).thenReturn(colspan);

        if (text != null && footnote != null) {
            // With Footnote
            // <td><sup position="2">*</sup>some text</td>
            when(this.readerMock.getValue()).thenReturn(footnote).thenReturn(text);
            when(this.readerMock.hasMoreChildren()).thenReturn(true);
            when(this.readerMock.getAttribute(DocumentationXmlReader.XML_FOOTNOTE_ATTRIBUTE_POSITION))
                    .thenReturn(footnotePosition);

        } else if (text != null) {
            // <td>some text</td>
            when(this.readerMock.getValue()).thenReturn(text);
            when(this.readerMock.hasMoreChildren()).thenReturn(false);
        } else if (footnote != null) {
            // With Footnote - no text
            // <td><sup position="2">*</sup></td>
            when(this.readerMock.getValue()).thenReturn(footnote).thenReturn("");
            when(this.readerMock.hasMoreChildren()).thenReturn(true);
            when(this.readerMock.getAttribute(DocumentationXmlReader.XML_FOOTNOTE_ATTRIBUTE_POSITION))
                    .thenReturn(footnotePosition);
        } else {
            // <td></td>
            when(this.readerMock.getValue()).thenReturn("");
            when(this.readerMock.hasMoreChildren()).thenReturn(false);
        }

        when(this.readerMock.getNodeName()).thenReturn(nodeName);
    }
}
