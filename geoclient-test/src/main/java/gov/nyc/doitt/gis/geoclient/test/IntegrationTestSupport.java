package gov.nyc.doitt.gis.geoclient.test;

import org.slf4j.Logger;

public interface IntegrationTestSupport {

	default void logSystemProperties(Logger logger) {
		
		System.getProperties().entrySet().forEach( 
				e -> logger.info("{}={}", e.getKey(),e.getValue()
		));
	}
}
