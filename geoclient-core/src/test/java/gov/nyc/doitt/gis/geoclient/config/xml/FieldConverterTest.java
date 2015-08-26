package gov.nyc.doitt.gis.geoclient.config.xml;

import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_ALIAS; 
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_ID;
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_INPUT;
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_LENGTH;
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_START;
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_TYPE;
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_VALUE_COMPOSITE_TYPE;
import static gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader.XML_FIELD_ATTRIBUTE_WHITESPACE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.function.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class FieldConverterTest
{
	private FieldConverter converter;
	private HierarchicalStreamReader readerMock;
	private UnmarshallingContext contextMock;
	private String id;
	private String start;
	private String length;
	private String type;
	private String isInput;
	private String alias;
	private String whitespace;

	@Before
	public void setUp() throws Exception
	{
		this.converter = new FieldConverter();
		this.readerMock = Mockito.mock(HierarchicalStreamReader.class);
		this.id = "returnCodeId";
		this.start = "12";
		this.length = "6";
		this.type = XML_FIELD_VALUE_COMPOSITE_TYPE;
		this.isInput = "trUe";
		this.alias = "returnCodeAlias";
		this.whitespace = "TRUE";
		contextMock = Mockito.mock(UnmarshallingContext.class);
	}
	
	@Test
	public void testCanConvert()
	{
		assertTrue(this.converter.canConvert(Field.class));
		assertFalse(this.converter.canConvert(Object.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMarshal()
	{
		this.converter.marshal(null, null, null);
	}

	@Test
	public void testUnmarshal_compositeType()
	{
		prepareReaderMock(this.type);
		Field result = (Field) this.converter.unmarshal(readerMock, contextMock);
		assertFieldResult(true, result);
	}

	@Test
	public void testUnmarshal_regularType()
	{
		prepareReaderMock("REG");
		Field result = (Field) this.converter.unmarshal(readerMock, contextMock);
		assertFieldResult(false, result);
	}

	@Test
	public void testUnmarshal_nullType()
	{
		prepareReaderMock(null);
		Field result = (Field) this.converter.unmarshal(readerMock, contextMock);
		assertFieldResult(false, result);
	}

	private void prepareReaderMock(String typeToUse)
	{
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_ID)).thenReturn(id);
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_START)).thenReturn(start);
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_LENGTH)).thenReturn(this.length);
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_TYPE)).thenReturn(typeToUse);
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_INPUT)).thenReturn(this.isInput);
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_ALIAS)).thenReturn(this.alias);
		Mockito.when(this.readerMock.getAttribute(XML_FIELD_ATTRIBUTE_WHITESPACE)).thenReturn(this.whitespace);
	}

	private void assertFieldResult(boolean isComposite, Field field)
	{
		assertEquals(this.id, field.getId());
		Integer startInt = Integer.valueOf(start);
		assertEquals(new Integer(startInt - 1), field.getStart());
		Integer lengthInt = Integer.valueOf(this.length);
		assertEquals(lengthInt, field.getLength());
		assertEquals(isComposite, field.isComposite());
		boolean impBool = Boolean.parseBoolean(this.isInput);
		assertEquals(impBool, field.isInput());
		assertEquals(this.alias, field.getAlias());
		boolean whitespaceBool = Boolean.parseBoolean(this.whitespace);
		assertEquals(whitespaceBool, field.isWhitespaceSignificant());
	}
	
}
