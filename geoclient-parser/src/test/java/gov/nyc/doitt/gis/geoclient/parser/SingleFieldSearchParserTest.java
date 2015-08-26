package gov.nyc.doitt.gis.geoclient.parser;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;
import gov.nyc.doitt.gis.geoclient.parser.test.ChunkSpec;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ParserConfig.class})
public class SingleFieldSearchParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SingleFieldSearchParserTest.class);
	
	@Autowired
	private SingleFieldSearchParser parser;

	@Test
	public void testParse()
	{
		List<ChunkSpec> specs =  specBuilder.getSpecs("AllParsers");
		assertThat(specs.isEmpty(), not(true));
		for (ChunkSpec spec : specs)
		{
			logSpecStart(LOGGER, spec);
			Input input = spec.input();
			LocationTokens locationTokens = this.parser.parse(input);
			assertChunksEquals(spec.getId(), spec.getChunks(), locationTokens.getChunks(), LOGGER);
		}
	}
	

}
