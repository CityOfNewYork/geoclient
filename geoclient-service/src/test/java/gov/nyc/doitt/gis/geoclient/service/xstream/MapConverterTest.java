package gov.nyc.doitt.gis.geoclient.service.xstream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import gov.nyc.doitt.gis.geoclient.service.xstream.MapConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MapConverterTest
{
	private HierarchicalStreamWriter writerMock;
	private MapConverter mapConverter;
	private Map<String, Object> data;

	@Before
	public void setUp() throws Exception
	{
		this.writerMock = Mockito.mock(HierarchicalStreamWriter.class);
		this.mapConverter = new MapConverter();
		this.data = new HashMap<String, Object>();
		this.data.put("ONE", 1.0);
		this.data.put("TWO", "2");
		this.data.put("THREE", null);
		
	}

	@Test
	public void testCanConvert()
	{
		assertTrue(this.mapConverter.canConvert(Map.class));
		assertTrue(this.mapConverter.canConvert(SortedMap.class));
		assertTrue(this.mapConverter.canConvert(HashMap.class));
		assertTrue(this.mapConverter.canConvert(TreeMap.class));
		assertFalse(this.mapConverter.canConvert(String.class));
	}

	@Test
	public void testMarshal()
	{
		this.mapConverter.marshal(this.data, this.writerMock, null);
		Mockito.verify(this.writerMock).startNode("ONE");
		Mockito.verify(this.writerMock).setValue("1.0");
		Mockito.verify(this.writerMock).startNode("TWO");
		Mockito.verify(this.writerMock).setValue("2");
		Mockito.verify(this.writerMock).startNode("THREE");
		Mockito.verify(this.writerMock).setValue(null);
		Mockito.verify(this.writerMock, Mockito.times(3)).endNode();
	}

	@Test
	public void testMarshalNestedMap()
	{
		Map<String, Object> outerMap = new HashMap<String, Object>();
		outerMap.put("Foo", this.data);
		this.mapConverter.marshal(outerMap, this.writerMock, null);
		Mockito.verify(this.writerMock).startNode("Foo");
		Mockito.verify(this.writerMock).startNode("ONE");
		Mockito.verify(this.writerMock).setValue("1.0");
		Mockito.verify(this.writerMock).startNode("TWO");
		Mockito.verify(this.writerMock).setValue("2");
		Mockito.verify(this.writerMock).startNode("THREE");
		Mockito.verify(this.writerMock).setValue(null);
		Mockito.verify(this.writerMock, Mockito.times(4)).endNode();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmarshal()
	{
		this.mapConverter.unmarshal(null, null);
	}

}
