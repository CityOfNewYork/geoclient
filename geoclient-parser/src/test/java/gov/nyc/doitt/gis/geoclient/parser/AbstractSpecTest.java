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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.List;

import difflib.DiffUtils;
import difflib.Patch;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;

import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpec;
import gov.nyc.doitt.gis.geoclient.parser.test.SpecBuilder;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;

public abstract class AbstractSpecTest
{
    protected static SpecBuilder specBuilder;

    @BeforeAll
    public static void setUpOnce()
    {
        specBuilder = new SpecBuilder();
    }

    protected void testParser(Parser parser, Logger logger)
    {
        List<ChunkSpec> specs =  specBuilder.getSpecs(parser.getName());
        assertFalse(specs.isEmpty());

        for (ChunkSpec spec : specs)
        {
            logSpecStart(logger, spec);
            Input input = new Input(spec.getId(), spec.plainText());
            ParseContext context = new ParseContext(input);
            parser.parse(context);
            assertChunksEquals(spec.getId(), spec.getChunks(), context.getChunks(), logger);
        }
    }

    protected void logSpecStart(Logger logger, ChunkSpec spec)
    {
        logger.debug("-----------------------------------------------------------------------");
        logger.debug(String.format("TEST-%s is expecting Tokens: '%s', Chunks: '%s'", spec.getId(), spec.delimitedText(),spec.chunkTypeNames()));
    }

    protected void assertChunksEquals(String specId, List<Chunk> expected, List<Chunk> actual, Logger logger)
    {
        if(!expected.equals(actual))
        {
            String messageStart = String.format("Test %s ",specId);
            int expectedSize = expected.size();
            int actualSize = actual.size();
            assertThat(actualSize).isEqualTo(expectedSize).as(messageStart + "expected " + expectedSize + " tokens but got " +actualSize);
            for (int i = 0; i < expectedSize; i++)
            {
                Chunk expectedChunk = expected.get(i);
                Chunk actualChunk = actual.get(i);
                if(!expectedChunk.equals(actualChunk))
                {
                    assertThat(actualChunk.getText()).isEqualTo(expectedChunk.getText()).as(String.format("Test %s Chunk texts are not equal.",specId));
                    assertThat(actualChunk.getType()).isEqualTo(expectedChunk.getType());
                    assertTokensEqual(specId,expectedChunk.getTokens(), actualChunk.getTokens(),logger);
                }
            }
        }
    }

    protected void assertTokensEqual(String specId, List<Token> expected, List<Token> actual, Logger logger)
    {
        if(!expected.equals(actual))
        {
            String messageStart = String.format("Test %s ",specId);
            int expectedSize = expected.size();
            int actualSize = actual.size();
            assertThat(actualSize).isEqualTo(expectedSize).as(messageStart + "expected " + expectedSize + " tokens but got " +actualSize);
            for (int i = 0; i < expectedSize; i++)
            {
                Token expectedToken = expected.get(i);
                Token actualToken = actual.get(i);
                assertEquals(expectedToken.getType(),actualToken.getType(), messageStart + "expected Token of type " + expectedToken.getType() + " but found " + actualToken.getType());
                String expectedValue = expectedToken.getValue();
                String actualValue = actualToken.getValue();
                List<String> expectedForDiff = Arrays.asList(expectedValue);
                List<String> actualForDiff = Arrays.asList(actualValue);
                Patch<String> patch = DiffUtils.diff(expectedForDiff, actualForDiff);
                logger.debug("Patch:{}",patch.toString());
                logger.debug("Deltas:{}",patch.getDeltas());
                assertEquals(String.format("%s found incorrect token value:",messageStart), expectedValue, actualValue);

            }
        }
    }

}
