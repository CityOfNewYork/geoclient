package gov.nyc.doitt.gis.geoclient.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JniContext {

    static final Logger logger = LoggerFactory.getLogger(JniContext.class);

	public static final String GC_SHAREDLIB_BASENAME = "geoclientjni";
	public static final String GC_PACKAGE_PATH = JniContext.class.getPackage().getName().replaceAll("\\.", "\\/");

	public enum SystemProperty {
		JAVA_IO_TMPDIR("java.io.tmpdir"),
		JAVA_LIBRARY_PATH("java.library.path");

		private final String key;

		private SystemProperty(String name) {
			this.key = name;
		}

		public String key() {
			return key;
		}

		@Override
		public String toString() {
			return key();
		}

	}

	public static String getSystemProperty(SystemProperty sysProp) {
        logger.info("Retrieving System property using {}", sysProp);
		return System.getProperty(sysProp.key());
	}
}
