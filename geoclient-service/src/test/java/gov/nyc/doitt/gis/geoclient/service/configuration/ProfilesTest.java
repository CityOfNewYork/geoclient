package gov.nyc.doitt.gis.geoclient.service.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfilesTest
{

	@Test
	public void testValue()
	{
		assertEquals("apikey", Profiles.API_KEY.value());
	}

}
