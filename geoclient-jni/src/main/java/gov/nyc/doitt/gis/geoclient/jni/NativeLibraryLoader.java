package gov.nyc.doitt.gis.geoclient.jni;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.util.JniLibrary;
import gov.nyc.doitt.gis.geoclient.jni.util.NativeLibraryLocator;
import gov.nyc.doitt.gis.geoclient.jni.util.Platform;

/**
 * Loads native shared libraries for use with the JNI API a using simple,
 * operating system-specific logic.
 */
public class NativeLibraryLoader {

    final Logger logger = LoggerFactory.getLogger(NativeLibraryLocator.class);

    private final String baseLibraryName;

    public NativeLibraryLoader(String baseLibraryName) {
        super();
        this.baseLibraryName = baseLibraryName;
    }

    public void loadLibrary(String extractDir) throws IOException {
        NativeLibraryLocator locator = new NativeLibraryLocator(extractDir);
        File libFile = locator.find(getJniLibrary());
        logger.info("Attempting to load {} from file {}", baseLibraryName, libFile.getCanonicalPath());
        System.load(libFile.getCanonicalPath());
        logger.info("Successfully loaded {} from file {}", baseLibraryName, libFile.getCanonicalPath());
    }

    protected JniLibrary getJniLibrary() {
        return JniLibrary.builder().name(this.baseLibraryName).platform(new Platform())
                .version(JniContext.getGeoclientJniVersion()).build();
    }

}
