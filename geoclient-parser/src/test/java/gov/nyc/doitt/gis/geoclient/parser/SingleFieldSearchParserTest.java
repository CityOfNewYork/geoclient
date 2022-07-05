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
package gov.nyc.doitt.gis.geoclient.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;
import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpec;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ParserConfig.class})
public class SingleFieldSearchParserTest extends AbstractSpecTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleFieldSearchParserTest.class);

    @Autowired
    private SingleFieldSearchParser parser;

    @Test
    public void testParse()
    {
        List<ChunkSpec> specs =  specBuilder.getSpecs("AllParsers");
        assertThat(specs.isEmpty()).isFalse();
        for (ChunkSpec spec : specs)
        {
            logSpecStart(LOGGER, spec);
            Input input = spec.input();
            LocationTokens locationTokens = this.parser.parse(input);
            assertChunksEquals(spec.getId(), spec.getChunks(), locationTokens.getChunks(), LOGGER);
        }
    }


}
