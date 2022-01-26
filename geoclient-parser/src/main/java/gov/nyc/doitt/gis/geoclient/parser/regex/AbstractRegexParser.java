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
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.Parser;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public abstract class AbstractRegexParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRegexParser.class);

    @Override
    public abstract void parse(ParseContext parseContext);

    protected void patternNotMatched(ParseContext parseContext, Pattern pattern) {
        LOGGER.debug("{} could not match tokens in chunk '{}' using pattern {}", getName(),
                parseContext.currentChunkText(), pattern);
    }

    protected Token newToken(TokenType type, MatchResult matchResult, int groupNumber) {
        Token token = new Token(type, matchResult.group(groupNumber), matchResult.start(groupNumber),
                matchResult.end(groupNumber));
        StringBuffer buff = new StringBuffer(matchResult.group());
        buff.insert(token.start(), '[');
        buff.insert(token.end() + 1, ']');
        LOGGER.debug("{} recognized '{}' as a '{}' token", getName(), buff.toString(), token.getType());
        return token;
    }

    protected void nextChunk(ParseContext parseContext, int newStart, int newEnd) {
        Chunk currentChunk = parseContext.getCurrent();
        LOGGER.debug("Creating subChunk '{}'", currentChunk.highlight(newStart, newEnd));
        Chunk subChunk = currentChunk.subChunk(newStart, newEnd);
        if (!subChunk.containsText()) {
            // All of the original input String has been matched for Tokens
            parseComplete(parseContext);
            return;
        }
        parseContext.setCurrent(subChunk);
    }

    protected void handleMatch(Match match, ChunkType chunkType) {
        Chunk currentChunk = match.currentChunk();
        currentChunk.setType(chunkType);
        List<RegexTokenGroup> matchGroups = match.matchGroups();
        MatchResult matchResult = match.matchResult();
        for (RegexTokenGroup matchGroup : matchGroups) {
            int groupNumber = matchGroup.getGroup();
            if (matchResult.group(groupNumber) != null) {
                currentChunk.add(newToken(matchGroup.getTokenType(), matchResult, matchGroup.getGroup()));
                LOGGER.debug("{} found tokens in chunk '{}' using pattern {}", getName(), currentChunk.getText(),
                        matchGroup.getPattern());
                // patternMatched(match.parseContext(), matchGroup.getPattern());
            }
        }
        if (MatchType.END_OF_INPUT.equals(match.getMatchType())) {
            nextChunk(match.parseContext(), 0, match.firstMatchingGroupStart());
        }
        if (MatchType.COMPLETE.equals(match.getMatchType())) {
            parseComplete(match.parseContext());
        }
    }

    private void parseComplete(ParseContext parseContext) {
        LOGGER.debug("Parse complete.");
        parseContext.setParsed(true);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

}
