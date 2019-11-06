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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.Table;
import gov.nyc.doitt.gis.geoclient.function.Function;

public class DocumentationXmlReaderIntegrationTest
{
	private static DocumentationXmlReader xmlReader;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception
	{
		xmlReader = DocumentationXmlReader.fromXml(GeosupportConfig.DEFAULT_DOCUMENTATION_CONFIG_FILE);
		assertNotNull(xmlReader);
	}

	@Test
	public void testGetFunctionDocumentation()
	{
		List<String> functionIds = new ArrayList<String>();
		functionIds.add(Function.F1);
		functionIds.add(Function.F1A);
		functionIds.add(Function.F1AX);
		functionIds.add(Function.F1B);
		functionIds.add(Function.F1E);
		functionIds.add(Function.F2);
		functionIds.add(Function.F3);
		functionIds.add(Function.FAP);
		functionIds.add(Function.FBL);
		functionIds.add(Function.FBN);
		functionIds.add(Function.FD);
		functionIds.add(Function.FDG);
		functionIds.add(Function.FDN);
		functionIds.add(Function.FN);
		assertNull(xmlReader.getFunctionDocumentation("zzzz"));
	}

	@Test
	public void testDataDictionary()
	{
		DataDictionary dataDictionary = xmlReader.getDataDictionary();
		assertNotNull(dataDictionary);
		List<ItemDocumentation> items = dataDictionary.getItems();
		for (ItemDocumentation itemDocumentation : items)
		{
			assertItemDocumentation(itemDocumentation);
		}
	}

	private void assertItemDocumentation(ItemDocumentation iDoc)
	{
		assertNotNull(iDoc.getId());
		assertTrue(iDoc.getDescription() != null || iDoc.getTables() != null);
		assertNotNull(iDoc.getFormat());
		assertNotNull(iDoc.getFunctionNames());
		if (iDoc.getAliases() != null)
		{
			assertListOfStrings("alias", iDoc.toString(), iDoc.getAliases());
		}
		if (iDoc.getSeeAlso() != null)
		{
			assertListOfStrings("seeAlso", iDoc.toString(), iDoc.getSeeAlso());
		}
		if (iDoc.getTables() != null)
		{
			for (Table table : iDoc.getTables())
			{
				assertNotNull(table.toHtml());
			}
		}
	}

	private void assertListOfStrings(String elementName, String objectName, List<String> strings)
	{
		for (String alias : strings)
		{
			assertTrue(alias.trim().length() > 1, String.format("<%s> tag for %s was emtpy", elementName, objectName));
		}
	}
}
