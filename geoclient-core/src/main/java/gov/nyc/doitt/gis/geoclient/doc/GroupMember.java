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

public class GroupMember extends ItemDocumentationSupport
{
	private boolean sizeIndicator;

	public boolean matches(String fieldId)
	{
		String myId = getId();
		if(myId != null)
		{
			return fieldId.startsWith(myId);
		}
		return false;
	}
	
	public boolean isSizeIndicator()
	{
		return sizeIndicator;
	}

	public void setSizeIndicator(boolean sizeIndicator)
	{
		this.sizeIndicator = sizeIndicator;
	}

	@Override
	public String toString()
	{
		return "GroupMember [id=" + getId() + "]";
	}

}
