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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;

public class LatLongConfig
{
	private String latName;
	private String longName;
	private String xCoordInputName;
	private String yCoordInputName;
	private ItemDocumentation latDocumentation;
	private ItemDocumentation longDocumentation;

	public LatLongConfig(String latName, String longName, String xCoordInputName, String yCoordInputName,
			ItemDocumentation latDocumentation, ItemDocumentation longDocumentation)
	{
		super();
		this.latName = latName;
		this.longName = longName;
		this.xCoordInputName = xCoordInputName;
		this.yCoordInputName = yCoordInputName;
		this.latDocumentation = latDocumentation;
		this.longDocumentation = longDocumentation;
	}

	public String getLatName()
	{
		return latName;
	}

	public void setLatName(String latName)
	{
		this.latName = latName;
	}

	public String getLongName()
	{
		return longName;
	}

	public void setLongName(String longName)
	{
		this.longName = longName;
	}

	public String getXCoordInputName()
	{
		return xCoordInputName;
	}

	public void setXCoordInputName(String xCoordInputName)
	{
		this.xCoordInputName = xCoordInputName;
	}

	public String getYCoordInputName()
	{
		return yCoordInputName;
	}

	public void setYCoordInputName(String yCoordInputName)
	{
		this.yCoordInputName = yCoordInputName;
	}

	public ItemDocumentation getLatDocumentation()
	{
		return latDocumentation;
	}

	public void setLatDocumentation(ItemDocumentation latDocumentation)
	{
		this.latDocumentation = latDocumentation;
	}

	public ItemDocumentation getLongDocumentation()
	{
		return longDocumentation;
	}

	public void setLongDocumentation(ItemDocumentation longDocumentation)
	{
		this.longDocumentation = longDocumentation;
	}
}
