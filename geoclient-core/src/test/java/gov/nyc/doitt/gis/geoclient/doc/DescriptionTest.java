package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class DescriptionTest
{
	private List<Paragraph> paragraphs = new ArrayList<Paragraph>();
	
	@After
	public void teaDown()
	{
		this.paragraphs.clear();
	}
	
	@Test
	public void testHasText()
	{
		Description desc = new Description();
		desc.setParagraphs(null);
		assertFalse(desc.hasText());
		desc.setParagraphs(paragraphs);
		assertFalse(desc.hasText());	
		Paragraph p = new Paragraph();
		assertFalse(p.hasText());
		paragraphs.add(p);
		assertFalse(desc.hasText());	
		p.setText("x");
		assertTrue(p.hasText());
		assertTrue(desc.hasText());	
	}

	@Test
	public void testSummarize()
	{
		Description desc = new Description();
		assertTrue(desc.summarize(4).isEmpty());
		desc.getParagraphs().add(new Paragraph("12"));
		assertEquals("12",desc.summarize(4));
		desc.getParagraphs().add(new Paragraph("3456"));
		assertEquals("12 3",desc.summarize(4));
	}	
	
	@Test
	public void testJoinText()
	{
		Description desc = new Description();
		assertTrue(desc.joinText(", ").isEmpty());
		desc.getParagraphs().add(new Paragraph("one"));
		assertEquals("one", desc.joinText(", "));
		desc.getParagraphs().add(new Paragraph("two"));
		assertEquals("one, two", desc.joinText(", "));
	}
	
	@Test
	public void testJoinTextWithNullParagraphsList()
	{
		Description desc = new Description();
		desc.setParagraphs(null);
		assertTrue(desc.joinText(", ").isEmpty());
	}
	
	@Test
	public void testNoArgConstructor()
	{
		Description desc = new Description();
		assertNotNull(desc.getParagraphs());
		assertTrue(desc.getParagraphs().isEmpty());
	}

	@Test
	public void testStringConstructor()
	{
		String txt = "Pass the beans";
		Description desc = new Description(txt);
		assertNotNull(desc.getParagraphs());
		assertTrue(desc.getParagraphs().size()==1);
		assertEquals(txt, desc.getParagraphs().get(0).getText());
	}

	@Test
	public void testListOfParagraphsConstructor()
	{
		Description desc = new Description(paragraphs);
		assertSame(this.paragraphs,desc.getParagraphs());
	}

	@Test
	public void testToHtml()
	{
		Description desc = new Description(paragraphs);
		assertEquals("",desc.toHtml());
		Paragraph paragraphOne = new Paragraph("Blah, blah, blah");
		this.paragraphs.add(paragraphOne);
		assertEquals("<p>Blah, blah, blah</p>",desc.toHtml());
		Paragraph paragraphTwo = new Paragraph("Once upon a time");
		this.paragraphs.add(paragraphTwo);
		assertEquals("<p>Blah, blah, blah</p><p>Once upon a time</p>",desc.toHtml());
	}

}
