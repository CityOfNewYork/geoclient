package gov.nyc.doitt.gis.geoclient.jni;

import static gov.nyc.doitt.gis.geoclient.jni.JniContext.SystemProperty.JAVA_IO_TMPDIR;

import java.io.File;

import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;

@EnableRuleMigrationSupport
public interface TempDirSupport {

	default File getTempDirFromSystemProperty() {
		return new File(JniContext.getSystemProperty(JAVA_IO_TMPDIR));
	}

}
