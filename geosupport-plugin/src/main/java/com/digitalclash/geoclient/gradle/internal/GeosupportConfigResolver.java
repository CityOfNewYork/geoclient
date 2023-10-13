package com.digitalclash.geoclient.gradle.internal;

import java.io.File;

import static com.digitalclash.geoclient.gradle.internal.AbstractConfigResolver.isWindows;

public class GeosupportConfigResolver extends AbstractConfigResolver {

    public static final String DEFAULT_HOME = isWindows() ? "c:/opt/geosupport/current" : "/opt/geosupport/current";

    static final String GS_GEOFILES_ENVVAR = "GEOFILES";
    static final String GS_GEOFILES_SYSTEM = "gs.geofiles";
    static final String GS_HOME_ENVVAR = "GEOSUPPORT_HOME";
    static final String GS_HOME_SYSTEM = "gs.home";
    static final String GS_INCLUDE_PATH_ENVVAR = "GS_INCLUDE_PATH";
    static final String GS_INCLUDE_PATH_SYSTEM = "gs.include.path";
    static final String GS_LIBRARY_PATH_ENVVAR = "GS_LIBRARY_PATH";
    static final String GS_LIBRARY_PATH_SYSTEM = "gs.library.path";

    private final File geosupportHome;
    private final File includePath;

    public GeosupportConfigResolver(File geosupportHome, File includePath) {
        this.geosupportHome = geosupportHome;
        this.includePath = includePath;
    }

    public GeosupportConfigResolver(File geosupportHome) {
        this(geosupportHome, null);
    }

    public GeosupportConfigResolver() {
        this(null, null);
    }

    public File getIncludePath() {
        if (this.includePath != null) {
            return this.includePath;
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
        if (this.geosupportHome != null) {
            return this.geosupportHome.getAbsolutePath();
        }
        String geosupportHome = getSystemProperty(GS_HOME_SYSTEM);
        if (geosupportHome != null) {
            return geosupportHome;
        }
        geosupportHome = getEnv(GS_HOME_ENVVAR);
        if (geosupportHome != null) {
            return geosupportHome;
        }
        return DEFAULT_HOME;
    }
}