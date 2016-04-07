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
package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;

import org.junit.Before;
import org.junit.Test;

public class SearchTest
{
	private Fixtures fix;
	private Search search;

	@Before
	public void setUp() throws Exception
	{
		fix = new Fixtures();
		search = new Search(fix.requestLevelOne,fix.responseSuccess);
	}

	@Test
	public void testConstructor()
	{
		assertThat((AddressRequest)search.getRequest(), sameInstance(fix.requestLevelOne));
		assertThat(search.getResponse(), sameInstance(fix.responseSuccess));
	}

	@Test
	public void testIsRejected()
	{
		assertFalse(search.isRejected());
		assertTrue(new Search(fix.requestLevelFour,fix.responseReject).isRejected());
	}

	@Test(expected=NullPointerException.class)
	public void testIsRejected_nullResponse()
	{
		new Search(fix.requestLevelFour,null).isRejected();
	}

	@Test
	public void testGetSimilarNames()
	{
		assertThat(search.getSimilarNames(),sameInstance(search.getResponse().getSimilarNames()));
	}

	@Test(expected=NullPointerException.class)
	public void testgetSimilarNames_nullResponse()
	{
		new Search(fix.requestLevelFour,null).getSimilarNames();
	}

	@Test
	public void testResponseMessageAppliesTo()
	{
		String streetName = "FOO";
		assertThat(search.responseMessageAppliesTo(streetName),equalTo(search.getResponse().messageAppliesTo(streetName)));
		assertFalse(search.responseMessageAppliesTo(streetName));
		search.getResponse().getResponseStatus().getGeosupportReturnCode().setMessage(streetName);
		assertThat(search.responseMessageAppliesTo(streetName),equalTo(search.getResponse().messageAppliesTo(streetName)));
		assertTrue(search.responseMessageAppliesTo(streetName));
	}

	@Test(expected=NullPointerException.class)
	public void testResponseMessageAppliesTo_nullResponse()
	{
		new Search(fix.requestLevelFour,null).responseMessageAppliesTo("foo");
	}

	@Test
	public void testGetResponseStatus()
	{
		assertThat(search.getResponseStatus(),sameInstance(search.getResponse().getResponseStatus()));
	}

	@Test
	public void testGetLevel()
	{
		search.getRequest().setLevel(128);
		assertThat(search.getLevel(),equalTo(search.getRequest().getLevel()));		
		assertThat(search.getLevel(),equalTo(128));
	}

	@Test
	public void testLessThanOrEqualTo()
	{
		search.getRequest().setLevel(128);
		assertThat(search.getLevel(),equalTo(128));
		assertTrue(search.lessThanOrEqualTo(256));
		assertTrue(search.lessThanOrEqualTo(128));
		assertFalse(search.lessThanOrEqualTo(12));
	}

}
