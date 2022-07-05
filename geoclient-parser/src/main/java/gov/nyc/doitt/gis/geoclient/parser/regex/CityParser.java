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

public class CityParser extends AbstractRegexParser
{
    private final Pattern cityPattern;
    private final Pattern cityAndNycPattern;
    private final Pattern cityAndStatePattern;

    public CityParser(Set<String> cityNames)
    {
        super();
        this.cityPattern = buildEndsWithCityPattern(cityNames);
        this.cityAndNycPattern = buildEndsWithCityAndNycPattern(cityNames);
        this.cityAndStatePattern = buildEndsWithCityAndStatePattern(cityNames);
    }

    @Override
    public void parse(ParseContext parseContext)
    {
        boolean found = parseCityAndNyc(parseContext);
        if(!found){
            found = parseCityAndState(parseContext);
        }
        if(!found)
        {
            found = parseCity(parseContext);
        }
    }

    protected Pattern buildEndsWithCityAndNycPattern(Set<String> cityNames)
    {
        String literals = PatternUtils.literalMatchGroup(cityNames);
        return Pattern.compile(String.format("(?:.*)%s(?:\\s|,)+%s(?:\\s|,)*$",literals, ParserConfig.NY_CITY), Pattern.CASE_INSENSITIVE);
    }

    protected Pattern buildEndsWithCityAndStatePattern(Set<String> cityNames)
    {
        String literals = PatternUtils.literalMatchGroup(cityNames);
        return Pattern.compile(String.format("(?:.*)%s(?:\\s|,)+%s(?:\\s|,)*$",literals, ParserConfig.STATES), Pattern.CASE_INSENSITIVE);
    }

    protected Pattern buildEndsWithCityPattern(Set<String> cityNames)
    {
        String literals = PatternUtils.literalMatchGroup(cityNames);
        return Pattern.compile(String.format("(?:.*)%s(?:\\s|,)*$",literals), Pattern.CASE_INSENSITIVE);
    }

    private boolean parseCityAndNyc(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = cityAndNycPattern.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, cityAndNycPattern);
            return false;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(parseContext)
            .add(MatchType.END_OF_INPUT)
            .add(matcher)
            .add(cityAndNycPattern, 1, TokenType.CITY_NAME)
            .add(cityAndNycPattern, 2, TokenType.CITY_NAME);
        handleMatch(builder.build(), ChunkType.COUNTY);
        return true;
    }

    private boolean parseCityAndState(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = cityAndStatePattern.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, cityAndStatePattern);
            return false;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(parseContext)
            .add(MatchType.END_OF_INPUT)
            .add(matcher)
            .add(cityAndStatePattern,1, TokenType.CITY_NAME)
            .add(cityAndStatePattern,2, TokenType.STATE);
        handleMatch(builder.build(), ChunkType.COUNTY);
        return true;
    }

    private boolean parseCity(ParseContext parseContext)
    {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = cityPattern.matcher(currentChunk.getText());
        if(!matcher.matches())
        {
            patternNotMatched(parseContext, cityPattern);
            return false;
        }
        MatchBuilder builder = new MatchBuilder()
            .add(parseContext)
            .add(MatchType.END_OF_INPUT)
            .add(matcher)
            .add(cityPattern, 1, TokenType.CITY_NAME);
        handleMatch(builder.build(), ChunkType.COUNTY);
        return true;
    }

}
