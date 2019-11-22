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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Description
{
	private List<Paragraph> paragraphs;

	public Description()
	{
		super();
		this.paragraphs = new ArrayList<Paragraph>();
	}

	public Description(String text)
	{
		this();
		this.paragraphs.add(new Paragraph(text));
	}

	public boolean hasText()
	{
		if (this.paragraphs != null)
		{
			for (Paragraph p : this.paragraphs)
			{
				if (p.hasText())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public String summarize(int length)
	{
		String everything = joinText(" ");
		if(everything.length() <= length)
		{
			return everything;
		}
		return everything.substring(0, length);
	}

	public String joinText(String between)
	{
		StringBuffer sb = new StringBuffer();
		if (this.paragraphs != null)
		{
			for (Iterator<Paragraph> iterator = this.paragraphs.iterator(); iterator.hasNext();)
			{
				sb.append(iterator.next().getText());
				if (iterator.hasNext())
				{
					sb.append(between);
				}
			}
		}
		return sb.toString();
	}

	public Description(List<Paragraph> paragraphs)
	{
		super();
		this.paragraphs = paragraphs;
	}

	public List<Paragraph> getParagraphs()
	{
		return paragraphs;
	}

	public void setParagraphs(List<Paragraph> paragraphs)
	{
		this.paragraphs = paragraphs;
	}

	@Override
	public String toString()
	{
		return toHtml();
	}

	public String toHtml()
	{
		StringBuffer sb = new StringBuffer();
		if (this.paragraphs != null)
		{
			for (Paragraph paragraph : this.paragraphs)
			{
				sb.append(paragraph.toHtml());
			}
		}
		return sb.toString();
	}
}
