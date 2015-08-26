package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TableDataTest
{
	private Footnote footnote = new Footnote("*",5);

	@Test
	public void testDefaultConstructor()
	{
		TableData td = new TableData();
		assertEquals(1, td.getColspan());
		assertNull(td.getFootnote());
		assertNull(td.getText());
		assertFalse(td.isHeader());
	}

	@Test
	public void testOneArgConstructor()
	{
		TableData td = new TableData("blah");
		assertEquals(1, td.getColspan());
		assertNull(td.getFootnote());
		assertEquals("blah",td.getText());
		assertFalse(td.isHeader());
	}

	@Test
	public void testTwoArgConstructor()
	{
		TableData td = new TableData("blah",2);
		assertEquals(2, td.getColspan());
		assertNull(td.getFootnote());
		assertEquals("blah",td.getText());
		assertFalse(td.isHeader());
	}

	@Test
	public void testFourArgConstructor()
	{
		TableData td = new TableData("blah",2,true,footnote);
		assertEquals(2, td.getColspan());
		assertSame(footnote, td.getFootnote());
		assertEquals("blah",td.getText());
		assertTrue(td.isHeader());
	}

	@Test
	public void testToHtml()
	{
		TableData td = new TableData();
		assertEquals("<td></td>",td.toHtml());
		td.setHeader(true);
		assertEquals("<th></th>",td.toHtml());
		td.setHeader(false);
		td.setColspan(2);
		assertEquals("<td colspan=\"2\"></td>",td.toHtml());
		td.setColspan(1);
		td.setText("blah note");
		assertEquals("<td>blah note</td>",td.toHtml());
		td.setFootnote(footnote);
		assertEquals("<td>blah <sup>*</sup>note</td>",td.toHtml());
	}

}
