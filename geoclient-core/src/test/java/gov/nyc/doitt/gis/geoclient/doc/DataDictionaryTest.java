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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataDictionaryTest
{
	private ItemDocumentation itemDocumentation;
	private List<ItemDocumentation> items;
	private List<String> aliases;
	private DataDictionary dataDictionary;

	@BeforeEach
	public void setUp() throws Exception
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("hippo");
		this.aliases.add("froyo");
		this.itemDocumentation = new ItemDocumentation("cujo");
		this.items = new ArrayList<ItemDocumentation>();
		this.dataDictionary = new DataDictionary(items);
	}

	@Test
	public void testConstructor()
	{
		assertSame(this.items, this.dataDictionary.getItems());
	}

	@Test
	public void testFindItem()
	{
		assertNull(this.dataDictionary.findItem(itemDocumentation.getId()));
		this.items.add(itemDocumentation);
		assertSame(this.itemDocumentation, this.dataDictionary.findItem(itemDocumentation.getId()));
		assertNull(this.dataDictionary.findItem(this.aliases.get(0)));
		this.itemDocumentation.setAliases(aliases);
		assertSame(this.itemDocumentation, this.dataDictionary.findItem(this.aliases.get(0)));
	}

}
