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
import gov.nyc.doitt.gis.geoclient.doc.Footnote;
import gov.nyc.doitt.gis.geoclient.doc.TableData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TableDataConverter implements Converter
{
    private static final Logger log = LoggerFactory.getLogger(TableDataConverter.class);

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type)
    {
        return TableData.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
    {
        throw new UnsupportedOperationException("Marshalling back to XML is not implemented");
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
    {
        String colspanText = reader.getAttribute(DocumentationXmlReader.XML_TD_ATTRIBUTE_COLSPAN);
        int colspan = colspanText != null ? Integer.valueOf(colspanText) : 1;
        // Text
        //StringBuffer value = new StringBuffer(reader.getValue());
        Footnote footnote = null;
        if(reader.hasMoreChildren())
        {
            reader.moveDown();      
            String positionString = reader.getAttribute(DocumentationXmlReader.XML_FOOTNOTE_ATTRIBUTE_POSITION);
            footnote = new Footnote(reader.getValue(),Integer.valueOf(positionString));
            reader.moveUp();
        }
        StringBuffer value = new StringBuffer(reader.getValue());
        boolean isHeader = DocumentationXmlReader.XML_TH_ELEMENT.equalsIgnoreCase(reader.getNodeName());
        TableData td = new TableData(value.toString(), colspan,isHeader,footnote);
        log.trace("Created TableData {}", td);
        return td;
    }


}
