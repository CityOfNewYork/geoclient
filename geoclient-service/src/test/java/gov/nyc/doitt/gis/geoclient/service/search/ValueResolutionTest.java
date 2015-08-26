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
