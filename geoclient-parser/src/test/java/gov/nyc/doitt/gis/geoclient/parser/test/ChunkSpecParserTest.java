package gov.nyc.doitt.gis.geoclient.parser.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpecParser.MutableToken;
import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpecParser.TokenTypeOccurrence;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ChunkSpecParserTest
{
	private ChunkSpecParser specParser;

	@Before
	public void setUp() throws Exception
	{
		this.specParser = new ChunkSpecParser();
	}
	
	@Test
	public void testParse()
	{
		// '59 Maiden Lane, Manhattan'
		String delimitedTokenText = "[59] [Maiden Lane], [Manhattan]";
		StringBuffer buffer = new StringBuffer(delimitedTokenText);
		buffer.append("=");
		buffer.append(makeChunkDefinition(ChunkType.COUNTY, 0, 25, new TokenTypeOccurrence(TokenType.BOROUGH_NAME,3)));
		buffer.append("|");
		buffer.append(makeChunkDefinition(ChunkType.ADDRESS, 0, 16, new TokenTypeOccurrence(TokenType.HOUSE_NUMBER,1),new TokenTypeOccurrence(TokenType.STREET_NAME,2)));
		ChunkSpec actual = this.specParser.parse("testParse-id", buffer.toString());
		assertThat(actual.getId(), is(equalTo("testParse-id")));
		assertThat(actual.chunkCount(), is(equalTo(2)));
		Chunk expectedFirstChunk = new Chunk(ChunkType.COUNTY,"59 Maiden Lane, Manhattan");
		expectedFirstChunk.add(new Token(TokenType.BOROUGH_NAME,"Manhattan",16,25));
		assertThat(actual.firstChunk(), is(equalTo(expectedFirstChunk)));
		Chunk expectedSecondChunk = new Chunk(ChunkType.ADDRESS,"59 Maiden Lane, ");
		expectedSecondChunk.add(new Token(TokenType.HOUSE_NUMBER,"59",0,2));
		expectedSecondChunk.add(new Token(TokenType.STREET_NAME,"Maiden Lane",3,14));
		assertThat(actual.secondChunk(), is(equalTo(expectedSecondChunk)));
	}
	
	@Test
	public void testParseTokenTypeOccurrence()
	{
		String input = String.format("%s{%d}", TokenType.CITY_NAME,12);
		TokenTypeOccurrence result = this.specParser.parseTokenTypeOccurrence(input);
		assertThat(result.tokenType(), is(equalTo(TokenType.CITY_NAME)));
		assertThat(result.occurrence(), is(equalTo(12)));
		assertThat(result.zeroIndexOccurrence(), is(equalTo(11)));
	}
	
	@Test
	public void testCreateDefaultChunkSpec()
	{
		assertDefaultChunkSpec(this.specParser.createDefaultChunkSpec("123", "abc"), "123","abc");
	}

	@Test(expected=TestConfigurationException.class)
	public void testParse_idNull()
	{
		this.specParser.parse(null, "xyz");
	}

	@Test(expected=TestConfigurationException.class)
	public void testParse_idEmpty()
	{
		this.specParser.parse("", "xyz");
	}

	@Test(expected=TestConfigurationException.class)
	public void testParse_unparsedStringNull()
	{
		this.specParser.parse("qrx", null);
	}

	@Test(expected=TestConfigurationException.class)
	public void testParse_unparsedStringEmpty()
	{
		this.specParser.parse("qrx", "");
	}
	
	@Test
	public void testParse_noSplitCharReturnsDeafultChunkSpec()
	{
		assertDefaultChunkSpec(this.specParser.parse("testId", "XXXXYYYY"),"testId","XXXXYYYY");
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParse_splitsIntoTooMany()
	{
		this.specParser.parse("testParse", "XX=XXY=YYY");
	}

	@Test
	public void testParse_splitsIntoEmptyReturnsDefaultChunkSpec()
	{
		assertDefaultChunkSpec(this.specParser.parse("testId", "XXXXY="),"testId","XXXXY");
	}
	
	@Test
	public void testParseDelimitedString()
	{
		// X[YZ] [AB]C[D]
		// XYZ ABCD
		List<ChunkSpecParser.MutableToken> results = this.specParser.parseBracketValues("X[YZ] [AB]C[D]");
		assertThat(results.size(), is(equalTo(3)));
		MutableToken tk1 = results.get(0);
		assertThat(tk1.getValue(), is(equalTo("YZ")));
		assertThat(tk1.getStart(), is(equalTo(1)));
		assertThat(tk1.getEnd(), is(equalTo(3)));
		MutableToken tk2 = results.get(1);
		assertThat(tk2.getValue(), is(equalTo("AB")));
		assertThat(tk2.getStart(), is(equalTo(4)));
		assertThat(tk2.getEnd(), is(equalTo(6)));
		MutableToken tk3 = results.get(2);
		assertThat(tk3.getValue(), is(equalTo("D")));
		assertThat(tk3.getStart(), is(equalTo(7)));
		assertThat(tk3.getEnd(), is(equalTo(8)));
	}

	@Test(expected=TestConfigurationException.class)
	public void testParseChunkType_unparsedStringArgumentIsNull()
	{
		this.specParser.parseChunkType(null);
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunkType_unparsedStringArgumentIsEmpty()
	{
		this.specParser.parseChunkType("");
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunkType_unparsedStringArgumentDoesNotHaveValidChunkType()
	{
		this.specParser.parseChunkType("FOO(0,1)");
	}
	
	@Test
	public void testParseChunkType()
	{
		String input = String.format("%s(0-12:ZIP)", ChunkType.SUBSTRING.toString());
		assertThat(this.specParser.parseChunkType(input),is(equalTo(ChunkType.SUBSTRING)));
	}
	
	@Test
	public void testParseChunkRange()
	{
		ChunkSpecParser.Range range = specParser.parseChunkRange("0-12");
		assertThat(range.getStart(), is(equalTo(0)));
		assertThat(range.getEnd(), is(equalTo(12)));
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunkRange_null()
	{
		specParser.parseChunkRange(null);
	}
	
	@Test(expected=NumberFormatException.class)
	public void testParseChunkRange_invalidNumber()
	{
		specParser.parseChunkRange("2-A");
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunkRange_empty()
	{
		specParser.parseChunkRange("");
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunkRange_invalidRange1()
	{
		specParser.parseChunkRange("-");
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunkRange_invalidRange2()
	{
		specParser.parseChunkRange("2-");
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunk_chunkDefinitionEmpty()
	{
		specParser.parseChunk("","[A]BC",Arrays.asList(new MutableToken[]{}));
	}
	@Test(expected=TestConfigurationException.class)
	public void testParseChunk_delimitedTokenTextEmpty()
	{
		specParser.parseChunk("COUNTY(0-6)","",Arrays.asList(new MutableToken[]{}));
	}
	
	@Test
	public void testParseChunk_chunkDefinitionContainsNotTokens()
	{
		String chunkDefinition = makeChunkDefinition(ChunkType.COUNTY, 2, 4); 
		Chunk chunk = specParser.parseChunk(chunkDefinition,"ABCDEF",Arrays.asList(new MutableToken[]{}));
		assertThat(chunk.getType(), is(equalTo(ChunkType.COUNTY)));
		assertThat(chunk.getText(), is(equalTo("CD")));
		assertThat(chunk.getTokens().isEmpty(), is(true));
	}
	
	@Test
	public void testParseChunk()
	{
		String chunkDefinition = makeChunkDefinition(ChunkType.COUNTY, 2, 7,new TokenTypeOccurrence(TokenType.BOROUGH_NAME,1)); 
		Chunk chunk = specParser.parseChunk(chunkDefinition,", [BRONX]",Arrays.asList(new MutableToken[]{new MutableToken("BRONX", 2, 7)}));
		assertThat(chunk.getType(), is(equalTo(ChunkType.COUNTY)));
		assertThat(chunk.getText(), is(equalTo("BRONX")));
		assertThat(chunk.getTokens().get(0), is(equalTo(new Token(TokenType.BOROUGH_NAME,"BRONX",2,7))));
	}
	
	@Test(expected=TestConfigurationException.class)
	public void testParseChunk_untypedTokenListSizeLessThanSpecifiedTokenTypesLength()
	{
		specParser.parseChunk(makeChunkDefinition(ChunkType.COUNTY, 0, 6, new TokenTypeOccurrence(TokenType.BLOCK,1))," [1889] ",Arrays.asList(new MutableToken[]{}));
	}
	
	@Test
	public void testRemoveBrackets()
	{
		assertThat(this.specParser.removeBrackets("A[B]C [ ][]D[EF] "),is(equalTo("ABC  DEF ")));
	}
	
	private String makeChunkDefinition(ChunkType chunkType, int start, int end,TokenTypeOccurrence... typeOccurrences)
	{
		StringBuffer buff = new StringBuffer(String.format("%s(%d-%d", chunkType.toString(),start,end));
		for (int i = 0; i < typeOccurrences.length; i++)
		{
			if(i == 0)
			{
				buff.append(":");
			}
			buff.append(typeOccurrences[i].tokenType());
			buff.append("{");
			buff.append(typeOccurrences[i].occurrence());
			buff.append("}");
			if(i < typeOccurrences.length -1)
			{
				buff.append(",");
			}
		}
		buff.append(")");
		return buff.toString();
	}

	private void assertDefaultChunkSpec(ChunkSpec actual, String expectedId, String expectedText)
	{
		assertThat(actual.getChunks().size(),is(equalTo(1)));
		assertThat(actual.getId(),is(equalTo(expectedId)));
		Chunk chunk = actual.getChunks().get(0);
		assertThat(chunk.tokenCount(),is(equalTo(0)));
		assertThat(chunk.getText(),is(equalTo(expectedText)));
		assertThat(chunk.getType(),is(equalTo(ChunkType.ORIGINAL_INPUT)));
	}
	
}
