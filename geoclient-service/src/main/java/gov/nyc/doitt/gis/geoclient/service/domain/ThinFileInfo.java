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
package gov.nyc.doitt.gis.geoclient.service.domain;

import java.util.List;

public class ThinFileInfo extends FileInfo
{
	private List<String> recordTypes;
	private List<String> fillerFields;

	public ThinFileInfo(){}
	public ThinFileInfo(List<String> recordTypes, String tag, String date, String release, String recordCount, List<String> fillerFields)
	{
		super(null, tag, date, release, recordCount);
		this.recordTypes = recordTypes;
		this.fillerFields = fillerFields;
	}

	public List<String> getRecordTypes()
	{
		return recordTypes;
	}

	public void setRecordTypes(List<String> recordTypes)
	{
		this.recordTypes = recordTypes;
	}

	public List<String> getFillerFields()
	{
		return fillerFields;
	}

	public void setFillerFields(List<String> fillerFields)
	{
		this.fillerFields = fillerFields;
	}

	@Override
	public String toString()
	{
		return "ThinFileInfo [recordTypes=" + recordTypes + ", fillerFields=" + fillerFields + "]";
	}
	
	
}
