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

public class CountryParser extends AbstractRegexParser
{
    private static final Pattern COUNTRY = Pattern.compile("(?:.*)\\b(U\\.?S\\.?A\\.?|U\\.?S\\.?|(?:(?<!UNITED STATES OF )AMERICA)|UNITED STATES OF AMERICA|UNITED STATES)\\s*$",Pattern.CASE_INSENSITIVE);

    @Override
    public void parse(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = COUNTRY.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, COUNTRY);
            return;
        }

        MatchBuilder builder = new MatchBuilder()
            .add(matcher)
            .add(MatchType.END_OF_INPUT)
            .add(parseContext)
            .add(COUNTRY, 1, TokenType.COUNTRY);

        handleMatch(builder.build(), ChunkType.COUNTY);
    }

}
