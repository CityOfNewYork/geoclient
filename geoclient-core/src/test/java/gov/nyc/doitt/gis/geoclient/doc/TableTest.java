/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TableTest
{
	private List<TableRow> rows;
	private TableRow rowOne;
	private TableRow rowTwo;
	private String id;
	private String caption;

	@Before
	public void setUp() throws Exception
	{
		id = "table_a";
		caption = "This is a table";
		rows = new ArrayList<TableRow>();
		List<TableData> cellsOne = new ArrayList<TableData>();
		cellsOne.add(new TableData("a"));
		cellsOne.add(new TableData("b"));
		cellsOne.add(new TableData("c"));
		rowOne = new TableRow(cellsOne);
		List<TableData> cellsTwo = new ArrayList<TableData>();
		cellsTwo.add(new TableData("a"));
		cellsTwo.add(new TableData("b"));
		cellsTwo.add(new TableData("c"));
		rowTwo = new TableRow(cellsTwo);
	}

	@Test
	public void testDefaultConstructor()
	{
		Table table = new Table();
		assertNull(table.getId());
		assertNull(table.getCaption());
		assertNotNull(table.getRows());
		assertEquals(0, table.getRows().size());
	}

	@Test
	public void testConstructorWithArguments()
	{
		Table table = new Table(id, rows, caption);
		assertEquals(id,table.getId());
		assertEquals(caption,table.getCaption());
		assertSame(rows,table.getRows());
	}

	@Test
	public void testToHtml()
	{
		assertEquals("<table>\n</table>",new Table().toHtml());
		assertEquals("<table id=\"foo\">\n</table>",new Table("foo",rows,null).toHtml());
		assertEquals("<table id=\"foo\">\n<caption>This is a table</caption>\n</table>",new Table("foo",rows,caption).toHtml());
		rows.add(rowOne);
		String tr1 = rowOne.toHtml() + "\n";
		assertEquals("<table id=\"foo\">\n<caption>This is a table</caption>\n" + tr1 + "</table>",new Table("foo",rows,caption).toHtml());
		rows.add(rowTwo);
		String tr2 = rowTwo.toHtml() + "\n";
		assertEquals("<table id=\"foo\">\n<caption>This is a table</caption>\n" + tr1 + tr2 + "</table>",new Table("foo",rows,caption).toHtml());
	}

}
