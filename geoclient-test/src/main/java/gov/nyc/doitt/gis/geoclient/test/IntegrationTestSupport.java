package gov.nyc.doitt.gis.geoclient.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import gov.nyc.doitt.gis.geoclient.test.LogLevelAdapter;


public interface IntegrationTestSupport {

	default String getJavaLibraryPath() {
		return System.getProperty("java.library.path");
	}
	
	default String getJavaIoTmpdir() {
		return System.getProperty("java.io.tmpdir");
	}
	
	default boolean contains(File target) {
		List<String> paths = Arrays.asList(System.getProperty("PATH").split(File.separator));
		for (String string : paths) {
			Path path = Paths.get(string);
			if(path.toFile().equals(target)) {
				return true;
			}
		}
		return false;
	}
	
	default File getSystemPropertyAsFile(String key) {
		return new File(System.getProperty(key,"."));		
	}
	
	default File getEnvVarAsFile(String name) {
		return new File(System.getenv(name));		
	}
	
	default void logEnvironment(final Level level, final Logger logger) {
		LogLevelAdapter.logAll(level, logger, Collections.synchronizedSortedMap(new TreeMap<>(System.getenv())));
	}

	default void logSystemProperties(final Level level, final Logger logger) {
		LogLevelAdapter.logAll(level, logger, Collections.synchronizedSortedMap(new TreeMap<>(System.getProperties())));
	}
}
