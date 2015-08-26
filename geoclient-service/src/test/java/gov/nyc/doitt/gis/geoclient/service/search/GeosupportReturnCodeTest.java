package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*;
import gov.nyc.doitt.gis.geoclient.config.ReturnCodeValue;

import org.junit.Before;
import org.junit.Test;

public class GeosupportReturnCodeTest
{
	private GeosupportReturnCode grc;
	
	@Before
	public void setUp()
	{
		this.grc = new GeosupportReturnCode();
	}
	@Test
	public void testIsCompassDirectionRequired()
	{
		assertFalse(this.grc.isCompassDirectionRequired());
		this.grc.setReturnCode(ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.value());
		assertTrue(this.grc.isCompassDirectionRequired());
	}
	
	@Test
	public void testHasSimilarNames()
	{
		assertFalse(this.grc.hasSimilarNames());
		this.grc.setReturnCode(ReturnCodeValue.NOT_RECOGNIZED_WITH_SIMILAR_NAMES.value());
		assertTrue(this.grc.hasSimilarNames());
		this.grc.setReturnCode(ReturnCodeValue.NOT_RECOGNIZED_NO_SIMILAR_NAMES.value());
		assertFalse(this.grc.hasSimilarNames());
	}	
	
	@Test
	public void testIsRejected()
	{
		assertTrue(this.grc.isRejected());
		this.grc.setReturnCode("00");
		assertFalse(this.grc.isRejected());
		this.grc.setReturnCode("EE");
		assertTrue(this.grc.isRejected());
		this.grc.setReturnCode("01");
		assertFalse(this.grc.isRejected());
	}

	@Test
	public void testHasReasonCode()
	{
		assertFalse(this.grc.hasReasonCode());
		this.grc.setReasonCode("V");
		assertTrue(this.grc.hasReasonCode());
	}

	@Test
	public void testHasMessage()
	{
		assertFalse(this.grc.hasMessage());
		this.grc.setMessage("Hello");
		assertTrue(this.grc.hasMessage());
	}

}
