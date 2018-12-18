package gov.nyc.doitt.gis.geoclient.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
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
		
		File getJavaLibraryPathAsFile() {
			return getSystemPropertyAsFile(getJavaLibraryPath());
		}
	}
	
	private IntegrationTest itestInstance;
	
	@BeforeEach
	void setUp() {
		this.itestInstance = new IntegrationTest();
	}
	
	@Test
	void testGetSystemPropertyAsFile() {
		File actual = itestInstance.getJavaLibraryPathAsFile();
		assertTrue(actual.exists());
	}
	
	@Test
	void testLogEnvironment() {
		itestInstance.debugLogEnvironment();
	}

	@Test
	void testLogSystemProperties() {
		itestInstance.debugLogSystemProperties();
	}

}
