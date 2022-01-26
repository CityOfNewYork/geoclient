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

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class BoroughParser extends AbstractRegexParser
{
    private final Pattern boroughPattern;
    private final Pattern boroughAndNycPattern;
    private final Pattern boroughAndStatePattern;

    public BoroughParser(Set<String> boroughNames)
    {
        super();
        boroughAndNycPattern = buildEndsWithBoroughAndNycPattern(boroughNames);
        boroughAndStatePattern = buildEndsWithBoroughAndStatePattern(boroughNames);
        boroughPattern = buildEndsWithBoroughPattern(boroughNames);
    }

    @Override
    public void parse(ParseContext parseContext)
    {
        boolean found = parseBoroughAndNyc(parseContext);
        if(!found)
        {
            found = parseBoroughAndState(parseContext);
        }
        if(!found)
        {
            parseBorough(parseContext);
        }
    }
    protected Pattern buildEndsWithBoroughAndNycPattern(Set<String> boroughNames)
    {
        String literals = PatternUtils.literalMatchGroup(boroughNames);
        return Pattern.compile(String.format("(?:.*)%s(?:\\s|,)+%s(?:\\s|,)*$",literals, ParserConfig.NY_CITY), Pattern.CASE_INSENSITIVE);
    }

    protected Pattern buildEndsWithBoroughAndStatePattern(Set<String> boroughNames)
    {
        String literals = PatternUtils.literalMatchGroup(boroughNames);
        return Pattern.compile(String.format("(?:.*)%s(?:\\s|,)+%s(?:\\s|,)*$",literals, ParserConfig.STATES), Pattern.CASE_INSENSITIVE);
    }

    protected Pattern buildEndsWithBoroughPattern(Set<String> boroughNames)
    {
        String literals = PatternUtils.literalMatchGroup(boroughNames);
        return Pattern.compile(String.format("(?:.*)%s(?:\\s|,)*$",literals), Pattern.CASE_INSENSITIVE);
    }


    private boolean parseBoroughAndNyc(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = boroughAndNycPattern.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, boroughAndNycPattern);
            return false;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(parseContext)
            .add(MatchType.END_OF_INPUT)
            .add(matcher)
            .add(boroughAndNycPattern, 1, TokenType.BOROUGH_NAME)
            .add(boroughAndNycPattern, 2, TokenType.CITY_NAME);
        handleMatch(builder.build(), ChunkType.COUNTY);
        return true;
    }

    private boolean parseBoroughAndState(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = boroughAndStatePattern.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, boroughAndStatePattern);
            return false;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(parseContext)
            .add(MatchType.END_OF_INPUT)
            .add(matcher)
            .add(boroughAndStatePattern,1, TokenType.BOROUGH_NAME)
            .add(boroughAndStatePattern,2, TokenType.STATE);
        handleMatch(builder.build(), ChunkType.COUNTY);
        return true;
    }

    private boolean parseBorough(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = boroughPattern.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, boroughPattern);
            return false;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(parseContext)
            .add(MatchType.END_OF_INPUT)
            .add(matcher)
            .add(boroughPattern, 1, TokenType.BOROUGH_NAME);
        handleMatch(builder.build(), ChunkType.COUNTY);
        return true;
    }
}
