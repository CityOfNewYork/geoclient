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
