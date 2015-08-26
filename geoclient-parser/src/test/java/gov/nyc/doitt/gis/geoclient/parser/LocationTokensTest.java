package gov.nyc.doitt.gis.geoclient.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LocationTokensTest
{
	private Input input;
	private List<Chunk> chunks;
	private LocationTokens locationTokens;
	private Token houseNumberToken;
	private Token streetNameToken;
	private Token boroughToken;

	@Before
	public void setUp()
	{
		input = new Input("1", "22 Broadway, Manhattan");
		chunks = new ArrayList<>();
		Chunk cityBoroughChunk = new Chunk(ChunkType.COUNTY,"22 Broadway, Manhattan");
		boroughToken = new Token(TokenType.BOROUGH_NAME,"Manhattan",13,22);
		cityBoroughChunk.add(boroughToken);
		chunks.add(cityBoroughChunk);
		Chunk addressChunk = new Chunk(ChunkType.ADDRESS, "22 Broadway");
		houseNumberToken = new Token(TokenType.HOUSE_NUMBER,"22",0,2);
		addressChunk.add(houseNumberToken);
		streetNameToken = new Token(TokenType.STREET_NAME,"Broadway",3,11);
		addressChunk.add(streetNameToken);
		chunks.add(addressChunk);
		locationTokens = new LocationTokens(input, chunks);
	}
	
	@Test
	public void testTokensOfType()
	{
		List<Token> tokenList = locationTokens.tokensOfType(TokenType.AND);
		assertThat(tokenList.size(), equalTo(0));
		tokenList = locationTokens.tokensOfType(TokenType.HOUSE_NUMBER);
		assertThat(tokenList.size(), equalTo(1));
		assertThat(tokenList.contains(houseNumberToken), is(true));
		tokenList = locationTokens.tokensOfType(TokenType.STREET_NAME); 
		assertThat(tokenList.size(), equalTo(1));
		assertThat(tokenList.contains(streetNameToken), is(true));
		tokenList = locationTokens.tokensOfType(TokenType.BOROUGH_NAME);
		assertThat(tokenList.size(), equalTo(1));
		assertThat(tokenList.contains(boroughToken), is(true));
		tokenList = locationTokens.tokensOfType(TokenType.HOUSE_NUMBER, TokenType.STREET_NAME);
		assertThat(tokenList.size(), equalTo(2));
		assertThat(tokenList.contains(houseNumberToken), is(true));
		assertThat(tokenList.contains(streetNameToken), is(true));
	}
	
	@Test
	public void testFirstTokenOfType()
	{
		assertThat(locationTokens.firstTokenOfType(TokenType.AND), is(nullValue()));
		assertThat(locationTokens.firstTokenOfType(TokenType.STREET_NAME), equalTo(streetNameToken));
	}
	
	@Test
	public void testParseSummary()
	{
		assertThat(locationTokens.parseSummary(), equalTo("ADDRESS{ HOUSE_NUMBER[22], STREET_NAME[Broadway] }, COUNTY{ BOROUGH_NAME[Manhattan] }"));
	}
	
	@Test
	public void testConstructor()
	{
		assertThat(locationTokens.getInput(),sameInstance(input));
		assertThat(locationTokens.getChunks(),sameInstance(chunks));
	}
}
