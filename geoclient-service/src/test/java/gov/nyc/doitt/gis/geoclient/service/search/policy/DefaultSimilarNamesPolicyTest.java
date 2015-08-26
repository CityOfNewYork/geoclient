package gov.nyc.doitt.gis.geoclient.service.search.policy;

import static org.junit.Assert.*; 
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class DefaultSimilarNamesPolicyTest
{
	private DefaultSimilarNamesPolicy policy;

	@Before
	public void setUp() throws Exception
	{
		this.policy = new DefaultSimilarNamesPolicy();
	}

	@Test
	public void testIsSimilarName()
	{
		assertTrue(policy.isSimilarName("BRO", "BROADWAY"));
		assertTrue(policy.isSimilarName("BRODWAY", "BRODWAY"));
		assertTrue(policy.isSimilarName("BRODWAY", "BROADWAY"));
		assertTrue(policy.isSimilarName("BROAD", "BROADWAY"));
		assertTrue(policy.isSimilarName("BRADWAY", "BROADWAY"));
		assertTrue(policy.isSimilarName("ROADWAY", "BROADWAY"));
		assertTrue(policy.isSimilarName("100 ST", "WEST 100 STREET"));
		assertTrue(policy.isSimilarName("100 ST", "EAST 100 STREET"));
		assertTrue(policy.isSimilarName("WEST 100 AVE", "WEST 100 STREET"));
		assertTrue(policy.isSimilarName("EasTeRN PKWY", "EASTERN PARKWAY"));
		assertTrue(policy.isSimilarName("BROAD STREET", "BROADWAY"));
		assertTrue(policy.isSimilarName("BRODWAY", "BROAD STREET"));
		assertTrue(policy.isSimilarName("BRODWAY", "BROWN BOULEVARD"));
		assertFalse(policy.isSimilarName("BRODWAY", "WB BRIDGE APPROACH"));
	}

	@Test
	public void testClean()
	{
		assertThat(policy.clean(""), equalTo(""));
		assertThat(policy.clean(" "), equalTo(" "));
		assertThat(policy.clean("a"), equalTo("A"));
		assertThat(policy.clean("a "), equalTo("A"));
		assertThat(policy.clean(" a "), equalTo("A"));
		assertThat(policy.clean(" ave a "), equalTo("A"));
		assertThat(policy.clean("St Marks"), equalTo("MARKS"));
		// TODO more tests
	}

}
