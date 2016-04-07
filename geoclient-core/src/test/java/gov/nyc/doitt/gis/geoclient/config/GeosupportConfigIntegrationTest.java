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
package gov.nyc.doitt.gis.geoclient.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientImpl;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientStub;
import gov.nyc.doitt.gis.geoclient.util.OperatingSystemUtils;

import org.junit.BeforeClass;
import org.junit.Test;

public class GeosupportConfigIntegrationTest
{
	protected static GeosupportConfig geosupportConfig;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		if(OperatingSystemUtils.isWindows())
		{
			geosupportConfig = new GeosupportConfig(new GeoclientStub());
		}else
		{
			geosupportConfig = new GeosupportConfig(new GeoclientImpl());
		}
	}	
	
	@Test(expected=UnknownFunctionException.class)
	public void nonExistentFunction()
	{
		geosupportConfig.getFunction("fun");
	}
	
	@Test
	public void testGetFunction()
	{
		assertNotNull(geosupportConfig.getFunction(Function.F1));
		assertNotNull(geosupportConfig.getFunction(Function.F1E));
		assertNotNull(geosupportConfig.getFunction(Function.F1A));
		assertNotNull(geosupportConfig.getFunction(Function.F1AX));
		assertNotNull(geosupportConfig.getFunction(Function.F1B));
		assertNotNull(geosupportConfig.getFunction(Function.FBL));
		assertNotNull(geosupportConfig.getFunction(Function.FBN));
		assertNotNull(geosupportConfig.getFunction(Function.F2));
		assertNotNull(geosupportConfig.getFunction(Function.F3));
		assertNotNull(geosupportConfig.getFunction(Function.FDG));
		assertNotNull(geosupportConfig.getFunction(Function.FHR));
	}
	
    @Test
    public void testGetFunctionConfiguration()
    {
        assertNotNull(geosupportConfig.getFunction(Function.F1).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1E).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1A).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1AX).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1B).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FBL).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FBN).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F2).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F3).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FDG).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FHR).getConfiguration());
    }
    
	@Test
	public void testItemDocumentationDisplayNameSetProperly()
	{
		assertItemDocumentationDisplayNames(geosupportConfig.getFunctionDocumentation(Function.F1B), Arrays.asList("listOfSecondSetOf5Lgcs"));
		assertItemDocumentationDisplayNames(geosupportConfig.getFunctionDocumentation(Function.F2), Arrays.asList("dcpPreferredLgcForStreet1"));
	}
	
	private void assertItemDocumentationDisplayNames(FunctionDocumentation functionDocumentation, List<String> names) 
	{
		for (String name : names)
		{
			assertTrue(String.format("%s is missing expected ItemDocumentation with displayName='%s'", functionDocumentation,name),containsItemDocumentationWithDisplayName(name, functionDocumentation.getFields()));
		}
	}

	private boolean containsItemDocumentationWithDisplayName(String displayName, SortedSet<ItemDocumentation> items)
	{
		for (ItemDocumentation itemDocumentation : items)
		{
			if(displayName.equals(itemDocumentation.getDisplayName()))
			{
				return true;
			}
		}
		return false;
	}
	
}
