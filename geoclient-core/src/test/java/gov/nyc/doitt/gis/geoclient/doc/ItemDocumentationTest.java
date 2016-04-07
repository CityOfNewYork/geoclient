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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ItemDocumentationTest
{
	@Test
	public void testCopyWithDisplayName()
	{
		ItemDocumentation iDoc1 = new ItemDocumentation();
		assertNull(iDoc1.getId());
		assertNull(iDoc1.getDisplayName());
		iDoc1.setId("aaa");
		assertEquals("aaa", iDoc1.getDisplayName());
		iDoc1.setDisplayName("bbb");
		assertEquals("bbb", iDoc1.getDisplayName());
		assertEquals("aaa", iDoc1.getId());
		List<String> listOfStrings = new ArrayList<String>();
		iDoc1.setAliases(listOfStrings);
		iDoc1.setFunctionNames(listOfStrings);
		iDoc1.setSeeAlso(listOfStrings);
		Description description = new Description();
		iDoc1.setDescription(description);
		List<Table> listOfTables = new ArrayList<Table>();
		iDoc1.setTables(listOfTables);
		String format = "fff";
		iDoc1.setFormat(format);
		ItemDocumentation iDoc2 = iDoc1.copyWithDisplayName("ccc");
		assertEquals("aaa", iDoc2.getId());
		assertEquals("ccc", iDoc2.getDisplayName());
		assertSame(listOfStrings,iDoc2.getAliases());
		assertSame(listOfStrings,iDoc2.getFunctionNames());
		assertSame(listOfStrings,iDoc2.getSeeAlso());
		assertSame(description,iDoc2.getDescription());
		assertSame(listOfTables,iDoc2.getTables());
		assertSame(format,iDoc2.getFormat());
	}	
	
	@Test
	public void testDocuments()
	{
		String id = "abc";
		ItemDocumentation itemDocumentation = new ItemDocumentation();
		assertFalse(itemDocumentation.documents(id));
		itemDocumentation.setId(id);
		assertTrue(itemDocumentation.documents(id));
		itemDocumentation.setId(null);
		List<String> aliases = new ArrayList<String>();
		itemDocumentation.setAliases(aliases);
		assertFalse(itemDocumentation.documents(id));
		aliases.add(id);
		assertTrue(itemDocumentation.documents(id));
	}

	@Test
	public void testDefaultConstructor()
	{
		ItemDocumentation iDoc = new ItemDocumentation();
		assertNull(iDoc.getDescription());
		assertNull(iDoc.getFormat());
		assertNull(iDoc.getFunctionNames());
		assertNull(iDoc.getId());
		assertNull(iDoc.getTables());
	}

	@Test
	public void testOneArgumentConstructor()
	{
		String id = "abc";
		ItemDocumentation iDoc = new ItemDocumentation(id);
		assertNotNull(iDoc.getDescription());
		assertNull(iDoc.getFormat());
		assertNull(iDoc.getFunctionNames());
		assertEquals(id,iDoc.getId());
		assertNull(iDoc.getTables());
	}

}
