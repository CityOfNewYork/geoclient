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
package gov.nyc.doitt.gis.geoclient.parser.token;

import java.util.ArrayList;
import java.util.List;

public class Chunk
{
    private final String text;
    private ChunkType chunkType;
    private List<Token> tokens;

    public Chunk(ChunkType chunkType, String text)
    {
        super();
        this.chunkType = chunkType;
        this.text = text;
        this.tokens = new ArrayList<>();
    }

    public boolean containsText()
    {
        return this.text != null && this.text.length() > 0;
    }

    public int tokenCount()
    {
        return tokens.size();
    }

    public boolean contains(Token o)
    {
        return tokens.contains(o);
    }

    public Chunk subChunk(int start, int end)
    {
        return new Chunk(ChunkType.SUBSTRING, TextUtils.sanitize(this.text.substring(start, end)));
    }

    public String highlight(int start, int end)
    {
        StringBuffer buff = new StringBuffer(this.text);
        buff.insert(start, "|");
        buff.insert(end + 1, "|");
        return buff.toString();
    }

    public boolean add(Token e)
    {
        return tokens.add(e);
    }

    public String getText()
    {
        return text;
    }

    public ChunkType getType()
    {
        return chunkType;
    }

    public void setType(ChunkType chunkType)
    {
        this.chunkType = chunkType;
    }

    public List<Token> getTokens()
    {
        return tokens;
    }

    public void setTokens(List<Token> tokens)
    {
        this.tokens = tokens;
    }

    public String pictogram()
    {
        StringBuffer buff = new StringBuffer(text);
        int offset = 0;
        for (Token token : tokens)
        {
            buff.insert(token.start() + offset, '[');
            buff.insert(token.end()+1 + offset, ']');
            offset = offset + 2;
        }
        return String.format("%s: '%s'", chunkType, buff);
    }

    @Override
    public String toString()
    {
        return "Chunk [text=" + text + ", chunkType=" + chunkType + ", tokens=" + tokens + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chunkType == null) ? 0 : chunkType.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((tokens == null) ? 0 : tokens.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chunk other = (Chunk) obj;
        if (chunkType != other.chunkType)
            return false;
        if (text == null)
        {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (tokens == null)
        {
            if (other.tokens != null)
                return false;
        } else if (!tokens.equals(other.tokens))
            return false;
        return true;
    }

}
