package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItemDocumentationSupportTest
{
	private ItemDocumentationSupport support;
	private ItemDocumentation itemDocumentation;

	@Before
	public void setUp() throws Exception
	{
		support = new ItemDocumentationSupport();
		itemDocumentation = new ItemDocumentation();
	}

	@Test
	public void testHasItemDocumentation()
	{
		assertFalse(support.hasItemDocumentation());
		support.setItemDocumentation(itemDocumentation);
		assertFalse(support.hasItemDocumentation());
		itemDocumentation.setId("foo");
		assertTrue(support.hasItemDocumentation());
	}

	@Test
	public void testGetItemDocumentationId()
	{
		assertNull(support.getItemDocumentationId());
		support.setItemDocumentation(itemDocumentation);
		assertNull(support.getItemDocumentationId());
		itemDocumentation.setId("foo");
		assertEquals("foo",support.getItemDocumentationId());
	}

}
