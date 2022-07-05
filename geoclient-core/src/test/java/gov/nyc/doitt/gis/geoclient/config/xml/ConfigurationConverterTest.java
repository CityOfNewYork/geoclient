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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thoughtworks.xstream.XStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;
import gov.nyc.doitt.gis.geoclient.test.Fixtures;

public class ConfigurationConverterTest
{

    private ConfigurationConverter.Metadata metadata;
    private ConfigurationConverter converter;
    private XStream xstream;

    @BeforeEach
    public void setUp()
    {
        metadata = new Fixtures().configurationConverterMetadata();
        converter = new ConfigurationConverter(metadata);
        xstream = new XStreamBuilder()
                    .addAllClassesInSamePackageAs(DefaultConfiguration.class)
                    .registerConverter(converter)
                    .alias("configuration", DefaultConfiguration.class)
                    .build();
    }

    @Test
    public void testCanConvert()
    {
        assertTrue(this.converter.canConvert(DefaultConfiguration.class));
        assertFalse(this.converter.canConvert(Configuration.class));
    }

    @Test
    public void testUnmarshalEmpty()
    {
        DefaultConfiguration result = (DefaultConfiguration) xstream.fromXML("<configuration></configuration>");
        assertNotNull(result);
        assertNotNull(result.requiredArguments());
        assertTrue(result.requiredArguments().isEmpty());
    }

    @Test
    public void testUnmarshal()
    {
        DefaultConfiguration result = (DefaultConfiguration) xstream
                .fromXML("<configuration>" + "<requiredArgument name=\"arg1\" value=\"val1\"/>"
                        + "<requiredArgument name=\"arg2\" value=\"val2\"/>" + "</configuration>");
        assertNotNull(result);
        assertNotNull(result.requiredArguments());
        assertEquals("val1", result.requiredArguments().get("arg1"));
        assertEquals("val2", result.requiredArguments().get("arg2"));
    }

    @Test
    public void testMarshal()
    {
assertThrows(UnsupportedOperationException.class, () -> {
        this.converter.marshal(new DefaultConfiguration(), null, null);
});
    }

}
