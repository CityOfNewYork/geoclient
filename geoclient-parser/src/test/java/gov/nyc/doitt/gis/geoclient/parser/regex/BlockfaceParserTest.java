package gov.nyc.doitt.gis.geoclient.parser.regex;

import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockfaceParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BlockfaceParserTest.class);
	private BlockfaceParser parser;

	@Before
	public void setUp() throws Exception
	{
		this.parser = new BlockfaceParser();
	}

	@Test
	public void testParse()
	{
		testParser(parser, LOGGER);
	}

}
