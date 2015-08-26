package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*; 
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class SearchIdTest
{

	@Test
	public void testNext()
	{
		String prefix = "xyz";
		SearchId id = new SearchId(prefix);
    // FIXME This fails to compile unpredictably leading me to believe that there
    // is a classpath issue
		//assertThat(id.next(),org.hamcrest.CoreMatchers.containsString(prefix));
		assertTrue(id.next().contains(prefix));
		assertThat(id.next(),not(equalTo(id.next())));
	}

}
