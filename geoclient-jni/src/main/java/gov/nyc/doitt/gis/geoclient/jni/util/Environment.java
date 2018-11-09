package gov.nyc.doitt.gis.geoclient.jni.util;

//
// This class was originally copied from Facebook's RocksDB v5.9.2
// See https://github.com/facebook/rocksdb
//

public class Environment {

	public enum Platform {

		linux_x64, windows_x64;

		public static Platform fromText(String text) {
			if (null == text) {
				throw new NullPointerException("Argument 'text' cannot be null");
			}
			if (text.toLowerCase().contains("linux")) {
				return Platform.linux_x64;
			}
			if (text.toLowerCase().contains("windows")) {
				return Platform.windows_x64;
			}

			throw new IllegalArgumentException(String.format("Unsupported platform '%s'", text));
		}

		@Override
		public String toString() {
			return this.name().replace("_", "-");
		}

	};

	private static String OS = System.getProperty("os.name").toLowerCase();

	private static String ARCH = System.getProperty("os.arch").toLowerCase();

	private static String JNI_LIBRARY_BASENAME = "geoclientjni";

	public static boolean isWindows() {
		return OS.contains("windows");
	}

	public static boolean isLinux() {
		return OS.contains("linux");
	}

	public static boolean is64Bit() {
		return ARCH.indexOf("64") > 0;
	}

	public static String getPlatformName() {
		if (isLinux()) {
			return "linux";
		}
		if (isWindows()) {
			return "windows";
		}

		throw new UnsupportedPlatformException(OS, ARCH);
	}

	public static String getArchitecture() {
		return "x64";
	}

	public static String getJniLibraryName() {
		return JNI_LIBRARY_BASENAME;
	}

	public static String getJniLibraryFileName() {

		if (isLinux()) {
			return String.format("lib%s.so", getJniLibraryName());
		}

		if (isWindows()) {
			return String.format("%s.dll", getJniLibraryName());
		}

		throw new UnsupportedPlatformException(OS, ARCH);
	}

	public static String getJniLibrarySubdirectoryName() {

		if (isLinux()) {
			return "linux-64";
		}

		if (isWindows()) {
			return "windows-64";
		}

		throw new UnsupportedPlatformException(OS, ARCH);
	}

}
