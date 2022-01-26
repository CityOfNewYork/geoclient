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
package gov.nyc.doitt.gis.geoclient.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

public class WorkAreaConfig
{
    private static final Logger log = LoggerFactory.getLogger(WorkAreaConfig.class);
    private String id;
    private int length;
    private boolean isWorkAreaOne;
    private List<Field> fields;
    private List<Filter> outputFilters;

    public WorkAreaConfig()
    {
        super();
    }

    public WorkAreaConfig(String id, int length, boolean isWorkAreaOne, List<Field> fields, List<Filter> outputFilters)
    {
        super();
        this.id = id;
        this.length = length;
        this.isWorkAreaOne = isWorkAreaOne;
        this.fields = fields;
        this.outputFilters = outputFilters;
    }

    public WorkArea createWorkArea()
    {
        if(Registry.containsWorkArea(this.id))
        {
            return Registry.getWorkArea(this.id);
        }
        SortedSet<Field> uniqueSet = new TreeSet<Field>();
        validate(this.fields, uniqueSet);
        List<Filter> oFilters = this.outputFilters != null ? this.outputFilters : Collections.<Filter>emptyList();
        WorkArea workArea = new WorkArea(this.id, uniqueSet, oFilters);
        validate(workArea);
        Registry.addWorkArea(workArea);
        return workArea;
    }

    public String getId()
    {
        return id;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public boolean isWorkAreaOne()
    {
        return isWorkAreaOne;
    }

    public void setWorkAreaOne(boolean isWorkAreaOne)
    {
        this.isWorkAreaOne = isWorkAreaOne;
    }

    public List<Field> getFields()
    {
        return fields;
    }

    public void setFields(List<Field> fields)
    {
        this.fields = fields;
    }

    public List<Filter> getOutputFilters()
    {
        return outputFilters;
    }

    public void setOutputFilters(List<Filter> outputFilters)
    {
        this.outputFilters = outputFilters;
    }

    @Override
    public String toString()
    {
        return "WorkAreaConfig [id=" + this.id + ", length=" + length + ", isWorkAreaOne=" + isWorkAreaOne + "]";
    }

    protected List<Field> validate(List<Field> configuredFields, SortedSet<Field> uniqueSet)
    {
        List<Field> duplicates = new ArrayList<Field>();
        for (Field field : configuredFields)
        {
            if(!uniqueSet.add(field))
            {
                Field fieldAlreadyInTheSet = findDuplicate(field, uniqueSet);
                log.debug("Field [id={}] has a duplicate start and length of Field [id={}] and will NOT be added to WorkArea[id={}]", field.getId(), fieldAlreadyInTheSet.getId(), this.id);
                duplicates.add(field);
            }
        }
        return duplicates;
    }

    private Field findDuplicate(Field field, SortedSet<Field> uniqueSet)
    {
        for (Field possibleMatch : uniqueSet)
        {
            if(field.compareTo(possibleMatch)==0 || field.equals(possibleMatch))
            {
                return possibleMatch;
            }
        }
        return null;
    }

    private void validate(WorkArea workArea)
    {
        if (workArea.length() != this.length)
        {
            InvalidWorkAreaLengthException e = new InvalidWorkAreaLengthException(workArea, this.length);
            log.error(e.getMessage());
            throw e;
        }
    }

}
