package gov.nyc.doitt.gis.geoclient.parser.regex;

import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntersectionParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IntersectionParserTest.class);
	private IntersectionParser parser;

	@Before
	public void setUp() throws Exception
	{
		parser = new IntersectionParser();
	}

	@Test
	public void testParse()
	{
		testParser(this.parser, LOGGER);
	}

}
