package gov.nyc.doitt.gis.geoclient.jni.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.jni.Logger;

class NativeLibraryLocatorTest {

	private static final Logger logger = Logger.getLogger(NativeLibraryLocatorTest.class);
	
	private final static String tmpDirProperty = "java.io.tmpdir";
    private String backup;
    private File testTmpDir;

    @BeforeEach
    void beforeEach() throws IOException {
    	
    	// Backup actual java.io.tmpdir System property
    	backup = System.getProperty(tmpDirProperty);
    	logger.debug(String.format("Actual: %s=%s", tmpDirProperty, backup));
		
    	// Create java.io.tmpdir for these tests
    	testTmpDir = getTempDir();		
		System.setProperty(tmpDirProperty, testTmpDir.getCanonicalPath());
		logger.debug(String.format(String.format("testTmpDir=%s", testTmpDir.getCanonicalPath())));		
    }

    @AfterEach
    void afterEach() {
    	// Restore actual java.io.tmpdir System property
        System.setProperty(tmpDirProperty,backup);
    }	
	
	@Test
	void testFind() throws IOException {
		NativeLibraryLocator locator = new NativeLibraryLocator(testTmpDir.getCanonicalPath());		
		File result = locator.find(getJniLibrary());
		assertNotNull(result);
		assertTrue(result.exists());
	}

	private JniLibrary getJniLibrary() throws IOException {
		return JniLibrary.builder().name("geoclientjni").platform(new Platform()).version("X1200").build();
	}
	
	private File getTempDir() throws IOException {
		URL url = getClass().getClassLoader().getResource(".");
		File root = new File(String.format("%s/../tmplib",url.getPath()));
		return root;
	}
}
