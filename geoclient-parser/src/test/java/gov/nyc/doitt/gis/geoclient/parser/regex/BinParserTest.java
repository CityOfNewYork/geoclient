package gov.nyc.doitt.gis.geoclient.parser.regex;

import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BinParserTest.class);
	
	@Test
	public void testBblParser()
	{
		testParser(new BinParser(), LOGGER);
	}
	
	
}
