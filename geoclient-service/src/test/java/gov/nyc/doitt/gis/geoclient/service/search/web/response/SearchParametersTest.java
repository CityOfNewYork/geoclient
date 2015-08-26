package gov.nyc.doitt.gis.geoclient.service.search.web.response;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultExactMatchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultSearchDepthPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultSimilarNamesPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchParameters;

import org.junit.Before;
import org.junit.Test;

public class SearchParametersTest
{
	private SearchParameters params;

	@Before
	public void setUp() throws Exception
	{
		this.params = new SearchParameters();
	}
	@Test
	public void testInputConstructor()
	{
		assertDefaultSettings("foo", new SearchParameters("foo"));
	}
	
	@Test
	public void testDefaultConstructor()
	{
		assertDefaultSettings(null, params);
	}

	@Test
	public void testBuildSearchPolicy()
	{
		params.setMaxDepth(5);
		params.setSimilarNamesDistance(2);
		params.setExactMatchMaxLevel(3);
		params.setExactMatchForSingleSuccess(true);
		SearchPolicy searchPolicy = params.buildSearchPolicy();
		assertThat(searchPolicy, not(nullValue()));
		assertThat(((DefaultSearchDepthPolicy)searchPolicy.getSearchDepthPolicy()).getMaximumDepth(), equalTo(5));
		assertThat(((DefaultSimilarNamesPolicy)searchPolicy.getSimilarNamesPolicy()).getSimilarNamesDistance(), equalTo(2));
		assertThat(((DefaultExactMatchPolicy)searchPolicy.getExactMatchPolicy()).getExactMatchMaxLevel(), equalTo(3));
		assertThat(((DefaultExactMatchPolicy)searchPolicy.getExactMatchPolicy()).isExactMatchForSingleSuccess(), equalTo(true));
	}

	private void assertDefaultSettings(String input, SearchParameters searchParams)
	{
		if(input != null)
		{
			assertThat(searchParams.getInput(), equalTo(input));			
		}else
		{
			assertThat(searchParams.getInput(), is(nullValue()));			
		}
		
		assertFalse(searchParams.isReturnPolicy());
		assertFalse(searchParams.isReturnTokens());
		assertFalse(searchParams.isReturnPossiblesWithExactMatch());
		assertFalse(searchParams.isReturnRejections());
		assertThat(searchParams.getMaxDepth(),equalTo(DefaultSearchDepthPolicy.DEFAULT_MAX_DEPTH));
		assertThat(searchParams.getSimilarNamesDistance(),equalTo(DefaultSimilarNamesPolicy.DEFAULT_SIMILAR_NAMES_DISTANCE));
		assertThat(searchParams.isExactMatchForSingleSuccess(),equalTo(DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_FOR_SINGLE_SUCCESS));
		assertThat(searchParams.getExactMatchMaxLevel(),equalTo(DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_MAX_LEVEL));
	}
}
