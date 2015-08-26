package gov.nyc.doitt.gis.geoclient.service.search;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CountyResolverTest
{
	@SuppressWarnings("serial")
	private Map<String, String> boroughNames = new HashMap<String, String>()
	{{
		put("Bk","BROOKLYN");	
	}};

	private CountyResolver boroughResolver;
	private LocationTokens locationTokens;
	private Chunk chunk;
	private Token resolvableBoroughNameToken;
	private Token unresolvableBoroughNameToken;

	@Before
	public void setUp() throws Exception
	{
		boroughResolver = new CountyResolver(boroughNames);
		chunk = new Chunk(ChunkType.COUNTY, "countyChunk");
		List<Chunk> chunks = new ArrayList<>();
		chunks.add(chunk);
		locationTokens = new LocationTokens(new Input("abc","def"), chunks);
		resolvableBoroughNameToken = new Token(TokenType.BOROUGH_NAME, "BK", 4, 6);
		unresolvableBoroughNameToken = new Token(TokenType.BOROUGH_NAME, "foo", 0, 3);
	}

	@Test
	public void testResolve_emptyLocationTokensContainsFiveBoroughs()
	{
		ValueResolution res = boroughResolver.resolve(locationTokens);
		assertThat(res, not(nullValue()));
		assertThat(res.unresolvedCount(),equalTo(0));
		assertThat(res.resolvedCount(),equalTo(5));
		assertThat(res.totalCount(),equalTo(5));
		assertTrue(res.resolved().contains(CountyResolver.MANHATTAN));
		assertTrue(res.resolved().contains(CountyResolver.BRONX));
		assertTrue(res.resolved().contains(CountyResolver.BROOKLYN));
		assertTrue(res.resolved().contains(CountyResolver.QUEENS));
		assertTrue(res.resolved().contains(CountyResolver.STATEN_ISLAND));
	}

	@Test
	public void testResolve_doesNotCreateDefaultBoroughsWhenZipIsAvailable()
	{
		Chunk zipChunk = new Chunk(ChunkType.COUNTY, "10025");
		zipChunk.add(new Token(TokenType.ZIP, "10025", 0, 5));
		locationTokens.getChunks().add(zipChunk);
		ValueResolution res = boroughResolver.resolve(locationTokens);
		assertThat(res, not(nullValue()));
		assertThat(res.unresolvedCount(),equalTo(0));
		assertThat(res.resolvedCount(),equalTo(1));
		assertThat(res.totalCount(),equalTo(1));
	}

	@Test
	public void testResolve_containsCorrectResolutionCount()
	{
		chunk.add(resolvableBoroughNameToken);
		chunk.add(unresolvableBoroughNameToken);
		ValueResolution res = boroughResolver.resolve(locationTokens);
		assertThat(res, not(nullValue()));
		assertThat(res.unresolvedCount(),equalTo(1));
		assertThat(res.resolvedCount(),equalTo(1));
		assertThat(res.totalCount(),equalTo(2));
	}

}
