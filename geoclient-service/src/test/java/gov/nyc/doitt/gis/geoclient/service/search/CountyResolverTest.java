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
package gov.nyc.doitt.gis.geoclient.service.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class CountyResolverTest
{
    @SuppressWarnings("serial")
    private Map<String, String> boroughNames = new HashMap<String, String>()
    {{
        put("Bk","BROOKLYN");
    }};

    private CountyResolver boroughResolver;
    private LocationTokens locationTokens;
    private Chunk chunk;
    private Token resolvableBoroughNameToken;
    private Token unresolvableBoroughNameToken;

    @BeforeEach
    public void setUp() throws Exception
    {
        boroughResolver = new CountyResolver(boroughNames);
        chunk = new Chunk(ChunkType.COUNTY, "countyChunk");
        List<Chunk> chunks = new ArrayList<>();
        chunks.add(chunk);
        locationTokens = new LocationTokens(new Input("abc","def"), chunks);
        resolvableBoroughNameToken = new Token(TokenType.BOROUGH_NAME, "BK", 4, 6);
        unresolvableBoroughNameToken = new Token(TokenType.BOROUGH_NAME, "foo", 0, 3);
    }

    @Test
    public void testResolve_emptyLocationTokensContainsFiveBoroughs()
    {
        ValueResolution res = boroughResolver.resolve(locationTokens);
        assertThat(res).isNotNull();
        assertThat(res.unresolvedCount()).isEqualTo(0);
        assertThat(res.resolvedCount()).isEqualTo(5);
        assertThat(res.totalCount()).isEqualTo(5);
        assertTrue(res.resolved().contains(CountyResolver.MANHATTAN));
        assertTrue(res.resolved().contains(CountyResolver.BRONX));
        assertTrue(res.resolved().contains(CountyResolver.BROOKLYN));
        assertTrue(res.resolved().contains(CountyResolver.QUEENS));
        assertTrue(res.resolved().contains(CountyResolver.STATEN_ISLAND));
    }

    @Test
    public void testResolve_doesNotCreateDefaultBoroughsWhenZipIsAvailable()
    {
        Chunk zipChunk = new Chunk(ChunkType.COUNTY, "10025");
        zipChunk.add(new Token(TokenType.ZIP, "10025", 0, 5));
        locationTokens.getChunks().add(zipChunk);
        ValueResolution res = boroughResolver.resolve(locationTokens);
        assertThat(res).isNotNull();
        assertThat(res.unresolvedCount()).isEqualTo(0);
        assertThat(res.resolvedCount()).isEqualTo(1);
        assertThat(res.totalCount()).isEqualTo(1);
    }

    @Test
    public void testResolve_containsCorrectResolutionCount()
    {
        chunk.add(resolvableBoroughNameToken);
        chunk.add(unresolvableBoroughNameToken);
        ValueResolution res = boroughResolver.resolve(locationTokens);
        assertThat(res).isNotNull();
        assertThat(res.unresolvedCount()).isEqualTo(1);
        assertThat(res.resolvedCount()).isEqualTo(1);
        assertThat(res.totalCount()).isEqualTo(2);
    }

}
