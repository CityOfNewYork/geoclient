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
