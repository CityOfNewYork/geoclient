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
package gov.nyc.doitt.gis.geoclient.service.web;

import gov.nyc.doitt.gis.geoclient.doc.BaseDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.GroupDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.GroupMember;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentationSupport;

public class ViewHelper
{
	public String sectionHeaderText(int section, int subsection, FunctionDocumentation functionDocumentation)
	{
		return String.format("%s - %s (Function %s)", sectionNumber(section, subsection), functionDocumentation.getDisplayName(),functionDocumentation.getId());
	}

	public String sectionAnchor(int section, int subsection)
	{
		return String.format("section-%s", sectionNumber(section, subsection));
	}

	public String sectionNumber(int section, int subsection)
	{
		return String.format("%d.%d", section, subsection);
	}

	public String href(String id)
	{
	  if(id != null && id.toLowerCase().matches("^https?://.*"))
	  {
	    return id;
	  }
		return String.format("#%s",id);
	}

	public String href(ItemDocumentation itemDocumentation)
	{
		return href(itemAnchor(itemDocumentation));
	}

	public String href(ItemDocumentationSupport ids)
	{
		return href(ids.getItemDocumentation());
	}

	public String itemAnchor(ItemDocumentation itemDocumentation)
	{
		return String.format("item-%s", itemDocumentation.getId());
	}

	public String memberText(GroupDocumentation g, GroupMember m)
	{
		StringBuffer sb = new StringBuffer(m.getId());

		if(!m.isSizeIndicator())
		{
			// Not the size indicator so add 1..max
			sb.append("1 to " + g.getMax());
		}

		return sb.toString();
	}

	public String summarize(BaseDocumentation doc, int length, String elideText)
	{
		StringBuffer summary = new StringBuffer(doc.summarize(length));
		if(length == summary.length())
		{
			summary.append(elideText);
		}
		return summary.toString();
	}

}
