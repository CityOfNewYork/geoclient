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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class MatchBuilder
{
    private ParseContext parseContext;
    private MatchType matchType;
    private Matcher matcher;
    private List<RegexTokenGroup> matchGroups = new ArrayList<>();

    public Match build()
    {
        return new Match(parseContext, matchType, matcher, matchGroups);
    }
    public MatchBuilder add(ParseContext parseContext)
    {
        this.parseContext = parseContext;
        return this;
    }
    public MatchBuilder add(MatchType matchType)
    {
        this.matchType = matchType;
        return this;
    }
    public MatchBuilder add(Matcher matcher)
    {
        this.matcher = matcher;
        return this;
    }
    public MatchBuilder add(Pattern pattern, int group, TokenType tokenType)
    {
        this.matchGroups.add(new RegexTokenGroup(pattern, group, tokenType));
        return this;
    }
}
