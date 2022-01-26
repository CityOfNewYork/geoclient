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

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.util.Assert;

public class Match
{

    private final ParseContext parseContext;
    private final MatchType matchType;
    private final Matcher matcher;
    private final List<RegexTokenGroup> matchGroups;

    public Match(ParseContext parseContext, MatchType matchType, Matcher matcher, List<RegexTokenGroup> matchGroups)
    {
        super();
        Assert.notNull(parseContext, "ParseContext argument cannot be null.");
        this.parseContext = parseContext;
        Assert.notNull(matchType, "MatchType argument cannot be null.");
        this.matchType = matchType;
        Assert.notNull(matcher, "MatchResult argument cannot be null.");
        this.matcher = matcher;
        Assert.notEmpty(matchGroups, "List<RegexTokenGroup> argument cannot be empty or null.");
        this.matchGroups = matchGroups;
    }

    public boolean matches()
    {
        return this.matcher.matches();
    }

    public MatchType getMatchType()
    {
        return matchType;
    }

    public Chunk currentChunk()
    {
        return this.parseContext.getCurrent();
    }

    public MatchResult matchResult()
    {
        return this.matcher;
    }

    public List<RegexTokenGroup> matchGroups()
    {
        return this.matchGroups;
    }

    public ParseContext parseContext()
    {
        return this.parseContext;
    }

    public int firstMatchingGroupStart()
    {
        int leftMostGroupStart = matcher.group().length();
        for (RegexTokenGroup matchGroup : matchGroups)
        {
            int start = matcher.start(matchGroup.getGroup());
            if(start != -1)
            {
                leftMostGroupStart = start < leftMostGroupStart ? start : leftMostGroupStart;
            }
        }
        return leftMostGroupStart;
    }

}
