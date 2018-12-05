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
package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.junit.Assert.*;

import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentationTest
{
	private Documentation documentation;

	@BeforeEach
	public void setUp() throws Exception
	{
		this.documentation = new Documentation();
	}

	@Test
	public void testApplicableFunctions()
	{
		List<String> ids = new ArrayList<String>();
		ids.add(Function.F1A);
		ids.add(Function.F3);
		ids.add(Function.F2);
		ids.add(Function.FDG);
		ids.add(Function.F1B);
		ids.add(Function.FBN);
		ids.add("bogus");
		ids.add(Function.FBL);
		ItemDocumentation itemDocumentation = new ItemDocumentation();
		assertTrue(documentation.applicableFunctions(itemDocumentation).isEmpty());
		itemDocumentation.setFunctionNames(ids);
		List<String> result = documentation.applicableFunctions(itemDocumentation);
		assertEquals(Documentation.BLOCKFACE_FUNCTION, result.get(0));
		assertEquals(Documentation.INTERSECTION_FUNCTION, result.get(1));
		assertEquals(Documentation.ADDRESS_AND_PLACE_FUNCTIONS, result.get(2));
		assertEquals(Documentation.BIN_FUNCTION, result.get(3));
		assertEquals(Documentation.BBL_FUNCTION, result.get(4));
		assertEquals(5, result.size());
	}

	@Test
	public void testGetAllFunctionDocumentation()
	{
		FunctionDocumentation address = new FunctionDocumentation();
		FunctionDocumentation bbl = new FunctionDocumentation();
		FunctionDocumentation bin = new FunctionDocumentation();
		FunctionDocumentation blockface = new FunctionDocumentation();
		FunctionDocumentation intersection = new FunctionDocumentation();
		FunctionDocumentation place = new FunctionDocumentation();
		this.documentation.setAddressDocumentation(address);
		this.documentation.setBblDocumentation(bbl);
		this.documentation.setBinDocumentation(bin);
		this.documentation.setBlockfaceDocumentation(blockface);
		this.documentation.setIntersectionDocumentation(intersection);
		this.documentation.setPlaceDocumentation(place);
		List<FunctionDocumentation> result = this.documentation.getAllFunctionDocumentation();
		assertEquals(6, result.size());
		assertSame(address,result.get(0));
		assertSame(bbl,result.get(1));
		assertSame(bin,result.get(2));
		assertSame(blockface,result.get(3));
		assertSame(intersection,result.get(4));
		assertSame(place,result.get(5));
	}

}
