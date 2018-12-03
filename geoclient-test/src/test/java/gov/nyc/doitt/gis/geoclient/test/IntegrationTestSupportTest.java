package gov.nyc.doitt.gis.geoclient.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

// TODO Capture stdout or mock
class IntegrationTestSupportTest {

	private static class IntegrationTest implements IntegrationTestSupport {
		
		final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
		
		void debugLogEnvironment() {
			logEnvironment(Level.DEBUG, logger);
		}
		void debugLogSystemProperties() {
			logSystemProperties(Level.DEBUG, logger);
		}
	}
	
	@Test
	void testLogEnvironment() {
		IntegrationTest t = new IntegrationTest();
		t.debugLogEnvironment();
	}

	@Test
	void testLogSystemProperties() {
		IntegrationTest t = new IntegrationTest();
		t.debugLogSystemProperties();
	}

}
