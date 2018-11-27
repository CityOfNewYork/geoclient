package gov.nyc.doitt.gis.geoclient.jni;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

class TempDirSupportTest implements TempDirSupport {

	private TemporaryFolder temporaryFolder;
	private File tempDir;
	
	@BeforeEach
	void beforeEach() throws IOException {
		temporaryFolder = getTemporaryFolder();
		tempDir = temporaryFolder.newFolder();
		assertTrue(tempDir.exists());
	}
	
	@AfterEach
	void afterEach() {
		assertFalse(tempDir.exists());
	}
	
	@Test
	void testGetTempDirSystemProperty() {
		File expected = getTempDirFromSystemProperty();
		TemporaryFolder tf = getTemporaryFolder(expected);
		File actual = tf.getRoot().getParentFile();
		assertEquals(expected, actual);
	}

}
