package gov.nyc.doitt.gis.geoclient.config.xml;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.config.FunctionConfig;
import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.config.WorkAreaConfig;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class GeoclientXmlReaderIntegrationTest
{
	private static GeoclientXmlReader xmlReader;

	@BeforeClass
	public static void setUp() throws Exception
	{
		xmlReader = GeoclientXmlReader.fromXml(GeosupportConfig.DEFAULT_CONFIG_FILE);
		assertNotNull(xmlReader);
	}

	@Test
	public void testFilters()
	{
		List<Filter> filters = xmlReader.getFilters();
		for (Filter filter : filters)
		{
			assertFilter(filter);
		}
	}

	@Test
	public void testFunctionConfig()
	{
		List<FunctionConfig> functions = xmlReader.getFunctions();
		for (FunctionConfig fConfig : functions)
		{
			assertFunctionConfig(fConfig);
		}
	}

	@Test
	public void testWorkAreaConfig()
	{
		boolean foundWorkAreaOne = false;
		List<WorkAreaConfig> workAreas = xmlReader.getWorkAreas();
		for (WorkAreaConfig wConfig : workAreas)
		{
			if ("WA1".equals(wConfig.getId()))
			{
				foundWorkAreaOne = true;
				assertWorkAreaConfig(wConfig, true);
			} else
			{
				assertWorkAreaConfig(wConfig, false);
			}
		}
		assertTrue(foundWorkAreaOne);
	}

	private void assertFilter(Filter filter)
	{
		assertNotNull(filter);
	}

	private void assertFunctionConfig(FunctionConfig fConfig)
	{
		String id = fConfig.getId();
		assertNotNull(id);
		assertNotNull(fConfig.getWorkAreaOneConfig());
		if (!Function.FDG.equals(id) && !Function.FBB.equals(id) && !Function.FBF.equals(id))
		{
			assertNotNull(fConfig.getWorkAreaTwoConfig());
		} else
		{
			assertNull(fConfig.getWorkAreaTwoConfig());
		}
		if(Function.FBL.equals(id))
		{
            assertNotNull(fConfig.getConfiguration());
            assertNotNull(fConfig.getConfiguration().requiredArguments());
            assertFalse(fConfig.getConfiguration().requiredArguments().isEmpty());
        }
	}

	private void assertField(Field field)
	{
		assertNotNull(field);
		assertNotNull(field.getId());
		assertTrue(field.getStart() >= 0);
		assertTrue(field.getLength() > 0);
	}

	private void assertWorkAreaConfig(WorkAreaConfig wConfig, boolean isWorkAreaOne)
	{
		assertNotNull(wConfig.getId());
		assertTrue(wConfig.getLength() > 0);
		if (isWorkAreaOne)
		{
			assertTrue(wConfig.isWorkAreaOne());
			List<Field> fields = wConfig.getFields();
			assertFalse(fields.isEmpty());
			for (Field field : fields)
			{
				assertField(field);
			}
		} else
		{
			assertFalse(wConfig.isWorkAreaOne());
		}
	}

}
