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
package gov.nyc.doitt.gis.geoclient.service.search.policy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.Fixtures;

import org.junit.Before;
import org.junit.Test;

public class DefaultExactMatchPolicyTest
{
	private Fixtures fix;
	private DefaultExactMatchPolicy policy;

	@Before
	public void setUp() throws Exception
	{
		this.fix = new Fixtures();
		this.policy = new DefaultExactMatchPolicy();
	}

	@Test
	public void testDefaultPolicySettings()
	{
		assertThat(policy.isExactMatchForSingleSuccess(), equalTo(DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_FOR_SINGLE_SUCCESS));
		assertThat(policy.getExactMatchMaxLevel(), equalTo(DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_MAX_LEVEL));
	}
	
	@Test
	public void testFindExactMatch_choosesFirstSuccessResultOfTheRightLevel()
	{
		Search searchOne = new Search(fix.requestLevelOne,fix.responseSuccess);
		fix.searchResult.add(searchOne);
		assertThat(policy.findExactMatch(fix.searchResult),is(nullValue()));
		Search searchZero = new Search(fix.requestLevelZero,fix.responseSuccess);
		fix.searchResult.add(searchZero);
		assertThat(policy.findExactMatch(fix.searchResult),equalTo(searchZero));
	}	
	
	@Test
	public void testMarkExactMatch_singleSuccessDisabled()
	{
		
		Search searchZero = new Search(fix.requestLevelZero,fix.responseReject);
		fix.searchResult.add(searchZero);
		assertThat(policy.findExactMatch(fix.searchResult),is(nullValue()));
		Search searchOne = new Search(fix.requestLevelOne,fix.responseSuccess);
		fix.searchResult.add(searchOne);
		assertThat(policy.findExactMatch(fix.searchResult),is(nullValue()));
	}	

	@Test
	public void testMarkExactMatch_singleSuccessEnabled()
	{
		policy.setExactMatchForSingleSuccess(true);
		Search searchZero = new Search(fix.requestLevelZero,fix.responseReject);
		fix.searchResult.add(searchZero);
		assertThat(policy.findExactMatch(fix.searchResult),is(nullValue()));
		Search searchOne = new Search(fix.requestLevelOne,fix.responseSuccess);
		fix.searchResult.add(searchOne);
		assertThat(policy.findExactMatch(fix.searchResult),equalTo(searchOne));
	}	
	

}
