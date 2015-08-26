package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*; 
import static org.hamcrest.CoreMatchers.*;
import gov.nyc.doitt.gis.geoclient.config.ReturnCodeValue;

import org.junit.Before;
import org.junit.Test;

public class ResponseTest
{
	private Fixtures fix;
	private Response response;

	@Before
	public void setUp() throws Exception
	{
		fix = new Fixtures();
		response = new Response(fix.successStatus, fix.geocodes);
	}

	@Test
	public void testConstructor()
	{
		assertThat(response.getGeocodes(), sameInstance(fix.geocodes));
		assertThat(response.getResponseStatus(), sameInstance(fix.successStatus));
		assertThat(response.getTimestamp(), not(nullValue()));
	}

	@Test
	public void testMessageAppliesTo()
	{
		assertFalse(response.messageAppliesTo(null));
		assertFalse(response.messageAppliesTo("truck"));
		response.getResponseStatus().getGeosupportReturnCode().setMessage("Street 'TRUCK STREET' not recognized");
		assertTrue(response.messageAppliesTo("truck"));
		assertFalse(response.messageAppliesTo("duck"));
		assertFalse(response.messageAppliesTo(null));
	}

	@Test
	public void testIsCompassDirectionRequired()
	{
		assertFalse(response.isCompassDirectionRequired());
		response.getResponseStatus().getGeosupportReturnCode().setReturnCode(ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.value());
		assertTrue(response.isCompassDirectionRequired());
		response.getResponseStatus().getGeosupportReturnCode().setReturnCode(null);
		assertFalse(response.isCompassDirectionRequired());
	}

	@Test
	public void testIsRejected()
	{
		assertFalse(response.isCompassDirectionRequired());
		assertTrue(new Response(fix.rejectStatus,fix.geocodes).isRejected());
	}

	@Test
	public void testSimilarNamesCount()
	{
		assertThat(response.similarNamesCount(), equalTo(0));
		response.getResponseStatus().getSimilarNames().add("abc");
		assertThat(response.similarNamesCount(), equalTo(1));
	}

	@Test
	public void testGetSimilarNames()
	{
		assertFalse(response.getSimilarNames().contains("abc"));
		assertThat(response.getSimilarNames(), sameInstance(response.getResponseStatus().getSimilarNames()));
		response.getResponseStatus().getSimilarNames().add("abc");
		assertTrue(response.getSimilarNames().contains("abc"));
	}

}
