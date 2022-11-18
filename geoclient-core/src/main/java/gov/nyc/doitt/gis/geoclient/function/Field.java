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
package gov.nyc.doitt.gis.geoclient.function;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.util.Assert;

public class Field implements Comparable<Field>
{
    public final static Comparator<Field> DEFAULT_SORT = new Field.StartLengthComparator();
    public final static Comparator<Field> NAME_SORT = new Field.NameStartLengthComparator();
    private final static Logger log = LoggerFactory.getLogger(Field.class);
    private final String id;
    private final Integer start;
    private final Integer length;
    private final boolean composite;
    private final boolean input;
    private final String alias;
    private final boolean whitespaceSignificant;
    private final String outputAlias;

    public Field(String id, Integer start, Integer length)
    {
        this(id, start, length, false);
    }

    public Field(String id, Integer start, Integer length, boolean composite)
    {
        this(id, start, length, composite, false, null);
    }

    public Field(String id, Integer start, Integer length, boolean composite, boolean input, String alias)
    {
        this(id, start, length, composite, input, alias, false);
    }

    public Field(String id, Integer start, Integer length, boolean composite, boolean input, String alias,
            boolean whitespace)
    {
        this(id, start, length, composite, input, alias, whitespace, null);
    }

    public Field(String id, Integer start, Integer length, boolean composite, boolean input, String alias,
            boolean whitespace, String outputAlias)
    {
        Assert.notNull(id, "Parameter 'id' cannot be null");
        this.id = id;
        Assert.notNull(start, "Parameter 'start' cannot be null");
        this.start = start;
        Assert.notNull(length, "Parameter 'length' cannot be null");
        this.length = length;
        this.composite = composite;
        this.input = input;
        this.alias = alias;
        this.whitespaceSignificant = whitespace;
        this.outputAlias = outputAlias;
    }

    public void write(ByteBuffer buffer)
    {
        write(null, buffer);
    }

    public void write(Object value, ByteBuffer buffer)
    {
        log.debug("Writing {} with value {}", this, value);
        buffer.position(this.start);
        buffer.put(getBytes(value));
    }

    public String read(ByteBuffer buffer)
    {
        byte[] bytes = new byte[this.length];
        buffer.position(this.start);
        buffer.get(bytes);
        String fieldValue = new String(bytes);
        if (!whitespaceSignificant || fieldValue.trim().isEmpty())
        {
            // Do not preserve whitespace
            fieldValue = fieldValue.trim();
        }
        log.debug("Read {}='{}'", this.id, fieldValue);
        return fieldValue;
    }

    public boolean isComposite()
    {
        return composite;
    }

    public String getAlias()
    {
        return alias;
    }

    public boolean isAliased()
    {
        return this.alias != null;
    }

    public boolean isInput()
    {
        return input;
    }

    public boolean isWhitespaceSignificant()
    {
        return whitespaceSignificant;
    }

    public String getOutputAlias()
    {
        return outputAlias;
    }

    public boolean isOutputAliased()
    {
        return this.outputAlias != null;
    }

    protected byte[] getBytes(Object value)
    {
        // Allocate result array
        byte[] bytes = new byte[this.length];
        // Fill with blanks
        Arrays.fill(bytes, (byte) ' ');
        // Set value or null
        String stringValue = value == null ? null : value.toString();
        if (stringValue != null && !stringValue.equals(""))
        {
            // Value is not null and not the empty string: copy over values to
            // result
            byte[] valueBytes = value.toString().getBytes();
            // Make sure not to copy more bytes than exist in the dest array
            int numberToCopy = valueBytes.length > this.length ? this.length : valueBytes.length;
            // Copy to result
            System.arraycopy(valueBytes, 0, bytes, 0, numberToCopy);
        }
        return bytes;
    }

    /**
     * Default ordering uses start, length.
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Field o)
    {
        return DEFAULT_SORT.compare(this, o);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((length == null) ? 0 : length.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Field other = (Field) obj;
        if (length == null)
        {
            if (other.length != null)
            {
                return false;
            }
        } else if (!length.equals(other.length))
        {
            return false;
        }
        if (start == null)
        {
            if (other.start != null)
            {
                return false;
            }
        } else if (!start.equals(other.start))
        {
            return false;
        }
        return true;
    }

    public Integer getLength()
    {
        return length;
    }

    public String getId()
    {
        return id;
    }

    public Integer getStart()
    {
        return start;
    }

    @Override
    public String toString()
    {
        return "Field [id=" + id + ", start=" + start + ", length=" + length + ", composite=" + composite + ", input="
                + input + ", alias=" + alias + ", whitespaceSignificant=" + whitespaceSignificant + ", outputAlias=" + outputAlias + "]";
    }

    public static class StartLengthComparator implements Comparator<Field>
    {

        @Override
        public int compare(Field o1, Field o2)
        {
            int startComparison = o1.start.compareTo(o2.start);

            if (startComparison != 0)
            {
                return startComparison;
            }

            return o1.length.compareTo(o2.length);
        }
    }

    public static class NameStartLengthComparator implements Comparator<Field>
    {

        @Override
        public int compare(Field o1, Field o2)
        {
            int nameComparison = o1.id.compareTo(o2.id);
            if (nameComparison != 0)
            {
                return nameComparison;
            }
            int startComparison = o1.start.compareTo(o2.start);
            if (startComparison != 0)
            {
                return startComparison;
            }
            return o1.length.compareTo(o2.length);
        }

    }

}
