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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
