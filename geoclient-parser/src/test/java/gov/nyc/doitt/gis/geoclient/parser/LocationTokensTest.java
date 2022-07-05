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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class LocationTokensTest
{
    private Input input;
    private List<Chunk> chunks;
    private LocationTokens locationTokens;
    private Token houseNumberToken;
    private Token streetNameToken;
    private Token boroughToken;

    @BeforeEach
    public void setUp()
    {
        input = new Input("1", "22 Broadway, Manhattan");
        chunks = new ArrayList<>();
        Chunk cityBoroughChunk = new Chunk(ChunkType.COUNTY,"22 Broadway, Manhattan");
        boroughToken = new Token(TokenType.BOROUGH_NAME,"Manhattan",13,22);
        cityBoroughChunk.add(boroughToken);
        chunks.add(cityBoroughChunk);
        Chunk addressChunk = new Chunk(ChunkType.ADDRESS, "22 Broadway");
        houseNumberToken = new Token(TokenType.HOUSE_NUMBER,"22",0,2);
        addressChunk.add(houseNumberToken);
        streetNameToken = new Token(TokenType.STREET_NAME,"Broadway",3,11);
        addressChunk.add(streetNameToken);
        chunks.add(addressChunk);
        locationTokens = new LocationTokens(input, chunks);
    }

    @Test
    public void testTokensOfType()
    {
        List<Token> tokenList = locationTokens.tokensOfType(TokenType.AND);
        assertThat(tokenList.size()).isEqualTo(0);
        tokenList = locationTokens.tokensOfType(TokenType.HOUSE_NUMBER);
        assertThat(tokenList.size()).isEqualTo(1);
        assertThat(tokenList).contains(houseNumberToken);
        tokenList = locationTokens.tokensOfType(TokenType.STREET_NAME);
        assertThat(tokenList.size()).isEqualTo(1);
        assertThat(tokenList).contains(streetNameToken);
        tokenList = locationTokens.tokensOfType(TokenType.BOROUGH_NAME);
        assertThat(tokenList.size()).isEqualTo(1);
        assertThat(tokenList).contains(boroughToken);
        tokenList = locationTokens.tokensOfType(TokenType.HOUSE_NUMBER, TokenType.STREET_NAME);
        assertThat(tokenList.size()).isEqualTo(2);
        assertThat(tokenList).contains(houseNumberToken);
        assertThat(tokenList).contains(streetNameToken);
    }

    @Test
    public void testFirstTokenOfType()
    {
        assertThat(locationTokens.firstTokenOfType(TokenType.AND)).isNull();
        assertThat(locationTokens.firstTokenOfType(TokenType.STREET_NAME)).isEqualTo(streetNameToken);
    }

    @Test
    public void testParseSummary()
    {
        assertThat(locationTokens.parseSummary()).isEqualTo("ADDRESS{ HOUSE_NUMBER[22], STREET_NAME[Broadway] }, COUNTY{ BOROUGH_NAME[Manhattan] }");
    }

    @Test
    public void testConstructor()
    {
        assertThat(locationTokens.getInput()).isSameAs(input);
        assertThat(locationTokens.getChunks()).isSameAs(chunks);
    }
}
