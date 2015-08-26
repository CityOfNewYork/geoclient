package gov.nyc.doitt.gis.geoclient.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

import java.util.Map;
import java.util.TreeSet;

import org.junit.Test;

public class RegistryTest
{
	private static final String WA_NAME = "WA1";
	private WorkArea workArea = new WorkArea(WA_NAME, new TreeSet<Field>());
	private String functionId = Function.F1B;

	@Test
	public void testWorkAreaRegistry()
	{
		Registry.clearWorkAreas();
		assertFalse(Registry.containsWorkArea(WA_NAME));
		assertNull(Registry.getWorkArea(WA_NAME));
		Registry.addWorkArea(workArea);
		assertTrue(Registry.containsWorkArea(WA_NAME));
		assertSame(workArea, Registry.getWorkArea(WA_NAME));
		Registry.clearWorkAreas();
		assertFalse(Registry.containsWorkArea(WA_NAME));
		assertNull(Registry.getWorkArea(WA_NAME));
	}

	@Test
	public void testFunctionRegistry()
	{
		Registry.clearFunctions();
		Function function = new Function()
		{
			@Override
			public String getId() { return functionId; }
			@Override
			public Map<String, Object> call(Map<String, Object> parameters){ return null; }
			@Override
			public WorkArea getWorkAreaOne(){ return null; }
			@Override
			public WorkArea getWorkAreaTwo() { return null; }
			@Override
			public boolean isTwoWorkAreas() { return false; }
            @Override
            public Configuration getConfiguration(){ return null; }
		};
		assertFalse(Registry.containsFunction(functionId));
		assertNull(Registry.getFunction(functionId));
		Registry.addFunction(function);
		assertTrue(Registry.containsFunction(functionId));
		assertSame(function, Registry.getFunction(functionId));
		Registry.clearFunctions();
		assertFalse(Registry.containsFunction(functionId));
		assertNull(Registry.getFunction(functionId));
	}
	
	@Test
	public void testFunctionDocumentationRegistry()
	{
		Registry.clearFunctionDocumentation();
		FunctionDocumentation expected = new FunctionDocumentation();
		expected.setId(functionId);
		assertFalse(Registry.containsFunctionDocumentation(functionId));
		assertNull(Registry.getFunctionDocumentation(functionId));
		Registry.addFunctionDocumentation(expected);
		assertTrue(Registry.containsFunctionDocumentation(functionId));
		assertSame(expected, Registry.getFunctionDocumentation(functionId));
		Registry.clearFunctionDocumentation();
		assertFalse(Registry.containsFunctionDocumentation(functionId));
		assertNull(Registry.getFunctionDocumentation(functionId));		
	}	
}
