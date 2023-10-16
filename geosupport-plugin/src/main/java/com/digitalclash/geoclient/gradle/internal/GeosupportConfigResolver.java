package com.digitalclash.geoclient.gradle.internal;

/**
 * Resolves Geosupport configuration values, providing defaults if necessary.
 * Order of precedence (the first available source is used):
 * <ol>
 * <li>Include path: set via constructor argument.</li>
 * <li>All (except include path if set by 1): Java System properties.</li>
 * <li>All (except include path if set by 1): Environment variables.</li>
 * <li>All (when 1, 2, and 3 are not set): Geosupport home used for base path.</li>
 * </ol>
 * <p>
 * Note that Geosupport home is used as a base path by other defaulted properties
 * whether or not it has itself been defaulted.
 * </p>
 */
public class GeosupportConfigResolver extends AbstractConfigResolver {

    public static final String DEFAULT_HOME_LINUX = "/opt/geosupport/current";
    public static final String DEFAULT_HOME_WINDOWS = "c:" + DEFAULT_HOME_LINUX;

    static final String GS_GEOFILES_ENVVAR = "GEOFILES";
    static final String GS_GEOFILES_SYSTEM = "gs.geofiles";
    static final String GS_HOME_ENVVAR = "GEOSUPPORT_HOME";
    static final String GS_HOME_SYSTEM = "gs.home";
    static final String GS_INCLUDE_PATH_ENVVAR = "GS_INCLUDE_PATH";
    static final String GS_INCLUDE_PATH_SYSTEM = "gs.include.path";
    static final String GS_LIBRARY_PATH_ENVVAR = "GS_LIBRARY_PATH";
    static final String GS_LIBRARY_PATH_SYSTEM = "gs.library.path";

    public String getIncludePath() {
        String includePath = getSystemProperty(GS_INCLUDE_PATH_SYSTEM);
        if (includePath != null) {
            return includePath;
        }
        includePath = getEnv(GS_INCLUDE_PATH_ENVVAR);
        if (includePath != null) {
            return includePath;
        }
        return getHome() + "/include";
    }

    public String getLibraryPath() {
        String libraryPath = getSystemProperty(GS_LIBRARY_PATH_SYSTEM);
        if (libraryPath != null) {
            return libraryPath;
        }
        libraryPath = getEnv(GS_LIBRARY_PATH_ENVVAR);
        if (libraryPath != null) {
            return libraryPath;
        }
        return getHome() + "/lib";
    }

    public String getGeofiles() {
        String geofiles = getSystemProperty(GS_GEOFILES_SYSTEM);
        if (geofiles != null) {
            return geofiles;
        }
        geofiles = getEnv(GS_GEOFILES_ENVVAR);
        if (geofiles != null) {
            return geofiles;
        }
        // Trailing slash is required!
        return getHome() + "/fls/";
    }

    public String getHome() {
        String geosupportHome = getSystemProperty(GS_HOME_SYSTEM);
        if (geosupportHome != null) {
            return geosupportHome;
        }
        geosupportHome = getEnv(GS_HOME_ENVVAR);
        if (geosupportHome != null) {
            return geosupportHome;
        }
        if (isWindows()) {
           return DEFAULT_HOME_WINDOWS;
        }
        return DEFAULT_HOME_LINUX;
    }
}