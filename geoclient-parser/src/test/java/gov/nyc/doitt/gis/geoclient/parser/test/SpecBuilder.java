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
package gov.nyc.doitt.gis.geoclient.parser.test;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.springframework.util.ClassUtils;

public class SpecBuilder
{
    private static final String PARSER_TEST_DATA_FILE = "specs.xml";
    private final XStream xStream;
    private final UnparsedSpecs unparsedTokenSpecs;

    public SpecBuilder()
    {
        xStream = new XStream(new DomDriver());
        xStream.allowTypes(new Class[] {UnparsedSpecs.class});
        xStream.setMode(XStream.ID_REFERENCES);
        xStream.processAnnotations(UnparsedSpecs.class);
        this.unparsedTokenSpecs = (UnparsedSpecs) xStream.fromXML(ClassUtils.getDefaultClassLoader().getResourceAsStream(
                PARSER_TEST_DATA_FILE));
    }

    public List<ChunkSpec> getSpecs(String testAttribute)
    {
        ChunkSpecParser specParser = new ChunkSpecParser();
        List<ChunkSpec> specs = new ArrayList<>();
        for (UnparsedSpec unparsed : this.unparsedTokenSpecs.getSpecs())
        {
            if (testAttribute.equalsIgnoreCase(unparsed.getTest()))
            {
                specs.add(specParser.parse(unparsed.getId(),unparsed.getBody()));
            }
        }
        return specs;
    }
}
