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

public class AddressParser extends AbstractRegexParser
{
    // See UPG section V. for details on naming and parsing rules
    private static final String HN_BASIC = "(\\d+(?:-\\d+)?)";
    private static final String HN_SUFFIX = "([A-Z]|(?:\\s1/[2-5]|\\sFRONT(?!\\s+ST|AV)|\\sREAR|\\sGARAGE))?";
    private static final String STREET = "\\s*(.*)";
    private static final String ADDRESS = "^" + HN_BASIC +  HN_SUFFIX + STREET + "$";
    private static final Pattern REGEX = Pattern.compile(ADDRESS,Pattern.CASE_INSENSITIVE);

    @Override
    public void parse(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = REGEX.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, REGEX);
            return;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(matcher)
            .add(MatchType.COMPLETE)
            .add(parseContext)
            .add(REGEX, 1, TokenType.HOUSE_NUMBER)
            .add(REGEX, 2, TokenType.HOUSE_NUMBER_SUFFIX)
            .add(REGEX, 3, TokenType.STREET_NAME);

        handleMatch(builder.build(), ChunkType.ADDRESS);
    }


}
