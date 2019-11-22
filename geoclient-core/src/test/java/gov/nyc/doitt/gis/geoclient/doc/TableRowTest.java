/*
 * Copyright 2013-2019 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableRowTest
{
	private List<TableData> columns;

	@BeforeEach
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
