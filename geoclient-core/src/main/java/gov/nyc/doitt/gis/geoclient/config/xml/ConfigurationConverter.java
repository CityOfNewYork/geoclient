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

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;


public class ConfigurationConverter implements Converter
{
    private static final Logger log = LoggerFactory.getLogger(ConfigurationConverter.class);

    public static class Metadata {
        public final String xmlRequiredArgumentAttributeName;
        public final String xmlRequiredArgumentAttributeValue;
        public final String xmlRequiredArgumentElement;
        public Metadata(String xmlRequiredArgumentAttributeName,
                String xmlRequiredArgumentAttributeValue, String xmlRequiredArgumentElement)
        {
            super();
            this.xmlRequiredArgumentAttributeName = xmlRequiredArgumentAttributeName;
            this.xmlRequiredArgumentAttributeValue = xmlRequiredArgumentAttributeValue;
            this.xmlRequiredArgumentElement = xmlRequiredArgumentElement;
        }
    }

    private final Metadata metadata;

    public ConfigurationConverter(Metadata metadata)
    {
        super();
        this.metadata = metadata;
    }

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type)
    {
        return DefaultConfiguration.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
    {
        throw new UnsupportedOperationException("Marshalling back to XML is not implemented");
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
    {
        DefaultConfiguration configuration = new DefaultConfiguration();
        Map<String, Object> requiredArguments = new HashMap<String, Object>();
        while(reader.hasMoreChildren()) {
            reader.moveDown();
            if(metadata.xmlRequiredArgumentElement.equals(reader.getNodeName()))
            {
                requiredArguments.put(reader.getAttribute(metadata.xmlRequiredArgumentAttributeName), reader.getAttribute(metadata.xmlRequiredArgumentAttributeValue));
            }
            reader.moveUp();
        }
        configuration.setRequiredArguments(requiredArguments);
        log.trace("Created {}", configuration);
        return configuration;
    }

}