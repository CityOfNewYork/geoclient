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
package gov.nyc.doitt.gis.geoclient.config.xml;

import gov.nyc.doitt.gis.geoclient.config.FunctionConfig;
import gov.nyc.doitt.gis.geoclient.config.WorkAreaConfig;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;
import gov.nyc.doitt.gis.geoclient.util.ClassUtils;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GeoclientXmlReader
{
    // TODO get rid of constants as they are hard to read and use
	static final String CLASS_FILTER_PROPERTY_PATTERN = "pattern";
	static final String CLASS_FUNCTION_PROPERTY_ID = "id";
	static final String CLASS_FUNCTION_PROPERTY_WORKAREAONE = "workAreaOneConfig";
	static final String CLASS_FUNCTION_PROPERTY_WORKAREATWO = "workAreaTwoConfig";
	static final String CLASS_WORK_AREA_PROPERTY_ID = "id";
	static final String CLASS_WORK_AREA_PROPERTY_IS_WA1 = "isWorkAreaOne";
    static final String XML_CONFIGURATION_ELEMENT = "configuration";    
	static final String XML_FIELD_ATTRIBUTE_ALIAS = "alias";
	static final String XML_FIELD_ATTRIBUTE_ID = "id";
	static final String XML_FIELD_ATTRIBUTE_INPUT = "input";
	static final String XML_FIELD_ATTRIBUTE_LENGTH = "length";
	static final String XML_FIELD_ATTRIBUTE_START = "start";
	static final String XML_FIELD_ATTRIBUTE_TYPE = "type";
	static final String XML_FIELD_ATTRIBUTE_WHITESPACE = "whitespace";
	static final String XML_FIELD_ELEMENT = "field";
	static final String XML_FIELD_VALUE_COMPOSITE_TYPE = "COMP";
	static final String XML_FIELD_VALUE_REGULAR_TYPE = "REG";
	static final String XML_FIELDS_ELEMENT = "fields";
	static final String XML_FILTER_ATTRIBUTE_PATTERN = "pattern";
	static final String XML_FILTER_ELEMENT = "filter";
	static final String XML_FILTERS_ELEMENT = "filters";
	static final String XML_FUNCTION_ATTRIBUTE_ID = "id";
	static final String XML_FUNCTION_ELEMENT = "function";
	static final String XML_FUNCTION_WORKAREAONE_ELEMENT = "workAreaOne";
	static final String XML_FUNCTION_WORKAREATWO_ELEMENT = "workAreaTwo";
	static final String XML_LENGTH_ELEMENT = "length";
	static final String XML_OUTPUT_FILTERS_ELEMENT = "outputFilters";
    static final String XML_REQUIREDARGUMENT_ELEMENT = "requiredArgument";    
    static final String XML_REQUIREDARGUMENT_ATTRIBUTE_NAME = "name";    
    static final String XML_REQUIREDARGUMENT_ATTRIBUTE_VALUE = "value";    
	static final String XML_ROOT_ELEMENT = "geoclient";
	static final String XML_WORK_AREA_ATTRIBUTE_ID = "id";
	static final String XML_WORK_AREA_ATTRIBUTE_IS_WA1 = "isWA1";
	static final String XML_WORK_AREA_ELEMENT = "workArea";

	public static GeoclientXmlReader fromXml(String configFile)
	{
		XStream xStream = new XStream(new DomDriver());
		// Use reference="another id" to reference elements
		xStream.setMode(XStream.ID_REFERENCES);

		// <geoclient> to class GeoclientXmlReader
		xStream.alias(XML_ROOT_ELEMENT, GeoclientXmlReader.class);

	    // <filter> to class Filter
		xStream.alias(XML_FILTER_ELEMENT, Filter.class);
		
		// <filter pattern=""> to Filter.pattern
		xStream.aliasAttribute(Filter.class, CLASS_FILTER_PROPERTY_PATTERN, XML_FILTER_ATTRIBUTE_PATTERN);

		// <field> to class Field
		xStream.alias(XML_FIELD_ELEMENT, Field.class);
		
		// Custom converter for <field>
		xStream.registerConverter(new FieldConverter());

		// <workArea> to class WorkAreaConfig
		xStream.alias(XML_WORK_AREA_ELEMENT, WorkAreaConfig.class);

		// <workArea id=""> to WorkArea.id
		xStream.aliasAttribute(WorkAreaConfig.class, CLASS_WORK_AREA_PROPERTY_ID, XML_WORK_AREA_ATTRIBUTE_ID);
		
		// <workArea isWA1=""> to WorkArea.isWorkAreaOne
		xStream.aliasAttribute(WorkAreaConfig.class, CLASS_WORK_AREA_PROPERTY_IS_WA1, XML_WORK_AREA_ATTRIBUTE_IS_WA1);
		
		// <configuration> to class DefaultConfiguration
        xStream.alias(XML_CONFIGURATION_ELEMENT, DefaultConfiguration.class);
        
        // Custom converter for <configuration>
        xStream.registerConverter(new ConfigurationConverter());
		
		// <function> to class FunctionConfig
		xStream.alias(XML_FUNCTION_ELEMENT, FunctionConfig.class);
		
		// <function id=""> to FunctionConfig.id
		xStream.aliasAttribute(FunctionConfig.class, CLASS_FUNCTION_PROPERTY_ID, XML_FUNCTION_ATTRIBUTE_ID);
		
		// <function workAreaOne=""> to FunctionConfig.workAreaOneConfig
		xStream.aliasField(XML_FUNCTION_WORKAREAONE_ELEMENT, FunctionConfig.class, CLASS_FUNCTION_PROPERTY_WORKAREAONE);
		
		// <function workAreaTwo=""> to FunctionConfig.workAreaTwoConfig
		xStream.aliasField(XML_FUNCTION_WORKAREATWO_ELEMENT, FunctionConfig.class, CLASS_FUNCTION_PROPERTY_WORKAREATWO);

		return (GeoclientXmlReader) xStream.fromXML(ClassUtils.getDefaultClassLoader().getResourceAsStream(configFile));
	}
	private List<Filter> filters;
	private List<FunctionConfig> functions;

	private List<WorkAreaConfig> workAreas;

	public List<Filter> getFilters()
	{
		return filters;
	}

	public List<FunctionConfig> getFunctions()
	{
		return functions;
	}

	public List<WorkAreaConfig> getWorkAreas()
	{
		return workAreas;
	}

	public void setFilters(List<Filter> filters)
	{
		this.filters = filters;
	}

	public void setFunctions(List<FunctionConfig> functions)
	{
		this.functions = functions;
	}

	public void setWorkAreas(List<WorkAreaConfig> workAreas)
	{
		this.workAreas = workAreas;
	}

}
