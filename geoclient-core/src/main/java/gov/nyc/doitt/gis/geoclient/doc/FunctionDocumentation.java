/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.doc;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class FunctionDocumentation extends BaseDocumentation
{
    private String displayName;
    private SortedSet<ItemDocumentation> fields;
    private List<GroupDocumentation> groups;

    public FunctionDocumentation()
    {
        super();
    }

    public boolean add(ItemDocumentation itemDocumentation)
    {
        initFields();
        if (!isGroupMember(itemDocumentation))
        {
            this.fields.add(itemDocumentation);
            return true;
        }
        return false;
    }

    public boolean isGroupMember(ItemDocumentation itemDocumentation)
    {
        if (this.groups != null)
        {
            for (GroupDocumentation group : this.groups)
            {
                if (group.isMember(itemDocumentation))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public SortedSet<ItemDocumentation> getFields()
    {
        return fields;
    }

    public boolean hasDisplayName()
    {
        return this.displayName != null;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public void setFields(SortedSet<ItemDocumentation> fields)
    {
        if (fields != null)
        {
            initFields();
            for (ItemDocumentation itemDocumentation : fields)
            {
                add(itemDocumentation);
            }
        }
    }

    public List<GroupDocumentation> getGroups()
    {
        return groups;
    }

    public void setGroups(List<GroupDocumentation> groups)
    {
        this.groups = groups;
    }

    private void initFields()
    {
        if (this.fields == null)
        {
            this.fields = new TreeSet<ItemDocumentation>(new DisplayNameComparator());
        }

    }
    
    private static class DisplayNameComparator implements Comparator<ItemDocumentation>
    {

        @Override
        public int compare(ItemDocumentation o1, ItemDocumentation o2)
        {
            return o1.getDisplayName().compareTo(o2.getDisplayName());
        }
        
    }

    @Override
    public String toString()
    {
        return "FunctionDocumentation [id=" + getId() + "]";
    }
    
}
