package gov.nyc.doitt.gis.geoclient.parser.regex;

import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ParserConfig.class})
public class BoroughParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BoroughParserTest.class);
	
	@Autowired
	private BoroughParser parser;
	

	@Test
	public void testParse()
	{
		testParser(parser, LOGGER);
	}

}
