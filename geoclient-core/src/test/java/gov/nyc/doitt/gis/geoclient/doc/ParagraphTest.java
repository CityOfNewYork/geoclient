package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParagraphTest
{
	@Test
	public void testHasText()
	{
		Paragraph p = new Paragraph();
		assertFalse(p.hasText());
		p.setText("");
		assertFalse(p.hasText());
		p.setText("x");
		assertTrue(p.hasText());
	}

	@Test
	public void testDefaultConstructor()
	{
		assertNull(new Paragraph().getText());
	}

	@Test
	public void testConstructorWithArguments()
	{
		assertEquals("foo",new Paragraph("foo").getText());
	}

	@Test
	public void testToHtml()
	{
		assertEquals("<p>foo</p>",new Paragraph("foo").toHtml());
		assertEquals("<p></p>",new Paragraph().toHtml());
	}

}
