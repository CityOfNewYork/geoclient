package gov.nyc.doitt.gis.geoclient.service.search.request;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;

import org.junit.Test;

public class BlockfaceRequestTest
{

	@Test
	public void testSummarize()
	{
		BlockfaceRequest request = new BlockfaceRequest();
		assertThat(request.summarize(),equalTo("blockface [onStreet=null, crossStreetOne=null, crossStreetTwo=null, borough=null]"));
		request.setOnStreetInputValue(new InputValue(TokenType.ON_STREET, "bway"));
		request.setCrossStreetOneInputValue(new InputValue(TokenType.ON_STREET, "w 100 st"));
		request.setCrossStreetTwoInputValue(new InputValue(TokenType.ON_STREET, "w 101 st"));
		request.setBoroughInputValue(new InputValue(TokenType.BOROUGH_NAME,"manhattan"));
		assertThat(request.summarize(),equalTo("blockface [onStreet=bway, crossStreetOne=w 100 st, crossStreetTwo=w 101 st, borough=manhattan]"));
	}

}
