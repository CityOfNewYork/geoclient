package gov.nyc.doitt.gis.geoclient.jni;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TempDirSupportTest implements TempDirSupport {

    final Logger logger = LoggerFactory.getLogger(TempDirSupportTest.class);

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	void testGetTempDirSystemProperty() throws java.io.IOException {
        logger.warn("Calling getTempDirFromSystemProperty()");
		File expected = getTempDirFromSystemProperty();
        logger.warn("Result: {}", expected.getCanonicalPath());
        logger.warn("Calling temporaryFolder.newFolder({})", "snafubar");
        File tempDir = temporaryFolder.newFolder("snafubar");
        logger.warn("Result: {}", tempDir);
		File actual = temporaryFolder.getRoot().getParentFile();
		assertEquals(expected, actual);
	}

}
