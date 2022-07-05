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



/**
 * @author mlipper
 *
 */
public class Token
{
    private final String value;
    private final TokenType type;
    private final int start;
    private final int end;

    public Token(TokenType type, String value, int start, int end)
    {
        super();
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public TokenType getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public int start()
    {
        return start;
    }

    public int end()
    {
        return end;
    }

    @Override
    public String toString()
    {
        return "Token [value=" + value + ", type=" + type + ", start=" + start + ", end=" + end + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + end;
        result = prime * result + start;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Token other = (Token) obj;
        if (end != other.end)
            return false;
        if (start != other.start)
            return false;
        if (type != other.type)
            return false;
        if (value == null)
        {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
