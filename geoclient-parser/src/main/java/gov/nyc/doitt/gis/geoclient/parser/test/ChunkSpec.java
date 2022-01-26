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
package gov.nyc.doitt.gis.geoclient.parser.test;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;

public class ChunkSpec
{
    private final String id;
    private final String delimitedTokenValues;
    private final List<Chunk> chunks;
    public ChunkSpec(String id,String delimitedTokenValues, List<Chunk> chunks)
    {
        super();
        this.id = id;
        this.delimitedTokenValues = delimitedTokenValues;
        this.chunks = chunks;
    }

    public Input input()
    {
        return new Input(this.id, plainText());
    }

    public String plainText()
    {
        return this.delimitedTokenValues.replaceAll("[\\[\\]]", "");
    }
    public String delimitedText()
    {
        return this.delimitedTokenValues;
    }
    public String getId()
    {
        return id;
    }
    public List<Chunk> getChunks()
    {
        return chunks;
    }

    public int chunkCount()
    {
        return this.chunks == null ? 0 : this.chunks.size();
    }

    public Chunk firstChunk()
    {
        if(this.chunkCount() == 0)
        {
            throw new IllegalStateException("There are zero Chunks.");
        }
        return this.chunks.get(0);
    }

    public Chunk secondChunk()
    {
        if(this.chunkCount() < 2)
        {
            throw new IllegalStateException("There are less than two Chunks.");
        }
        return this.chunks.get(1);
    }

    public boolean matchesStateOf(ParseContext parseContext)
    {
        return this.chunks.equals(parseContext.getChunks());
    }

    public String chunkTypeNames()
    {
        StringBuffer buff = new StringBuffer();
        for (Chunk chunk : this.chunks)
        {
            buff.append(chunk.getType());
            buff.append(",");
        }
        String chunkTypes = buff.substring(0, buff.length()-1);
        return chunkTypes;
    }

}
