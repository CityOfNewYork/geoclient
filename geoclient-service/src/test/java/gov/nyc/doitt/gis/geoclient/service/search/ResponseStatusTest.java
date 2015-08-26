package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import gov.nyc.doitt.gis.geoclient.config.ReturnCodeValue;

import org.junit.Before;
import org.junit.Test;

public class ResponseStatusTest
{
	private ResponseStatus responseStatus;

	@Before
	public void setUp() throws Exception
	{
		this.responseStatus = new ResponseStatus();
	}
	
	@Test
	public void testIsCompassDirectionRequired()
	{
		assertFalse(this.responseStatus.isCompassDirectionRequired());
		this.responseStatus.getGeosupportReturnCode().setReturnCode(ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.value());
		assertTrue(this.responseStatus.isCompassDirectionRequired());
	}
	
	@Test
	public void testIsRejected()
	{
		assertThat(this.responseStatus.isRejected(), is(true));
		GeosupportReturnCode grc1 = this.responseStatus.getGeosupportReturnCode();
		grc1.setReturnCode("00");
		assertThat(grc1.isRejected(), is(false));
		GeosupportReturnCode grc2 = this.responseStatus.getGeosupportReturnCode2();
		assertThat(grc2.isRejected(), is(true));
		assertThat(this.responseStatus.isRejected(), is(false));
		grc2.setReturnCode("01");
		assertThat(grc2.isRejected(), is(false));
		assertThat(this.responseStatus.isRejected(), is(false));
		grc1.setReturnCode("EE");
		assertThat(grc1.isRejected(), is(true));
		assertThat(this.responseStatus.isRejected(), is(false));
		grc2.setReturnCode("11");
		assertThat(grc2.isRejected(), is(true));
		assertThat(this.responseStatus.isRejected(), is(true));
		this.responseStatus.setGeosupportReturnCode(null);
		assertThat(this.responseStatus.isRejected(), is(true));
		this.responseStatus.setGeosupportReturnCode2(null);
		assertThat(this.responseStatus.isRejected(), is(true));
	}

	@Test
	public void testSimilarNamesCount()
	{
		assertThat(this.responseStatus.similarNamesCount(), equalTo(0));
		this.responseStatus.getSimilarNames().add("abc");
		assertThat(this.responseStatus.similarNamesCount(), equalTo(1));
		this.responseStatus.setSimilarNames(null);
		assertThat(this.responseStatus.similarNamesCount(), equalTo(0));
	}

}
