package gov.nyc.doitt.gis.geoclient.gradle.common;

import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR;

import java.io.File;
import java.io.IOException;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.provider.Provider;

import gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin;
import gov.nyc.doitt.gis.geoclient.gradle.ctx.AnnotatedGeoclientExtension;
import gov.nyc.doitt.gis.geoclient.gradle.ctx.AnnotatedGeosupportExtension;

public class BuildConfigurationResolver {

    final Logger logger = Logging.getLogger(BuildConfigurationResolver.class);

    private final Project project;
    private final AnnotatedGeoclientExtension geoclientExtension;
    private final AnnotatedGeosupportExtension geosupportExtension;

    public BuildConfigurationResolver(Project project, AnnotatedGeoclientExtension geoclientExtension,
            AnnotatedGeosupportExtension geosupportExtension) {
        this.project = project;
        this.geoclientExtension = geoclientExtension;
        this.geosupportExtension = geosupportExtension;
    }

    public String getSystemProperty(String name) {
        String result = System.getProperty(name);
        if (result != null) {
            logger.info("Resolved java system property '{}' to value '{}'", name, result);
            return result;
        }
        logger.info("Java system property with name '{}' could not be resolved", name);
        return null;
    }

    public String getEnv(String name) {
        String result = System.getenv(name);
        if (result != null) {
            logger.info("Resolved environment variable '{}' to value '{}'", name, result);
            return result;
        }
        logger.info("Environment variable '{}' could not be resolved", name);
        return null;
    }

    public Object getGradleProperty(String propName) {
        if (this.project.hasProperty(propName)) {
            Object result = this.project.property(propName);
            logger.info("Resolved gradle project property '{}' to value '{}'", propName, result);
            return result;
        }
        logger.info("Gradle project property '{}' could not be resolved", propName);
        return null;
    }

    public String resolveGeosupportHome() {
        // 1. Resolve from plugin extension
        String geosupportHome = geosupportExtension.getHome().getOrNull();
        logger.info("geosupport.home extension value: '{}'", geosupportHome);
        // 2. Resolve from environment variable and update extension (if successful)
        if (!Utils.hasValue(geosupportHome)) {
            geosupportHome = getEnv(GeoclientPlugin.GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME);
            logger.info("GEOSUPPORT_HOME environment variable value: '{}'", geosupportHome);
            if (geosupportHome != null) {
                geosupportExtension.getHome().set(geosupportHome);
            }
        }
        return geosupportHome;
    }

    public String resolveGeosupportLibraryPath() {
        // 1. Resolve from plugin extension
        String gsLibraryPath = geosupportExtension.getLibraryPath().getOrNull();
        logger.info("geosupport.libraryPath extension value: '{}'", gsLibraryPath);
        // 2. Resolve from environment variable
        if (!Utils.hasValue(gsLibraryPath)) {
            gsLibraryPath = getEnv(GeoclientPlugin.GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH);
            logger.info("GS_LIBRARY_PATH environment variable value: '{}'", gsLibraryPath);
        }
        // 3. Try using default lib subdirectory if geosupport.home can be resolved
        if (!Utils.hasValue(gsLibraryPath)) {
            gsLibraryPath = resolveGeosupportHomeSubdir(GeoclientPlugin.GEOSUPPORT_DEFAULT_LIBRARY_PATH);
            logger.info("geosupport.libraryPath value derived from '${geosupport.home}/lib': '{}'", gsLibraryPath);
        }
        // 4. If the value was found by steps 2. or 3., update the extension
        if (Utils.hasValue(gsLibraryPath) && !geosupportExtension.getLibraryPath().isPresent()) {
            geosupportExtension.getLibraryPath().set(gsLibraryPath);
        }
        return gsLibraryPath;
    }

    public String resolveGeosupportGeofiles() {
        // 1. Resolve from plugin extension
        String gsGeofiles = geosupportExtension.getGeofiles().getOrNull();
        logger.info("geosupport.geofiles extension value: '{}'", gsGeofiles);
        // 2. Resolve from environment variable
        if (!Utils.hasValue(gsGeofiles)) {
            gsGeofiles = getEnv(GeoclientPlugin.GEOSUPPORT_ENV_VAR_GEOFILES);
            logger.info("GEOFILES environment variable value: '{}'", gsGeofiles);
        }
        // 3. Try using default fls/ subdirectory if geosupport.home can be resolved
        if (!Utils.hasValue(gsGeofiles)) {
            gsGeofiles = resolveGeosupportHomeSubdir(GeoclientPlugin.GEOSUPPORT_DEFAULT_GEOFILES);
            logger.info("geosupport.geofiles value derived from '${geosupport.home}/fls/': '{}'", gsGeofiles);
        }
        // 4. Insure that any value set ends with the required trailing slash!
        if (Utils.hasValue(gsGeofiles) && !endsWithFileSeparator(gsGeofiles)) {
            gsGeofiles = gsGeofiles + System.getProperty("file.separator");
            logger.info(
                    "Appended file.separator character '{}' to geosupport.geofiles value because trailing slash is required by Geosupport",
                    System.getProperty("file.separator"));
            // 5. Update the extension regardless of how it was because of possible fix by
            // step 4.
            geosupportExtension.getGeofiles().set(gsGeofiles);
        }
        return gsGeofiles;
    }

    private boolean endsWithFileSeparator(String path) {
        if (path != null) {
            if (path.endsWith("/")) {
                return true;
            }
            if (path.endsWith("\\")) {
                return true;
            }
        }
        return false;
    }

    private String resolveGeosupportHomeSubdir(String subdir) {
        String result = null;
        String geosupportHome = resolveGeosupportHome();
        if (Utils.hasValue(geosupportHome)) {
            try {
                result = new File(geosupportHome, subdir).getCanonicalPath();
            } catch (IOException e) {
                logger.warn(String.format("Error constructing canonical path from Geosupport home subdirectoy:",
                        e.getMessage()));
            }
        }
        return result;
    }

    public File resolveGeoclientNativeTempDir() {
        File dir = null;
        // 1. Resolve from plugin extension
        if (geoclientExtension.getNativeTempDir().isPresent()) {
            Provider<File> viewOfDir = geoclientExtension.getNativeTempDir().getAsFile();
            dir = viewOfDir.getOrNull();
            logger.info("geoclient.nativeTempDir extension value: '{}'", dir);
        }

        // 2. Resolve from java system property and update extension (if successfull)
        if (dir == null) {
            String nativeTempDirString = getSystemProperty(GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR);
            logger.info("geoclient.nativeTempDir resolved from java system property '{}' to value: '{}'",
                    GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR, nativeTempDirString);
            if (nativeTempDirString != null) {
                geoclientExtension.getNativeTempDir().dir(nativeTempDirString);
                Provider<File> viewOfDir = geoclientExtension.getNativeTempDir().getAsFile();
                dir = viewOfDir.getOrNull();
            }
        }
        return dir;
    }
}
