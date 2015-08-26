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

import java.util.ArrayList;
import java.util.List;

public class Table
{
	private String caption;
	private String id;
	private List<TableRow> rows;

	public Table()
	{
		this(null, new ArrayList<TableRow>(), null);
	}

	public Table(String id, List<TableRow> rows, String caption)
	{
		super();
		this.id = id;
		this.rows = rows;
		this.caption = caption;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getId()
	{
		return id;
	}

	public List<TableRow> getRows()
	{
		return rows;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setRows(List<TableRow> rows)
	{
		this.rows = rows;
	}

	public String toHtml()
	{
		StringBuffer sb = new StringBuffer("<table");
		if (id != null)
		{
			sb.append(" id=\"" + id + "\"");
		}
		sb.append(">\n");
		if (caption != null)
		{
			sb.append(String.format("<caption>%s</caption>\n", caption));
		}
		for (TableRow tr : this.rows)
		{
			sb.append(tr.toHtml());
			sb.append("\n");
		}
		sb.append("</table>");
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return toHtml();
	}
}
