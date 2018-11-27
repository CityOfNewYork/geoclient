package gov.nyc.doitt.gis.geoclient.jni;

public class JniContext {

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
		return System.getProperty(sysProp.key());
	}
}
