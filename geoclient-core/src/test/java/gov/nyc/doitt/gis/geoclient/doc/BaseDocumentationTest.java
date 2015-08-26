package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.doc.BaseDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.Description;

import org.junit.Test;

public class BaseDocumentationTest
{
	@Test
	public void testIsDocumented()
	{
		BaseDocumentation bd = new BaseDocumentation();
		assertFalse(bd.isDocumented());
		bd.setId("xxx");
		assertTrue(bd.isDocumented());
	}
	
	
	@Test
	public void testCompareTo()
	{
		BaseDocumentation abc = new BaseDocumentation("abc",null);
		BaseDocumentation def = new BaseDocumentation("def",null);
		assertEquals(abc.getId().compareTo(def.getId()),abc.compareTo(def));
		assertEquals(def.getId().compareTo(abc.getId()),def.compareTo(abc));		
		BaseDocumentation abcAlso = new BaseDocumentation("abc",new Description("duh"));
		assertEquals(0, abc.compareTo(abcAlso));
		assertEquals(0, abcAlso.compareTo(abc));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCompareToThrowsNullPointerExceptionBecauseIdIsNotSet()
	{
		BaseDocumentation bd = new BaseDocumentation("abc",null);
		bd.compareTo(new BaseDocumentation());
	}
	
	@Test
	public void testSummarize()
	{
		Description description = new Description("xyz");
		BaseDocumentation fd = new BaseDocumentation();
		assertTrue(fd.summarize(2).isEmpty());
		fd.setDescription(description);
		assertEquals(description.summarize(2), fd.summarize(2));
	}

	@Test
	public void testJoinDescriptionText()
	{
		Description description = new Description("xxx");
		BaseDocumentation fd = new BaseDocumentation();
		assertTrue(fd.joinDescriptionText(", ").isEmpty());
		fd.setDescription(description);
		assertEquals(description.joinText(" "), fd.joinDescriptionText(" "));
	}
	
	@Test
	public void testHasText()
	{
		BaseDocumentation bd = new BaseDocumentation();
		assertFalse(bd.hasText());
		Description desc = new Description("xx");
		assertTrue(desc.hasText());
		bd.setDescription(desc);
		assertTrue(bd.hasText());
	}
	
	@Test
	public void testConstructorWithArgs()
	{
		Description desc = new Description();
		BaseDocumentation bd = new BaseDocumentation("12", desc);
		assertEquals("12",bd.getId());
		assertSame(desc,bd.getDescription());
	}

	@Test
	public void testDefaultConstructor()
	{
		BaseDocumentation bd = new BaseDocumentation();
		assertNull(bd.getId());
		assertNull(bd.getDescription());
	}

}
