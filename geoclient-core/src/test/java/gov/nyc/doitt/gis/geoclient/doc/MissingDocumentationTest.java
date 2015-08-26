package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import org.junit.Test;

public class MissingDocumentationTest
{
	@Test
	public void testIsDocumented()
	{
		assertFalse(new MissingDocumentation("xx").isDocumented());
	}
	
	@Test
	public void testMissingDocumentation()
	{
		String id = "xyz";
		MissingDocumentation md = new MissingDocumentation(id);
		assertEquals(id, md.getId());
		assertEquals(MissingDocumentation.MESSAGE, md.getDescription().joinText(""));
	}

}
