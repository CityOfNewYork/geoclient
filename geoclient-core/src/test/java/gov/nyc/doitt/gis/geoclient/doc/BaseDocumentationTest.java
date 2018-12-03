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
import gov.nyc.doitt.gis.geoclient.doc.BaseDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.Description;

import org.junit.Test;

public class BaseDocumentationTest
{
	@Test
	public void testIsDocumented()
	{
		BaseDocumentation bd = new BaseDocumentation();
		assertFalse(bd.isDocumented());
		bd.setId("xxx");
		assertTrue(bd.isDocumented());
	}
	
	
	@Test
	public void testCompareTo()
	{
		BaseDocumentation abc = new BaseDocumentation("abc",null);
		BaseDocumentation def = new BaseDocumentation("def",null);
		assertEquals(abc.getId().compareTo(def.getId()),abc.compareTo(def));
		assertEquals(def.getId().compareTo(abc.getId()),def.compareTo(abc));		
		BaseDocumentation abcAlso = new BaseDocumentation("abc",new Description("duh"));
		assertEquals(0, abc.compareTo(abcAlso));
		assertEquals(0, abcAlso.compareTo(abc));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCompareToThrowsNullPointerExceptionBecauseIdIsNotSet()
	{
		BaseDocumentation bd = new BaseDocumentation("abc",null);
		bd.compareTo(new BaseDocumentation());
	}
	
	@Test
	public void testSummarize()
	{
		Description description = new Description("xyz");
		BaseDocumentation fd = new BaseDocumentation();
		assertTrue(fd.summarize(2).isEmpty());
		fd.setDescription(description);
		assertEquals(description.summarize(2), fd.summarize(2));
	}

	@Test
	public void testJoinDescriptionText()
	{
		Description description = new Description("xxx");
		BaseDocumentation fd = new BaseDocumentation();
		assertTrue(fd.joinDescriptionText(", ").isEmpty());
		fd.setDescription(description);
		assertEquals(description.joinText(" "), fd.joinDescriptionText(" "));
	}
	
	@Test
	public void testHasText()
	{
		BaseDocumentation bd = new BaseDocumentation();
		assertFalse(bd.hasText());
		Description desc = new Description("xx");
		assertTrue(desc.hasText());
		bd.setDescription(desc);
		assertTrue(bd.hasText());
	}
	
	@Test
	public void testConstructorWithArgs()
	{
		Description desc = new Description();
		BaseDocumentation bd = new BaseDocumentation("12", desc);
		assertEquals("12",bd.getId());
		assertSame(desc,bd.getDescription());
	}

	@Test
	public void testDefaultConstructor()
	{
		BaseDocumentation bd = new BaseDocumentation();
		assertNull(bd.getId());
		assertNull(bd.getDescription());
	}

}
