package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TableRowTest
{
	private List<TableData> columns;

	@Before
	public void setUp() throws Exception
	{
		columns = new ArrayList<TableData>();
	}

	@Test
	public void testDefaultConstructor()
	{
		assertEquals(0,new TableRow().getColumns().size());
	}

	@Test
	public void testListOfTableDataConstructor()
	{
		assertSame(columns,new TableRow(columns).getColumns());
	}

	@Test
	public void testToHtml()
	{
		TableRow tr = new TableRow();
		assertEquals("<tr></tr>",tr.toHtml());
		tr.setColumns(columns);
		assertEquals("<tr></tr>",tr.toHtml());
		tr.getColumns().add(new TableData("foo"));
		assertEquals("<tr><td>foo</td></tr>",tr.toHtml());
	}

}
