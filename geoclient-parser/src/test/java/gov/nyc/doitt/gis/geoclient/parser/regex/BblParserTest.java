package gov.nyc.doitt.gis.geoclient.parser.regex;

import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;
import gov.nyc.doitt.gis.geoclient.parser.regex.BblParser;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BblParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BblParserTest.class);
	
	@Test
	public void testBblParser()
	{
		testParser(new BblParser(), LOGGER);
	}
	
	
}
