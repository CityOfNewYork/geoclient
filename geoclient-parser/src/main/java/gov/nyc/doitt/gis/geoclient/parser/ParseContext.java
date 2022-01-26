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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;

/**
 * This class is not thread-safe.
 *
 * @author mlipper
 */
public class ParseContext
{
    private final Input input;
    private final List<Chunk> chunks;
    private final AtomicInteger currentChunkIndex;
    private boolean parsed;

    public ParseContext(Input input)
    {
        super();
        this.input = input;
        this.chunks = new ArrayList<Chunk>();
        this.chunks.add(new Chunk(ChunkType.ORIGINAL_INPUT, this.input.getValue()));
        this.currentChunkIndex = new AtomicInteger(0);
    }

    public String currentChunkText()
    {
        return getCurrent().getText();
    }

    public boolean isParsed()
    {
        return parsed;
    }

    public void setParsed(boolean parsed)
    {
        this.parsed = parsed;
    }

    public void setCurrent(Chunk chunk)
    {
        this.chunks.add(chunk);
        this.currentChunkIndex.incrementAndGet();
    }

    public Chunk getCurrent()
    {
        return this.chunks.get(this.currentChunkIndex.intValue());
    }

    public void add(Chunk chunk)
    {
        this.chunks.add(chunk);
    }

    public List<Chunk> getChunks()
    {
        return chunks;
    }

    public List<Token> getTokens()
    {
        List<Token> tokens = new ArrayList<>();
        for (Chunk chunk : this.chunks)
        {
            tokens.addAll(chunk.getTokens());
        }
        return tokens;
    }
}
