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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

/**
 * The Class ChunkSpecParser parses textual metadata (typically from a
 * {@code spec.xml} file) to create {@link ChunkSpec} used to automate
 * configuration-driven {@code gov.nyc.doitt.gis.geoclient.parser.Parser} tests.
 */
public class ChunkSpecParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkSpecParser.class);
    private static final Pattern TOKEN_TYPE_OCCURRENCE_PATTERN = Pattern.compile("^(\\w+(?:_\\w+)?)\\{(.*)\\}$");
    private static final Pattern TOKEN_VALUE_PATTERN = Pattern.compile("\\[([^\\]]+)\\]");
    private static final Pattern CHUNK_DEFINITION_BODY_PATTERN = Pattern.compile("^\\w+(?:_\\w+)?\\((.*)\\)$");

    /**
     * Parses the given expectation text to produce a {@link ChunkSpec}.
     *
     * @param id              the spec id
     * @param expectationText the expression text for evaluating actual text
     *                        produced by the Class Under Test
     * @return the chunk spec instance
     * @throws TestConfigurationException if either the id or expectationText
     *                                    parameters are null or empty
     */
    public ChunkSpec parse(String id, String expectationText) throws TestConfigurationException {
        if (StringUtils.isEmpty(id)) {
            throw new TestConfigurationException("Parameter 'id' cannot be null or empty.");
        }
        if (StringUtils.isEmpty(expectationText)) {
            throw new TestConfigurationException("Parameter 'unparsedString' cannot be null or empty.");
        }
        String[] splits = expectationText.split("=");

        if (splits.length == 1) {
            return createDefaultChunkSpec(id, splits[0]);
        }

        if (splits.length != 2) {
            throw new TestConfigurationException(String.format(
                    "Parameter 'unparsedString(\"%s\")' cannot be split into exactly two strings on token '='.",
                    expectationText));
        }
        List<MutableToken> untypedTokens = parseBracketValues(splits[0]);
        ChunkSpec chunkSpec = new ChunkSpec(id, splits[0], parseChunkSpec(splits[1], splits[0], untypedTokens));
        return chunkSpec;
    }

    /**
     * Creates a default {@link ChunkSpec} using a single token.
     *
     * @param id                 the spec id
     * @param delimitedTokenText the token text including delimiters
     * @return the ChunkSpec
     */
    protected ChunkSpec createDefaultChunkSpec(String id, String delimitedTokenText) {
        return new ChunkSpec(id, delimitedTokenText,
                Arrays.asList(new Chunk[] { new Chunk(ChunkType.ORIGINAL_INPUT, removeBrackets(delimitedTokenText)) }));
    }

    /**
     * Removes the leading and trailing bracket delimiters. E.g.,
     * <code>[mytoken]</code> becomes <code>mytoken</code>.
     *
     * @param delimitedTokenText the bracket-delimited token
     * @return the token string
     */
    protected String removeBrackets(String delimitedTokenText) {
        return delimitedTokenText.replaceAll("[\\[\\]]", "");
    }

    /**
     * Parses zero or more bracket-delimited expressions from the given string and
     * returns a {@link List} of zero or more {@link MutableToken}s.
     *
     * @param delimitedString string containing delimited token expressions
     * @return the list of MutableToken instances
     */
    protected List<MutableToken> parseBracketValues(String delimitedString) {
        List<MutableToken> result = new ArrayList<>();
        Matcher matcher = TOKEN_VALUE_PATTERN.matcher(delimitedString);
        int previousBracketOffset = 0;
        while (matcher.find()) {
            String value = matcher.group(1);
            int start = matcher.start(1) - previousBracketOffset - 1; // -1 for opening "[" of this group
            int end = start + value.length();
            result.add(new MutableToken(value, start, end));
            previousBracketOffset = previousBracketOffset + 2;

        }
        return result;
    }

    /**
     * Parse List of Chunks from pipe-delimited chunk definitions .
     *
     * @param delimitedChunkDefinitions example:
     *                                  'COUNTY(0-24:BOROUGH_NAME)|ADDRESS(0-19:HOUSE_NUMBER,STREET_NAME)'
     * @param delimitedTokenText        the delimited token text
     * @param untypedTokens             the untyped tokens
     * @return list of zero or more Chunks
     */
    protected List<Chunk> parseChunkSpec(String delimitedChunkDefinitions, String delimitedTokenText,
            List<MutableToken> untypedTokens) {
        List<Chunk> chunks = new ArrayList<>();
        String[] chunkDefinitions = delimitedChunkDefinitions.split("\\|");
        for (int i = 0; i < chunkDefinitions.length; i++) {
            chunks.add(parseChunk(chunkDefinitions[i], delimitedTokenText, untypedTokens));
        }
        return chunks;
    }

    /**
     * Parse ChunkType from single chunk definition.
     *
     * @param chunkDefinition example:
     *                        'ADDRESS(0-19:HOUSE_NUMBER{1},STREET_NAME{2})'
     * @return the chunk type
     */
    protected ChunkType parseChunkType(String chunkDefinition) {
        if (StringUtils.isEmpty(chunkDefinition)) {
            throw new TestConfigurationException("Parameter 'unparsedChunkSpec' cannot be null or empty.");
        }
        // Example: 'COUNTY(0-24,BOROUGH_NAME{3})'
        String chunkTypeString = chunkDefinition.substring(0, chunkDefinition.indexOf("("));
        ChunkType[] actualTypes = ChunkType.values();
        for (int i = 0; i < actualTypes.length; i++) {
            ChunkType chunkType = actualTypes[i];
            if (chunkType.toString().startsWith(chunkTypeString)) {
                return chunkType;
            }
        }
        throw new TestConfigurationException(String.format("Unknown ChunkType.%s specified.", chunkTypeString));
    }

    /**
     * Given the spec body: '[59] [Maiden Lane]
     * [Manhattan]=COUNTY(0-24:BOROUGH_NAME{3})|ADDRESS(0-19:HOUSE_NUMBER{1},STREET_NAME{2})'
     * Expects the following parameter tokenType format.
     *
     * @param chunkDefinition    example:
     *                           'ADDRESS(0-19:HOUSE_NUMBER{1},STREET_NAME{2})'
     * @param delimitedTokenText example: '[59] [Maiden Lane] [Manhattan]'
     * @param untypedTokens      the untyped tokens
     * @return the chunk
     */
    protected Chunk parseChunk(String chunkDefinition, String delimitedTokenText, List<MutableToken> untypedTokens) {
        if (StringUtils.isEmpty(chunkDefinition)) {
            throw new TestConfigurationException("Parameter 'unparsedChunkSpec' cannot be null or empty.");
        }
        if (StringUtils.isEmpty(delimitedTokenText)) {
            throw new TestConfigurationException("Parameter 'delimitedTokenText' cannot be null or empty.");
        }
        Matcher matcher = CHUNK_DEFINITION_BODY_PATTERN.matcher(chunkDefinition);
        if (!matcher.matches()) {
            throw new TestConfigurationException(
                    String.format("Could not parse chunk definition from input '%s' using pattern '%s'",
                            chunkDefinition, CHUNK_DEFINITION_BODY_PATTERN.pattern()));
        }
        String chunkDefinitionBody = matcher.group(1);
        if (chunkDefinitionBody == null) {
            throw new TestConfigurationException(
                    String.format("Did not find chunk definition body in input '%s'", chunkDefinition));
        }
        // Must exist: splits[0] == '0-24'
        // Might exist: splits[1] == 'HOUSE_NUMBER,STREET_NAME'
        String[] splits = chunkDefinitionBody.split(":");
        int splitsLength = splits.length;
        if (splitsLength < 1 || splitsLength > 2) {
            throw new TestConfigurationException(
                    String.format("Expected format '2-12:TYPE1{2},TYPE2{1}' for chunk definition body but found '%s'.",
                            chunkDefinitionBody));
        }
        LOGGER.debug("Parsing Chunk definition '{}'", chunkDefinition);
        ChunkType chunkType = parseChunkType(chunkDefinition);
        String plainText = removeBrackets(delimitedTokenText);
        Range chunkRange = parseChunkRange(splits[0]);
        Chunk chunk = new Chunk(chunkType, chunkRange.substring(plainText));
        if (splitsLength == 1) {
            // Chunk definition does not contain tokens
            return chunk;
        }
        String[] tokenTypeNameAndPositions = splits[1].split(",");
        for (int i = 0; i < tokenTypeNameAndPositions.length; i++) {
            TokenTypeOccurrence typeOccurrence = parseTokenTypeOccurrence(tokenTypeNameAndPositions[i]);
            if (typeOccurrence.occurrence() > untypedTokens.size()) {
                throw new TestConfigurationException(
                        String.format("TokenType specified for value %d but there are only %d Token values.",
                                typeOccurrence.occurrence(), untypedTokens.size()));
            }
            MutableToken untypedToken = untypedTokens.get(typeOccurrence.zeroIndexOccurrence());
            untypedToken.setTokenType(typeOccurrence.tokenType());
            chunk.add(untypedToken.toToken());
        }
        return chunk;
    }

    /**
     * Parses the token type occurrence.
     *
     * @param tokenTypeNameAndPosition the token type name and position
     * @return the token type occurrence
     */
    protected TokenTypeOccurrence parseTokenTypeOccurrence(String tokenTypeNameAndPosition) {
        Matcher matcher = TOKEN_TYPE_OCCURRENCE_PATTERN.matcher(tokenTypeNameAndPosition);
        if (!matcher.matches()) {
            throw new TestConfigurationException(
                    String.format("Could not parse TokenTypeOccurrence from input '%s'", tokenTypeNameAndPosition));
        }
        return new TokenTypeOccurrence(TokenType.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
    }

    /**
     * Parses the chunk range.
     *
     * @param string the string
     * @return the chunk spec parser. range
     */
    protected ChunkSpecParser.Range parseChunkRange(String string) {
        if (string == null) {
            throw new TestConfigurationException("Chunk range string cannot be null or empty");
        }
        String[] splits = string.split("-");
        if (splits.length != 2) {
            throw new TestConfigurationException(
                    String.format("Expected format '2-12' for chunk range but was '%s'.", string));
        }
        return new Range(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]));
    }

    /**
     * The Class MutableToken.
     */
    public static class MutableToken {
        private TokenType tokenType;
        private String value;
        private int start;
        private int end;

        /**
         * Instantiates a new mutable token.
         *
         * @param value the value
         * @param start the start
         * @param end   the end
         */
        public MutableToken(String value, int start, int end) {
            super();
            this.value = value;
            this.start = start;
            this.end = end;
        }

        /**
         * Gets the token type.
         *
         * @return the token type
         */
        public TokenType getTokenType() {
            return tokenType;
        }

        /**
         * Sets the token type.
         *
         * @param tokenType the new token type
         */
        public void setTokenType(TokenType tokenType) {
            this.tokenType = tokenType;
        }

        /**
         * Gets the value.
         *
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value.
         *
         * @param value the new value
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the start.
         *
         * @return the start
         */
        public int getStart() {
            return start;
        }

        /**
         * Sets the start.
         *
         * @param start the new start
         */
        public void setStart(int start) {
            this.start = start;
        }

        /**
         * Gets the end.
         *
         * @return the end
         */
        public int getEnd() {
            return end;
        }

        /**
         * Sets the end.
         *
         * @param end the new end
         */
        public void setEnd(int end) {
            this.end = end;
        }

        /**
         * To token.
         *
         * @return the token
         */
        public Token toToken() {
            return new Token(tokenType, value, start, end);
        }

    }

    /**
     * The Class Range.
     */
    public static class Range {
        private final int start;
        private final int end;

        /**
         * Instantiates a new range.
         *
         * @param start the start
         * @param end   the end
         */
        public Range(int start, int end) {
            super();
            this.start = start;
            this.end = end;
        }

        /**
         * Gets the start.
         *
         * @return the start
         */
        public int getStart() {
            return start;
        }

        /**
         * Gets the end.
         *
         * @return the end
         */
        public int getEnd() {
            return end;
        }

        /**
         * Substring.
         *
         * @param string the string
         * @return the string
         */
        public String substring(String string) {
            if (string.length() < start || string.length() < end) {
                throw new TestConfigurationException(String.format(
                        "substring(%d,%d) specifies invalid index(s) for \"%s\" which is only %d characters long",
                        start, end, string, string.length()));
            }
            return string.substring(start, end);
        }
    }

    /**
     * The Class TokenTypeOccurrence.
     */
    public static class TokenTypeOccurrence {
        private final TokenType tokenType;
        private final int occurrence;

        /**
         * Instantiates a new token type occurrence.
         *
         * @param tokenType  the token type
         * @param occurrence the occurrence
         */
        public TokenTypeOccurrence(TokenType tokenType, int occurrence) {
            super();
            this.tokenType = tokenType;
            this.occurrence = occurrence;
        }

        /**
         * Token type.
         *
         * @return the token type
         */
        public TokenType tokenType() {
            return tokenType;
        }

        /**
         * Occurrence.
         *
         * @return the int
         */
        public int occurrence() {
            return occurrence;
        }

        /**
         * Zero index occurrence.
         *
         * @return the int
         */
        public int zeroIndexOccurrence() {
            return occurrence - 1;
        }

    }
}
