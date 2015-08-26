/*
 * Copyright 2013-2016 the original author or authors.
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



public class BaseDocumentation implements Comparable<BaseDocumentation>
{
	private String id;
	private Description description;
	
	public BaseDocumentation()
	{
		super();
	}
	
	public BaseDocumentation(String id, Description description)
	{
		super();
		this.id = id;
		this.description = description;
	}

	public boolean isDocumented() 
	{
		return this.id != null;
	}
	
	public boolean hasText()
	{
		return this.description != null && this.description.hasText();
	}
	
	public String getId()
	{
		return id;
	}

	public Description getDescription()
	{
		return description;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setDescription(Description description)
	{
		this.description = description;
	}

	public String summarize(int length)
	{
		if(this.description != null)
		{
			return this.description.summarize(length);
		}
		return "";
	}

	public String joinDescriptionText(String between)
	{
		if(this.description != null)
		{
			return this.description.joinText(between);
		}
		return "";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		String myId = getId();
		result = prime * result + ((myId == null) ? 0 : myId.hashCode());
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
		BaseDocumentation other = (BaseDocumentation) obj;
		// Self-encapsulate since some subclasses implement this differently
		String myId = getId();
		String otherId = other.getId();
		if (myId == null)
		{
			if (otherId != null)
			{
				return false;
			}
		} else if (!myId.equals(otherId))
		{
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(BaseDocumentation o)
	{
		return this.id.compareTo(o.id);
	}
	
}