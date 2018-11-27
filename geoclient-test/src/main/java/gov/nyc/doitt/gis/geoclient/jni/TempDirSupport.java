package gov.nyc.doitt.gis.geoclient.jni;

import static gov.nyc.doitt.gis.geoclient.jni.JniContext.SystemProperty.JAVA_IO_TMPDIR;

import java.io.File;

import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.migrationsupport.rules.ExternalResourceSupport;
import org.junit.rules.TemporaryFolder;

@ExtendWith(ExternalResourceSupport.class)
public interface TempDirSupport {

	@Rule
	default TemporaryFolder getTemporaryFolder(File parent) {
		return new TemporaryFolder(parent);
	}

	@Rule
	default TemporaryFolder getTemporaryFolder() {
		return new TemporaryFolder();
	}

	default File getTempDirFromSystemProperty() {		
		return new File(JniContext.getSystemProperty(JAVA_IO_TMPDIR));
	}

	
}
