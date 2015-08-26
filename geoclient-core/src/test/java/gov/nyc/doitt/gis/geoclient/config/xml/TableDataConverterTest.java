package gov.nyc.doitt.gis.geoclient.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nyc.doitt.gis.geoclient.doc.TableData;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class TableDataConverterTest
{
	private TableDataConverter converter;
	private HierarchicalStreamReader readerMock;
	private String text;

	@Before
	public void setUp()
	{
		converter = new TableDataConverter();
		readerMock = mock(HierarchicalStreamReader.class);
		text = "I'm too texty for my shirt!";
	}

	@Test
	public void testCanConvert()
	{
		assertTrue(this.converter.canConvert(TableData.class));
		assertFalse(this.converter.canConvert(String.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMarshal()
	{
		this.converter.marshal(null, null, null);
	}

	@Test
	public void testUnmarshal_withColspan()
	{
		setUpReaderMock("2", text, null, null, DocumentationXmlReader.XML_TD_ELEMENT);
		TableData td = (TableData) this.converter.unmarshal(readerMock, null);
		assertEquals(2, td.getColspan());
		assertEquals(text, td.getText());
		assertNull(td.getFootnote());
		assertFalse(td.isHeader());
	}

	@Test
	public void testUnmarshal_isHeader()
	{
		setUpReaderMock(null, text, null, null, DocumentationXmlReader.XML_TH_ELEMENT);
		TableData td = (TableData) this.converter.unmarshal(readerMock, null);
		assertEquals(1, td.getColspan());
		assertEquals(text, td.getText());
		assertNull(td.getFootnote());
		assertTrue(td.isHeader());
	}

	@Test
	public void testUnmarshal_textWithFootnote()
	{
		setUpReaderMock(null, text, "*", "8", DocumentationXmlReader.XML_TD_ELEMENT);
		TableData td = (TableData) this.converter.unmarshal(readerMock, null);
		assertEquals(1, td.getColspan());
		assertEquals(text, td.getText());
		assertEquals("*", td.getFootnote().getSymbol());
		assertEquals(8, td.getFootnote().getPosition());
		assertFalse(td.isHeader());
	}

	@Test
	public void testUnmarshal_footnoteOnly()
	{
		setUpReaderMock(null, null, "*", "0", DocumentationXmlReader.XML_TD_ELEMENT);
		TableData td = (TableData) this.converter.unmarshal(readerMock, null);
		assertEquals(1, td.getColspan());
		assertEquals("", td.getText());
		assertEquals("*", td.getFootnote().getSymbol());
		assertEquals(0, td.getFootnote().getPosition());
		assertFalse(td.isHeader());
	}

	@Test
	public void testUnmarshal_emptyElement()
	{
		setUpReaderMock(null, null, null, null, DocumentationXmlReader.XML_TD_ELEMENT);
		TableData td = (TableData) this.converter.unmarshal(readerMock, null);
		assertEquals(1, td.getColspan());
		assertEquals("", td.getText());
		assertNull(td.getFootnote());
		assertFalse(td.isHeader());
	}

	private void setUpReaderMock(String colspan, String text, String footnote, String footnotePosition, String nodeName)
	{
		when(this.readerMock.getAttribute(DocumentationXmlReader.XML_TD_ATTRIBUTE_COLSPAN)).thenReturn(colspan);

		if (text != null && footnote != null)
		{
			// With Footnote
			// <td><sup position="2">*</sup>some text</td>
			when(this.readerMock.getValue()).thenReturn(footnote).thenReturn(text);
			when(this.readerMock.hasMoreChildren()).thenReturn(true);
			when(this.readerMock.getAttribute(DocumentationXmlReader.XML_FOOTNOTE_ATTRIBUTE_POSITION)).thenReturn(footnotePosition);

		} else if (text != null)
		{
			// <td>some text</td>
			when(this.readerMock.getValue()).thenReturn(text);
			when(this.readerMock.hasMoreChildren()).thenReturn(false);
		} else if (footnote != null)
		{
			// With Footnote - no text
			// <td><sup position="2">*</sup></td>
			when(this.readerMock.getValue()).thenReturn(footnote).thenReturn("");
			when(this.readerMock.hasMoreChildren()).thenReturn(true);
			when(this.readerMock.getAttribute(DocumentationXmlReader.XML_FOOTNOTE_ATTRIBUTE_POSITION)).thenReturn(footnotePosition);
		} else
		{
			// <td></td>
			when(this.readerMock.getValue()).thenReturn("");
			when(this.readerMock.hasMoreChildren()).thenReturn(false);
		}

		when(this.readerMock.getNodeName()).thenReturn(nodeName);
	}
}
