package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.File;
import java.io.IOException;

public class BuildConfigurationResolver {

    public static final String DEFAULT_GC_JNI_VERSION_PREFIX = "geoclient-jni-";
    // Must end with a trailing slash!
    public static final String DEFAULT_SUBDIR_GS_GEOFILES = "fls" + System.getProperty("file.separator");
    public static final String DEFAULT_SUBDIR_GS_LIBRARY_PATH = "lib";

    public static final String ENV_VAR_GEOFILES = "GEOFILES";
    public static final String ENV_VAR_GEOSUPPORT_HOME = "GEOSUPPORT_HOME";
    public static final String ENV_VAR_GS_LIBRARY_PATH = "GS_LIBRARY_PATH";

    public static final String PROP_GC_JNI_VERSION = "gcJniVersion";
    public static final String PROP_GS_HOME = "gsHome";
    public static final String PROP_GS_GEOFILES = "gsGeofiles";
    public static final String PROP_GS_LIBRARY_PATH = "gsLibraryPath";

    public static final String SYSPROP_GC_JNI_VERSION = "gc.jni.version";

    final Logger logger = Logging.getLogger(BuildConfigurationResolver.class);

    private final Project project;
    private final GeosupportExtension geosupportExtension;

    public BuildConfigurationResolver(Project project, GeosupportExtension geosupportExtension) {
        this.project = project;
        this.geosupportExtension = geosupportExtension;
    }

    public String getSystemProperty(String name) {
        String result = System.getProperty(name);
        if(result != null) {
            logger.info("Resolved java system property '{}' to value '{}'", name, result);
            return result;
        }
        logger.info("Java system property with name '{}' could not be resolved", name);
        return null;
    }

    public String getEnv(String name) {
        String result = System.getenv(name);
        if(result != null) {
            logger.info("Resolved environment variable '{}' to value '{}'", name, result);
            return result;
        }
        logger.info("Environment variable '{}' could not be resolved", name);
        return null;
    }

    public Object getGradleProperty(String propName) {
        if(this.project.hasProperty(propName)) {
            Object result = this.project.property(propName);
            logger.info("Resolved gradle project property '{}' to value '{}'", propName, result);
            return result;
        }
        logger.info("Gradle project property '{}' could not be resolved", propName);
        return null;
    }

    private String getGradlePropertyAsString(String propName) {
        Object prop = getGradleProperty(propName);
        if(prop != null) {
            return prop.toString();
        }
        return null;
    }

    public String resolveGeosupportHome() {
        // 1. Resolve from gradle property
        String geosupportHome = getGradlePropertyAsString(PROP_GS_HOME);
        // 2. Resolve from plugin extension
        if(!Utils.hasValue(geosupportHome)) {
            geosupportHome = geosupportExtension.getHome().getOrNull();
        }
        // 3. Resolve from environment variable
        if(!Utils.hasValue(geosupportHome)) {
            geosupportHome = getEnv(ENV_VAR_GEOSUPPORT_HOME);
        }
        // Set the gradle property even if it is null
        project.setProperty(PROP_GS_HOME, geosupportHome);
        return geosupportHome;
    }

    public String resolveGeosupportLibraryPath() {
        // 1. Resolve from gradle property
        String gsLibraryPath = getGradlePropertyAsString(PROP_GS_LIBRARY_PATH);
        // 2. Resolve from plugin extension
        if(!Utils.hasValue(gsLibraryPath)) {
            gsLibraryPath = geosupportExtension.getLibraryPath().getOrNull();
        }
        // 3. Resolve from environment variable
        if(!Utils.hasValue(gsLibraryPath)) {
            gsLibraryPath = getEnv(ENV_VAR_GS_LIBRARY_PATH);
        }
        // 4. If gsHome has been resolved, use default
        if(!Utils.hasValue(gsLibraryPath)) {
            gsLibraryPath = resolveGeosupportHomeSubdir(DEFAULT_SUBDIR_GS_LIBRARY_PATH);
        }
        // Set the gradle property even if it is null
        project.setProperty(PROP_GS_LIBRARY_PATH, gsLibraryPath);
        return gsLibraryPath;
    }

    public String resolveGeosupportGeofiles() {
        // 1. Resolve from gradle property
        String gsGeofiles = getGradlePropertyAsString(PROP_GS_GEOFILES);
        // 2. Resolve from plugin extension
        if(!Utils.hasValue(gsGeofiles)) {
            gsGeofiles = geosupportExtension.getGeofiles().getOrNull();
        }
        // 3. Resolve from environment variable
        if(!Utils.hasValue(gsGeofiles)) {
            gsGeofiles = getEnv(ENV_VAR_GEOFILES);
        }
        // 4. If gsHome has been resolved, use default
        if(!Utils.hasValue(gsGeofiles)) {
            gsGeofiles = resolveGeosupportHomeSubdir(DEFAULT_SUBDIR_GS_GEOFILES);
        }
        // Set the gradle property even if it is null
        project.setProperty(PROP_GS_GEOFILES, gsGeofiles);
        return gsGeofiles;
    }

    private String resolveGeosupportHomeSubdir(String subdir) {
        String result = null;
        String geosupportHome = getGradlePropertyAsString(PROP_GS_HOME);
        if(Utils.hasValue(geosupportHome)) {
            try { 
                result = new File(geosupportHome, subdir).getCanonicalPath();
            } catch(IOException e) {
                logger.warn(String.format("Error constructing canonical path from Geosupport home subdirectoy:", e.getMessage()));
            }
        }
        return result;
    }

    public String resolveGeoclientJniVersion() {
        // 1. Resolve from gradle property
        String gcJniVersion = getGradlePropertyAsString(PROP_GC_JNI_VERSION);
        // 2. Resolve from java system property
        if(!Utils.hasValue(gcJniVersion)) {
            gcJniVersion = getSystemProperty(PROP_GC_JNI_VERSION);
        }
        // 3. Resolve from project.version property
        if(!Utils.hasValue(gcJniVersion)) {
            gcJniVersion = String.format("%s%s", DEFAULT_GC_JNI_VERSION_PREFIX, project.getVersion());
        }
        // Set the gradle property even if null or empty
        project.setProperty(PROP_GC_JNI_VERSION, gcJniVersion);
        return gcJniVersion;
    }
}