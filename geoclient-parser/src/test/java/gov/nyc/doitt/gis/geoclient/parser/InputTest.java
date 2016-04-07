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
package gov.nyc.doitt.gis.geoclient.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class InputTest
{

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_idNull()
	{
		new Input(null,"value");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_idEmpty()
	{
		new Input("","value");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_valueNull()
	{
		new Input("id",null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_valueEmpty()
	{
		new Input("id","");
	}

	@Test
	public void testConstructor()
	{
		String id = "123";
		String value = " $abc";
		Input input = new Input(id, value);
		assertThat(input.getId(), is(id));
		assertThat(input.getValue(), is("abc"));
		assertThat(input.getUnsanitizedValue(), is(value));
	}

}
