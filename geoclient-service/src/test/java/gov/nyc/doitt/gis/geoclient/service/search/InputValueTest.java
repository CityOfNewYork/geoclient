package gov.nyc.doitt.gis.geoclient.service.search;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

import org.junit.Test;

public class InputValueTest
{

	@Test
	public void testInputValue_tokenAndMappedValueArg()
	{
		InputValue res = new InputValue(new Token(TokenType.CITY_NAME, "LIC",0,3),"QUEENS");
		assertThat(res.getOriginalValue(), equalTo("LIC"));
		assertThat(res.getTokenType(), equalTo(TokenType.CITY_NAME));
		assertThat(res.getValue(), equalTo("QUEENS"));
		assertThat(res.isMapped(), is(true));
		assertThat(res.isAssigned(), is(false));
		assertThat(res.isParsed(), is(false));
		assertThat(res.isResolved(), is(true));
	}

	@Test
	public void testTokenMapping_tokenAndNullMappedValue()
	{
		InputValue res = new InputValue(new Token(TokenType.CITY_NAME, "LIC",0,3),null);
		assertThat(res.getOriginalValue(), equalTo("LIC"));
		assertThat(res.getTokenType(), equalTo(TokenType.CITY_NAME));
		assertThat(res.getValue(), equalTo("LIC"));
		assertThat(res.isMapped(), is(true));
		assertThat(res.isAssigned(), is(false));
		assertThat(res.isParsed(), is(false));
		assertThat(res.isResolved(), is(false));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTokenMapping_nullTokenAndMappedValueArg()
	{
		Token nullToken = null;
		new InputValue(nullToken,"duh");
	}

	@Test
	public void testInputValue_tokenArg()
	{
		InputValue res = new InputValue(new Token(TokenType.CITY_NAME, "LIC",0,3));
		assertThat(res.getOriginalValue(), equalTo("LIC"));
		assertThat(res.getTokenType(), equalTo(TokenType.CITY_NAME));
		assertThat(res.getValue(), equalTo("LIC"));
		assertThat(res.isMapped(), is(false));
		assertThat(res.isAssigned(), is(false));
		assertThat(res.isParsed(), is(true));
		assertThat(res.isResolved(), is(true));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTokenMapping_nullTokenArg()
	{
		Token nullToken = null;
		new InputValue(nullToken);
	}
	
	@Test
	public void testInputValue_tokenTypeAndAssignedValueArg()
	{
		InputValue res = new InputValue(TokenType.CITY_NAME,"QUEENS");
		assertThat(res.getOriginalValue(), equalTo("QUEENS"));
		assertThat(res.getTokenType(), equalTo(TokenType.CITY_NAME));
		assertThat(res.getValue(), equalTo("QUEENS"));
		assertThat(res.isMapped(), is(false));
		assertThat(res.isAssigned(), is(true));
		assertThat(res.isParsed(), is(false));
		assertThat(res.isResolved(), is(true));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTokenMapping_nullTokenTypeAndAssignedValueArg()
	{
		TokenType nullTokenType = null;
		new InputValue(nullTokenType,"duh");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTokenMapping_tokenTypeAndNullAssignedValueArg()
	{
		new InputValue(TokenType.BIN,null);
	}

}
