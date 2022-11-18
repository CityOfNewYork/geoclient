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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mlipper
 *
 */
public class WorkArea
{
    private static final Logger log = LoggerFactory.getLogger(WorkArea.class);

    private final String id;
    private final SortedSet<Field> fields;
    private final int length;
    private final List<Filter> outputFilters;

    public WorkArea(String id, SortedSet<Field> fields)
    {
        this(id,fields, Collections.<Filter> emptyList());
    }

    public WorkArea(String id, SortedSet<Field> fields, List<Filter> outputFilters)
    {
        super();
        this.id = id;
        this.fields = Collections.unmodifiableSortedSet(fields);
        this.length = computeLength();
        this.outputFilters = outputFilters;
    }

    /**
     * Initializes a ByteBuffer and sets value of matching fields for use as
     * input to a function call (Work Area One).
     * @param parameters Work Area One function arguments
     * @return Initialized ByteBuffer with values in the specified field positions.
     */
    public ByteBuffer createBuffer(Map<String, Object> parameters)
    {
        ByteBuffer buffer = createByteBuffer();
        for (Field field : fields)
        {
            log.trace("Before write ByteBuffer.position={}", buffer.position());
            field.write(resolveInputValue(parameters, field), buffer);
            log.trace("After write ByteBuffer.position={}", buffer.position());
        }
        buffer.flip();
        return buffer;
    }

    /**
     * Initializes an empty ByteBuffer sized for the fields in this WorkArea.
     * Used to create a properly sized Work Area Two buffer.
     * @return initialized and empty ByteBuffer
     */
    public ByteBuffer createBuffer()
    {
        ByteBuffer buffer = createByteBuffer();
        for (Field field : fields)
        {
            field.write(buffer);
        }
        buffer.flip();
        return buffer;
    }

    public Map<String, Object> parseResults(ByteBuffer buffer)
    {
        Map<String, Object> results = new TreeMap<String, Object>();
        for (Field field : fields)
        {
            String fieldValue = field.read(buffer);
            if (!fieldValue.isEmpty() && !isFiltered(field))
            {
                results.put(field.getId(), fieldValue);
                if(field.isOutputAliased())
                {
                    results.put(field.getOutputAlias(), fieldValue);
                }
            }
        }
        return results;
    }

    public List<String> getFieldIds()
    {
        return this.getFieldIds(null, false, true);
    }

    public List<String> getFieldIds(Comparator<Field> comparator, boolean includeFiltered, boolean includeInputFields)
    {

        return this.getFields(comparator, includeFiltered, includeInputFields).stream().map(Field::getId).collect(Collectors.toList());
    }

    public int length()
    {
        return this.length;
    }

    public Field findField(String id)
    {
        for (Field field : fields)
        {
            if (field.getId().equals(id))
            {
                return field;
            }
        }
        return null;
    }

    public String getId()
    {
        return id;
    }

    public boolean isFiltered(Field field)
    {
        for (Filter filter : this.outputFilters)
        {
            boolean matches = filter.matches(field);
            if(matches)
            {
                log.debug("Filtered output of {} because it matches {}", field, filter);
                return true;
            }
        }
        return false;
    }

    public List<Field> getFields(Comparator<Field> comparator, boolean includeFiltered, boolean includeInputFields)
    {
        List<Field> result = new ArrayList<Field>(this.fields.size());
        SortedSet<Field> sorted = this.fields;
        if(comparator!=null)
        {
            sorted = new TreeSet<Field>(comparator);
            sorted.addAll(fields);
        }

        for (Field field : sorted)
        {
            if(!includeInputFields && field.isInput())
            {
                // Don't include inputs has been specified
                // and this field is an input field
                continue;
            }

            if(!includeFiltered && isFiltered(field))
            {
                // Don't include filtered fields has been specified
                // and this field matches a filter
                continue;
            }

            result.add(field);
        }
        return result;
    }

    protected Object resolveInputValue(Map<String, Object> parameters, Field field)
    {
        if(!field.isInput())
        {
            return null;
        }

        Object value = null;

        if(field.isAliased() && parameters.containsKey(field.getAlias()))
        {
            // Check for alias first
            value = parameters.get(field.getAlias());
            log.debug("{} resolved input parameter value {} with alias", field, value);
        }else if(parameters.containsKey(field.getId()))
        {
            value = parameters.get(field.getId());
            log.debug("{} resolved input parameter value {} with id", field, value);
        }

        return value;
    }

    private int computeLength()
    {
        int totalLength = 0;
        for (Field field : fields)
        {
            if(field.isComposite())
            {
                log.debug("Skipping length {} from field {} because it is a composite field", field.getLength(), field.getId());
                continue;
            }
            log.debug("Adding length {} from field {}", field.getLength(), field.getId());
            totalLength += field.getLength();
        }
        log.debug("{} computed total length of {} bytes using {} fields", this.id, totalLength, fields.size());
        return totalLength;
    }

    private ByteBuffer createByteBuffer()
    {
        log.debug("Allocating ByteBuffer for {}", this);
        return ByteBuffer.allocate(length());
    }

    @Override
    public String toString()
    {
        return "WorkArea [id=" + id + ", fields=" + fields.size() + ", length=" + length + "]";
    }


}
