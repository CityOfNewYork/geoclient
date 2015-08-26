package gov.nyc.doitt.gis.geoclient.config.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.Table;
import gov.nyc.doitt.gis.geoclient.function.Function;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentationXmlReaderIntegrationTest
{
	private static DocumentationXmlReader xmlReader;

	@BeforeClass
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
		functionIds.add(Function.FBL);
		functionIds.add(Function.FBN);
		functionIds.add(Function.FDG);
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
			assertTrue(String.format("<%s> tag for %s was emtpy", elementName, objectName), alias.trim().length() > 1);
		}
	}
}
