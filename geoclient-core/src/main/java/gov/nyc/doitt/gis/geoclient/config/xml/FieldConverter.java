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
package gov.nyc.doitt.gis.geoclient.config.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Field;

public class FieldConverter implements Converter {
    private static final Logger log = LoggerFactory.getLogger(FieldConverter.class);

    public static class Metadata {
        public final String xmlFieldAttributeId;
        public final String xmlFieldAttributeStart;
        public final String xmlFieldAttributeLength;
        public final String xmlFieldValueCompositeType;
        public final String xmlFieldAttributeType;
        public final String xmlFieldAttributeInput;
        public final String xmlFieldAttributeAlias;
        public final String xmlFieldAttributeWhitespace;
        public final String xmlFieldAttributeOutputAlias;

        public Metadata(String xmlFieldAttributeId, String xmlFieldAttributeStrart, String xmlFieldAttributeLength,
                String xmlFieldValueCompositeType, String xmlFieldAttributeType, String xmlFieldAttributeInput,
                String xmlFieldAttributeAlias, String xmlFieldAttributeWhitespace, String xmlFieldAttributeOutputAlias) {
            super();
            this.xmlFieldAttributeId = xmlFieldAttributeId;
            this.xmlFieldAttributeStart = xmlFieldAttributeStrart;
            this.xmlFieldAttributeLength = xmlFieldAttributeLength;
            this.xmlFieldValueCompositeType = xmlFieldValueCompositeType;
            this.xmlFieldAttributeType = xmlFieldAttributeType;
            this.xmlFieldAttributeInput = xmlFieldAttributeInput;
            this.xmlFieldAttributeAlias = xmlFieldAttributeAlias;
            this.xmlFieldAttributeWhitespace = xmlFieldAttributeWhitespace;
            this.xmlFieldAttributeOutputAlias = xmlFieldAttributeOutputAlias;
        }

    }

    private final Metadata metadata;

    public FieldConverter(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return Field.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        throw new UnsupportedOperationException("Marshalling back to XML is not implemented");
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String id = reader.getAttribute(metadata.xmlFieldAttributeId);
        // The start value from XML is 1-indexed and needs to be adjusted for
        // the Field class which expects a zero-indexed start value.
        int start = Integer.valueOf(reader.getAttribute(metadata.xmlFieldAttributeStart)) - 1;
        int length = Integer.valueOf(reader.getAttribute(metadata.xmlFieldAttributeLength));
        boolean isComposite = metadata.xmlFieldValueCompositeType
                .equalsIgnoreCase(reader.getAttribute(metadata.xmlFieldAttributeType));
        boolean isInput = "true".equalsIgnoreCase(reader.getAttribute(metadata.xmlFieldAttributeInput));
        String alias = reader.getAttribute(metadata.xmlFieldAttributeAlias);
        boolean whitespace = "true".equalsIgnoreCase(reader.getAttribute(metadata.xmlFieldAttributeWhitespace));
        String outputAlias = reader.getAttribute(metadata.xmlFieldAttributeOutputAlias);
        Field field = new Field(id, start, length, isComposite, isInput, alias, whitespace, outputAlias);
        log.debug("Created {}", field);
        return field;
    }

}
