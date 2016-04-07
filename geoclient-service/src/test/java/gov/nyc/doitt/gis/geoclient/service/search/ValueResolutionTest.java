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
package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

import org.junit.Test;

public class ValueResolutionTest
{

	@Test
	public void testAdd()
	{
		ValueResolution res = new ValueResolution();
		assertThat(res.totalCount(),equalTo(0));
		assertThat(res.resolvedCount(),equalTo(0));
		assertThat(res.unresolvedCount(),equalTo(0));
		assertTrue(res.resolved().isEmpty());
		InputValue inputResolved = new InputValue(TokenType.AND,"and");
		assertTrue(inputResolved.isResolved());
		InputValue inputUnresolved = new InputValue(new Token(TokenType.BETWEEN,"bet",0,3),null);
		assertFalse(inputUnresolved.isResolved());
		res.add(inputResolved);
		assertThat(res.totalCount(),equalTo(1));
		assertThat(res.resolvedCount(),equalTo(1));
		assertThat(res.unresolvedCount(),equalTo(0));
		assertTrue(res.resolved().contains(inputResolved));
		res.add(inputUnresolved);
		assertThat(res.totalCount(),equalTo(2));
		assertThat(res.resolvedCount(),equalTo(1));
		assertThat(res.unresolvedCount(),equalTo(1));
		assertTrue(res.resolved().contains(inputResolved));
		assertFalse(res.resolved().contains(inputUnresolved));
		assertThat(res.resolvedValue(0), sameInstance(inputResolved));
		assertThat(res.unresolvedValue(0), sameInstance(inputUnresolved));
	}

}
