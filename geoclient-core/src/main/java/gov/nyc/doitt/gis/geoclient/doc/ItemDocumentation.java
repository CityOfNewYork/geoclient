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

import java.util.List;

/**
 * Documentation entry for {@link DataDictionary} item.
 * 
 * @author mlipper
 * 
 */
public class ItemDocumentation extends BaseDocumentation
{
	private List<String> aliases;
	private String format;
	private List<String> functionNames;
	private List<String> seeAlso;
	private List<Table> tables;
	private String displayName;

	public ItemDocumentation()
	{
		super();
	}

	public ItemDocumentation(String id)
	{
		super(id, new Description());
	}
	
	public ItemDocumentation copyWithDisplayName(String nameToUse)
	{
		if(nameToUse.equals(getId()))
		{
			return this;
		}
		ItemDocumentation copy = new ItemDocumentation(getId());
		copy.setDescription(getDescription());
		copy.displayName = nameToUse;
		copy.aliases = this.aliases;
		copy.tables = this.tables;
		copy.format = this.format;
		copy.seeAlso = this.seeAlso;
		copy.functionNames = this.functionNames;
		return copy;
	}
	
	public boolean documents(String id)
	{
		String myId = getId();
		if (myId != null && myId.equals(id))
		{
			return true;
		}
		if (isAliased())
		{
			for (String alias : this.aliases)
			{
				if (alias.equals(id))
				{
					return true;
				}
			}
		}
		return false;
	}

	public List<String> getAliases()
	{
		return this.aliases;
	}

	public String getDisplayName()
	{
		if(this.displayName == null)
		{
			return getId();
		}
		return this.displayName;
	}
	
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getFormat()
	{
		return this.format;
	}

	public List<String> getFunctionNames()
	{
		return this.functionNames;
	}

	public List<String> getSeeAlso()
	{
		return this.seeAlso;
	}

	public List<Table> getTables()
	{
		return this.tables;
	}

	public void setAliases(List<String> aliases)
	{
		this.aliases = aliases;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public void setFunctionNames(List<String> functionNames)
	{
		this.functionNames = functionNames;
	}

	public void setSeeAlso(List<String> itemDocumentationIds)
	{
		this.seeAlso = itemDocumentationIds;
	}

	public void setTables(List<Table> tables)
	{
		this.tables = tables;
	}

	@Override
	public String toString()
	{
		return "ItemDocumentation [id=" + getId() + "]";
	}

	private boolean isAliased()
	{
		return this.aliases != null && !this.aliases.isEmpty();
	}

}
