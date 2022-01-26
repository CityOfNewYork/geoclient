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

public class IntersectionParser extends AbstractRegexParser
{
    private static final Pattern INTERSECTION = Pattern.compile("^\\s*(.+)\\s+(AND|\\&|\\&\\&)\\s+(.+)\\s*$",Pattern.CASE_INSENSITIVE);

    @Override
    public void parse(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = INTERSECTION.matcher(currentChunk.getText());

        if(!matcher.matches())
        {
            patternNotMatched(parseContext,INTERSECTION);
            return;
        }

        MatchBuilder builder = new MatchBuilder()
        .add(matcher)
        .add(MatchType.COMPLETE)
        .add(parseContext)
        .add(INTERSECTION, 1, TokenType.CROSS_STREET_ONE)
        .add(INTERSECTION, 2, TokenType.AND)
        .add(INTERSECTION, 3, TokenType.CROSS_STREET_TWO);

        handleMatch(builder.build(), ChunkType.INTERSECTION);
    }

}
