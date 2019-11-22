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


public class TableData
{
	private int colspan;
	private Footnote footnote;
	private boolean header;
	private String text;
	
	public TableData()
	{
		this(null);
	}
	
	public TableData(String text)
	{
		this(text, 1, false, null);
	}
	
	public TableData(String text, int colspan)
	{
		this(text, colspan, false, null);
	}

	public TableData(String text, int colspan, boolean header, Footnote footnote)
	{
		super();
		this.text = text;
		this.colspan = colspan;
		this.header = header;
		this.footnote = footnote;
	}

	public int getColspan()
	{
		return colspan;
	}

	public Footnote getFootnote()
	{
		return footnote;
	}

	public String getText()
	{
		return text;
	}

	public boolean isHeader()
	{
		return header;
	}

	public void setColspan(int colspan)
	{
		this.colspan = colspan;
	}

	public void setFootnote(Footnote footnote)
	{
		this.footnote = footnote;
	}

	public void setHeader(boolean header)
	{
		this.header = header;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String toHtml()
	{
		String element = "td";
		String spanTxt = "";
		
		if(header)
		{
			element = "th";
		}
		
		if(colspan > 1)
		{
			spanTxt = " colspan=\"" + colspan + "\"";
		}
		
		StringBuffer sb = new StringBuffer(text != null ? text : "");
		
		if(footnote != null)
		{
			sb.insert(footnote.getPosition(), footnote.toHtml());
		}
		
		return String.format("<%s%s>%s</%s>", element, spanTxt, sb.toString(), element);
	}
	
	@Override
	public String toString()
	{
		return toHtml();
	}

	
}
