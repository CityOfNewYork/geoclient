package gov.nyc.doitt.gis.geoclient.parser.regex;

import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;
import gov.nyc.doitt.gis.geoclient.parser.regex.AddressParser;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressParserTest.class);
	
	@Test
	public void testAddressParser()
	{
		testParser(new AddressParser(), LOGGER);
	}
}
