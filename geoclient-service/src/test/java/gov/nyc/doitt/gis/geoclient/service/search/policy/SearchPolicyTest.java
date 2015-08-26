package gov.nyc.doitt.gis.geoclient.service.search.policy;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Test;

public class SearchPolicyTest
{
	@Test
	public void testDefaultSettings()
	{
		SearchPolicy searchPolicy = new SearchPolicy();
		assertThat(searchPolicy.getExactMatchPolicy(), instanceOf(DefaultExactMatchPolicy.class));
		assertThat(searchPolicy.getSearchDepthPolicy(), instanceOf(DefaultSearchDepthPolicy.class));
		assertThat(searchPolicy.getSimilarNamesPolicy(), instanceOf(DefaultSimilarNamesPolicy.class));
	}

	
}
