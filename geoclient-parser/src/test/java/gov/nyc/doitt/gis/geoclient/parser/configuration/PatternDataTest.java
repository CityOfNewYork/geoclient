package gov.nyc.doitt.gis.geoclient.parser.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ParserConfig.class})
public class PatternDataTest{

	@Autowired
	private ParserConfig patternData;
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testBoroughNames()
	{
		assertTrue(applicationContext.containsBean("boroughNamesToBoroughMap"));
		assertNotNull(patternData);
		assertFalse(patternData.boroughNamesToBoroughMap().isEmpty());
	}

	@Test
	public void testCityNames()
	{
		assertTrue(applicationContext.containsBean("cityNamesToBoroughMap"));
		assertNotNull(patternData);
		assertFalse(patternData.cityNamesToBoroughMap().isEmpty());
	}
}
