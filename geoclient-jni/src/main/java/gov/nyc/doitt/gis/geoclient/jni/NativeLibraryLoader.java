package gov.nyc.doitt.gis.geoclient.jni;

import java.io.File;
import java.io.IOException;

import gov.nyc.doitt.gis.geoclient.jni.util.JniLibrary;
import gov.nyc.doitt.gis.geoclient.jni.util.NativeLibraryLocator;
import gov.nyc.doitt.gis.geoclient.jni.util.Platform;

/**
 * Loads native shared libraries for use with the JNI API a using simple, operating
 * system-specific logic.
 */
public class NativeLibraryLoader {

	private final String baseLibraryName;

	public NativeLibraryLoader(String baseLibraryName) {
		super();
		this.baseLibraryName = baseLibraryName;
	}

	public void loadLibrary(String extractDir) throws IOException {
		NativeLibraryLocator locator = new NativeLibraryLocator(extractDir);
		File libFile = locator.find(getJniLibrary());
		System.load(libFile.getCanonicalPath());
	}

	protected JniLibrary getJniLibrary() {
		return JniLibrary.builder().name(this.baseLibraryName).platform(new Platform())
				.build();
	}

}
