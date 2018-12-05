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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MissingDocumentationTest
{
	@Test
	public void testIsDocumented()
	{
		assertFalse(new MissingDocumentation("xx").isDocumented());
	}
	
	@Test
	public void testMissingDocumentation()
	{
		String id = "xyz";
		MissingDocumentation md = new MissingDocumentation(id);
		assertEquals(id, md.getId());
		assertEquals(MissingDocumentation.MESSAGE, md.getDescription().joinText(""));
	}

}
