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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpecParser.MutableToken;
import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpecParser.TokenTypeOccurrence;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class ChunkSpecParserTest {
    private ChunkSpecParser specParser;

    @BeforeEach
    public void setUp() throws Exception {
        this.specParser = new ChunkSpecParser();
    }

    @Test
    public void testParse() {
        // '59 Maiden Lane, Manhattan'
        String delimitedTokenText = "[59] [Maiden Lane], [Manhattan]";
        StringBuffer buffer = new StringBuffer(delimitedTokenText);
        buffer.append("=");
        buffer.append(makeChunkDefinition(ChunkType.COUNTY, 0, 25, new TokenTypeOccurrence(TokenType.BOROUGH_NAME, 3)));
        buffer.append("|");
        buffer.append(makeChunkDefinition(ChunkType.ADDRESS, 0, 16, new TokenTypeOccurrence(TokenType.HOUSE_NUMBER, 1),
                new TokenTypeOccurrence(TokenType.STREET_NAME, 2)));
        ChunkSpec actual = this.specParser.parse("testParse-id", buffer.toString());
        assertThat(actual.getId()).isEqualTo("testParse-id");
        assertThat(actual.chunkCount()).isEqualTo(2);
        Chunk expectedFirstChunk = new Chunk(ChunkType.COUNTY, "59 Maiden Lane, Manhattan");
        expectedFirstChunk.add(new Token(TokenType.BOROUGH_NAME, "Manhattan", 16, 25));
        assertThat(actual.firstChunk()).isEqualTo(expectedFirstChunk);
        Chunk expectedSecondChunk = new Chunk(ChunkType.ADDRESS, "59 Maiden Lane, ");
        expectedSecondChunk.add(new Token(TokenType.HOUSE_NUMBER, "59", 0, 2));
        expectedSecondChunk.add(new Token(TokenType.STREET_NAME, "Maiden Lane", 3, 14));
        assertThat(actual.secondChunk()).isEqualTo(expectedSecondChunk);
    }

    @Test
    public void testParseTokenTypeOccurrence() {
        String input = String.format("%s{%d}", TokenType.CITY_NAME, 12);
        TokenTypeOccurrence result = this.specParser.parseTokenTypeOccurrence(input);
        assertThat(result.tokenType()).isEqualTo(TokenType.CITY_NAME);
        assertThat(result.occurrence()).isEqualTo(12);
        assertThat(result.zeroIndexOccurrence()).isEqualTo(11);
    }

    @Test
    public void testCreateDefaultChunkSpec() {
        assertDefaultChunkSpec(this.specParser.createDefaultChunkSpec("123", "abc"), "123", "abc");
    }

    @Test
    public void testParse_idNull() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parse(null, "xyz");
        });
    }

    @Test
    public void testParse_idEmpty() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parse("", "xyz");
        });
    }

    @Test
    public void testParse_unparsedStringNull() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parse("qrx", null);
        });
    }

    @Test
    public void testParse_unparsedStringEmpty() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parse("qrx", "");
        });
    }

    @Test
    public void testParse_noSplitCharReturnsDeafultChunkSpec() {
        assertDefaultChunkSpec(this.specParser.parse("testId", "XXXXYYYY"), "testId", "XXXXYYYY");
    }

    @Test
    public void testParse_splitsIntoTooMany() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parse("testParse", "XX=XXY=YYY");
        });
    }

    @Test
    public void testParse_splitsIntoEmptyReturnsDefaultChunkSpec() {
        assertDefaultChunkSpec(this.specParser.parse("testId", "XXXXY="), "testId", "XXXXY");
    }

    @Test
    public void testParseDelimitedString() {
        // X[YZ] [AB]C[D]
        // XYZ ABCD
        List<ChunkSpecParser.MutableToken> results = this.specParser.parseBracketValues("X[YZ] [AB]C[D]");
        assertThat(results.size()).isEqualTo(3);
        MutableToken tk1 = results.get(0);
        assertThat(tk1.getValue()).isEqualTo("YZ");
        assertThat(tk1.getStart()).isEqualTo(1);
        assertThat(tk1.getEnd()).isEqualTo(3);
        MutableToken tk2 = results.get(1);
        assertThat(tk2.getValue()).isEqualTo("AB");
        assertThat(tk2.getStart()).isEqualTo(4);
        assertThat(tk2.getEnd()).isEqualTo(6);
        MutableToken tk3 = results.get(2);
        assertThat(tk3.getValue()).isEqualTo("D");
        assertThat(tk3.getStart()).isEqualTo(7);
        assertThat(tk3.getEnd()).isEqualTo(8);
    }

    @Test
    public void testParseChunkType_unparsedStringArgumentIsNull() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parseChunkType(null);
        });
    }

    @Test
    public void testParseChunkType_unparsedStringArgumentIsEmpty() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parseChunkType("");
        });
    }

    @Test
    public void testParseChunkType_unparsedStringArgumentDoesNotHaveValidChunkType() {
        assertThrows(TestConfigurationException.class, () -> {
            this.specParser.parseChunkType("FOO(0,1)");
        });
    }

    @Test
    public void testParseChunkType() {
        String input = String.format("%s(0-12:ZIP)", ChunkType.SUBSTRING.toString());
        assertThat(this.specParser.parseChunkType(input)).isEqualTo(ChunkType.SUBSTRING);
    }

    @Test
    public void testParseChunkRange() {
        ChunkSpecParser.Range range = specParser.parseChunkRange("0-12");
        assertThat(range.getStart()).isEqualTo(0);
        assertThat(range.getEnd()).isEqualTo(12);
    }

    @Test
    public void testParseChunkRange_null() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunkRange(null);
        });
    }

    @Test
    public void testParseChunkRange_invalidNumber() {
        assertThrows(NumberFormatException.class, () -> {
            specParser.parseChunkRange("2-A");
        });
    }

    @Test
    public void testParseChunkRange_empty() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunkRange("");
        });
    }

    @Test
    public void testParseChunkRange_invalidRange1() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunkRange("-");
        });
    }

    @Test
    public void testParseChunkRange_invalidRange2() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunkRange("2-");
        });
    }

    @Test
    public void testParseChunk_chunkDefinitionEmpty() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunk("", "[A]BC", Arrays.asList(new MutableToken[] {}));
        });
    }

    @Test
    public void testParseChunk_delimitedTokenTextEmpty() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunk("COUNTY(0-6)", "", Arrays.asList(new MutableToken[] {}));
        });
    }

    @Test
    public void testParseChunk_chunkDefinitionContainsNotTokens() {
        String chunkDefinition = makeChunkDefinition(ChunkType.COUNTY, 2, 4);
        Chunk chunk = specParser.parseChunk(chunkDefinition, "ABCDEF", Arrays.asList(new MutableToken[] {}));
        assertThat(chunk.getType()).isEqualTo(ChunkType.COUNTY);
        assertThat(chunk.getText()).isEqualTo("CD");
        assertThat(chunk.getTokens().isEmpty()).isTrue();
    }

    @Test
    public void testParseChunk() {
        String chunkDefinition = makeChunkDefinition(ChunkType.COUNTY, 2, 7,
                new TokenTypeOccurrence(TokenType.BOROUGH_NAME, 1));
        Chunk chunk = specParser.parseChunk(chunkDefinition, ", [BRONX]",
                Arrays.asList(new MutableToken[] { new MutableToken("BRONX", 2, 7) }));
        assertThat(chunk.getType()).isEqualTo(ChunkType.COUNTY);
        assertThat(chunk.getText()).isEqualTo("BRONX");
        assertThat(chunk.getTokens().get(0)).isEqualTo(new Token(TokenType.BOROUGH_NAME, "BRONX", 2, 7));
    }

    @Test
    public void testParseChunk_untypedTokenListSizeLessThanSpecifiedTokenTypesLength() {
        assertThrows(TestConfigurationException.class, () -> {
            specParser.parseChunk(
                    makeChunkDefinition(ChunkType.COUNTY, 0, 6, new TokenTypeOccurrence(TokenType.BLOCK, 1)),
                    " [1889] ", Arrays.asList(new MutableToken[] {}));
        });
    }

    @Test
    public void testRemoveBrackets() {
        assertThat(this.specParser.removeBrackets("A[B]C [ ][]D[EF] ")).isEqualTo("ABC  DEF ");
    }

    private String makeChunkDefinition(ChunkType chunkType, int start, int end,
            TokenTypeOccurrence... typeOccurrences) {
        StringBuffer buff = new StringBuffer(String.format("%s(%d-%d", chunkType.toString(), start, end));
        for (int i = 0; i < typeOccurrences.length; i++) {
            if (i == 0) {
                buff.append(":");
            }
            buff.append(typeOccurrences[i].tokenType());
            buff.append("{");
            buff.append(typeOccurrences[i].occurrence());
            buff.append("}");
            if (i < typeOccurrences.length - 1) {
                buff.append(",");
            }
        }
        buff.append(")");
        return buff.toString();
    }

    private void assertDefaultChunkSpec(ChunkSpec actual, String expectedId, String expectedText) {
        assertThat(actual.getChunks().size()).isEqualTo(1);
        assertThat(actual.getId()).isEqualTo(expectedId);
        Chunk chunk = actual.getChunks().get(0);
        assertThat(chunk.tokenCount()).isEqualTo(0);
        assertThat(chunk.getText()).isEqualTo(expectedText);
        assertThat(chunk.getType()).isEqualTo(ChunkType.ORIGINAL_INPUT);
    }

}
