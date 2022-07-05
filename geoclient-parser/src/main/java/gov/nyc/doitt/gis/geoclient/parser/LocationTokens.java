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
package gov.nyc.doitt.gis.geoclient.parser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class LocationTokens {
    private final Input input;
    private final List<Chunk> chunks;

    public LocationTokens(Input input, List<Chunk> chunks) {
        super();
        this.input = input;
        this.chunks = chunks;
    }

    public List<Token> tokensOfType(TokenType... tokenTypes) {
        List<Token> tokensOfType = new ArrayList<>();
        for (Token token : getTokens()) {
            for (int i = 0; i < tokenTypes.length; i++) {
                if (tokenTypes[i].equals(token.getType())) {
                    tokensOfType.add(token);
                }
            }
        }
        return tokensOfType;
    }

    public Token firstTokenOfType(TokenType tokenType) {
        List<Token> tokens = tokensOfType(tokenType);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    public Input getInput() {
        return input;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public List<Token> getTokens() {
        List<Token> tokens = new ArrayList<>();
        for (Chunk chunk : chunks) {
            tokens.addAll(chunk.getTokens());
        }
        return tokens;
    }

    public String parseSummary() {
        Deque<String> stack = new ArrayDeque<>();
        for (Chunk chunk : chunks) {
            StringBuffer chunkBuffer = new StringBuffer();
            chunkBuffer.append(chunk.getType());
            chunkBuffer.append("{ ");
            for (Iterator<Token> iter = chunk.getTokens().iterator(); iter.hasNext();) {
                Token token = iter.next();
                chunkBuffer.append(String.format("%s[%s]", token.getType(), token.getValue()));
                if (iter.hasNext()) {
                    chunkBuffer.append(", ");
                }
            }
            chunkBuffer.append(" }");
            stack.push(chunkBuffer.toString());
        }
        StringBuffer buff = new StringBuffer();
        for (Iterator<String> iter = stack.iterator(); iter.hasNext();) {
            buff.append(iter.next());
            if (iter.hasNext()) {
                buff.append(", ");
            }
        }
        return String.format("%s", buff.toString());
    }

    @Override
    public String toString() {
        return "LocationTokens [" + parseSummary() + "]";
    }

}
