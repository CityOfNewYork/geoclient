package gov.nyc.doitt.gis.geoclient.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpec;
import gov.nyc.doitt.gis.geoclient.parser.test.SpecBuilder;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.slf4j.Logger;

import difflib.DiffUtils;
import difflib.Patch;

public abstract class AbstractSpecTest
{
	protected static SpecBuilder specBuilder;

	@BeforeClass
	public static void setUpOnce()
	{
		specBuilder = new SpecBuilder();
	}
	
	protected void testParser(Parser parser, Logger logger)
	{
		List<ChunkSpec> specs =  specBuilder.getSpecs(parser.getName());
		assertThat(specs.isEmpty(),is(not(true)));
		
		for (ChunkSpec spec : specs)
		{
			logSpecStart(logger, spec);
			Input input = new Input(spec.getId(), spec.plainText());
			ParseContext context = new ParseContext(input);
			parser.parse(context);
			assertChunksEquals(spec.getId(), spec.getChunks(), context.getChunks(), logger);
		}
	}
	
	protected void logSpecStart(Logger logger, ChunkSpec spec)
	{
		logger.debug("-----------------------------------------------------------------------");
		logger.debug(String.format("TEST-%s is expecting Tokens: '%s', Chunks: '%s'", spec.getId(), spec.delimitedText(),spec.chunkTypeNames()));
	}
	
	protected void assertChunksEquals(String specId, List<Chunk> expected, List<Chunk> actual, Logger logger)
	{
		if(!expected.equals(actual))
		{
			String messageStart = String.format("Test %s ",specId); 
			int expectedSize = expected.size();
			int actualSize = actual.size();
			assertThat(messageStart + "expected " + expectedSize + " tokens but got " +actualSize, actualSize, is(expectedSize));
			for (int i = 0; i < expectedSize; i++)
			{
				Chunk expectedChunk = expected.get(i);
				Chunk actualChunk = actual.get(i);
				if(!expectedChunk.equals(actualChunk))
				{
					assertThat(String.format("Test %s Chunk texts are not equal.",specId),actualChunk.getText(),is(equalTo(expectedChunk.getText())));
					assertThat(actualChunk.getType(),is(equalTo(expectedChunk.getType())));
					assertTokensEqual(specId,expectedChunk.getTokens(), actualChunk.getTokens(),logger);
				}
			}
		}
	}

	protected void assertTokensEqual(String specId, List<Token> expected, List<Token> actual, Logger logger)
	{
		if(!expected.equals(actual))
		{
			String messageStart = String.format("Test %s ",specId); 
			int expectedSize = expected.size();
			int actualSize = actual.size();
			assertThat(messageStart + "expected " + expectedSize + " tokens but got " +actualSize, actualSize, is(expectedSize));
			for (int i = 0; i < expectedSize; i++)
			{
				Token expectedToken = expected.get(i);
				Token actualToken = actual.get(i);
				assertEquals(messageStart + "expected Token of type " + expectedToken.getType() + " but found " + actualToken.getType(), expectedToken.getType(),actualToken.getType());
				String expectedValue = expectedToken.getValue();
				String actualValue = actualToken.getValue();
				List<String> expectedForDiff = Arrays.asList(expectedValue);
				List<String> actualForDiff = Arrays.asList(actualValue);
				Patch patch = DiffUtils.diff(expectedForDiff, actualForDiff);
				logger.debug("Patch:{}",patch.toString());
				logger.debug("Deltas:{}",patch.getDeltas());
				assertEquals(String.format("%s found incorrect token value:",messageStart), expectedValue, actualValue);
				
			}
		}
	}

}
