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
package gov.nyc.doitt.gis.geoclient.config;

import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.MissingDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentationConfig
{
	private static final Logger log = LoggerFactory.getLogger(DocumentationConfig.class);
	private DataDictionary dataDictionary;
	
	public DocumentationConfig(DataDictionary dataDictionary)
	{
		super();
		this.dataDictionary = dataDictionary;
	}

	public FunctionDocumentation document(FunctionDocumentation functionDocumentation, Function function)
	{
		addItemDocumentation(functionDocumentation, getFieldIds(function.getWorkAreaOne()));
		if(function.isTwoWorkAreas())
		{
			addItemDocumentation(functionDocumentation, getFieldIds(function.getWorkAreaTwo()));
		}
		return functionDocumentation;
	}
	
	private void addItemDocumentation(FunctionDocumentation functionDocumentation, List<String> fieldIds)
	{
		for (String fieldId : fieldIds)
		{
			ItemDocumentation itemDocumentation = dataDictionary.findItem(fieldId);
			if(itemDocumentation !=null)
			{
				functionDocumentation.add(itemDocumentation.copyWithDisplayName(fieldId));
			}else
			{
				MissingDocumentation md = new MissingDocumentation(fieldId);
				if(!functionDocumentation.isGroupMember(md)){
					log.debug(md.toString());
					functionDocumentation.add(md);
				}
			}
		}
	}
	
	private List<String> getFieldIds(WorkArea workArea)
	{
		return workArea.getFieldIds(Field.NAME_SORT, false, false);
	}
}
