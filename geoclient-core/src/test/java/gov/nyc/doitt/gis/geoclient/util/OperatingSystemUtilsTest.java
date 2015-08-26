package gov.nyc.doitt.gis.geoclient.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperatingSystemUtilsTest
{
	private static final String osName = System.getProperty("os.name");

	@Test
	public void testIsWindows()
	{
		if(osName.contains("Windows"))
		{
			assertTrue(OperatingSystemUtils.isWindows());
		}else
		{
			assertFalse(OperatingSystemUtils.isWindows());
		}
	}

	@Test
	public void testUName()
	{
		assertEquals(osName, OperatingSystemUtils.uname());
	}

}
