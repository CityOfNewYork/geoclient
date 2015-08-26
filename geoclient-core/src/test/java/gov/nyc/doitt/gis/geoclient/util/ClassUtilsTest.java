package gov.nyc.doitt.gis.geoclient.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassUtilsTest
{

	@Test
	public void testGetDefaultClassLoader()
	{
		assertNotNull(ClassUtils.getDefaultClassLoader());
	}

}
