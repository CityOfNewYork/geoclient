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
package gov.nyc.doitt.gis.geoclient.parser.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class BlockfaceParser extends AbstractRegexParser
{
    /**
     * Group 1: 'ON'
     * Group 2: on street
     * Group 3: 'BETWEEN|BW|...'
     * Group 4: cross street 1
     * Group 5: 'AND|...'
     * Group 6: cross street 2
     */
    private static final Pattern BLOCKFACE = Pattern.compile("^(?:(ON)\\s+)?(.+)\\s+(BETWEEN|BET|BW|B/W|BTWN|BTWN\\.)\\s+(.+)\\s+(AND|\\&|\\&\\&)\\s+(.+)\\s*$",Pattern.CASE_INSENSITIVE);


    @Override
    public void parse(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = BLOCKFACE.matcher(currentChunk.getText());

        if(!matcher.matches())
        {
            patternNotMatched(parseContext,BLOCKFACE);
            return;
        }
        MatchBuilder builder = new MatchBuilder()
        .add(matcher)
        .add(MatchType.COMPLETE)
        .add(parseContext)
        .add(BLOCKFACE, 1, TokenType.ON)
        .add(BLOCKFACE, 2, TokenType.ON_STREET)
        .add(BLOCKFACE, 3, TokenType.BETWEEN)
        .add(BLOCKFACE, 4, TokenType.CROSS_STREET_ONE)
        .add(BLOCKFACE, 5, TokenType.AND)
        .add(BLOCKFACE, 6, TokenType.CROSS_STREET_TWO);
        handleMatch(builder.build(), ChunkType.BLOCKFACE);
    }

}
