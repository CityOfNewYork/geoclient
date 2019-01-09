package gov.nyc.doitt.gis.geoclient.gradle;

import gov.nyc.doitt.gis.geoclient.gradle.ctx.EnvironmentVariables;

public interface GeosupportEnvironmentVariables extends EnvironmentVariables {

    default String getGeofiles() {
        return EnvironmentVariables.getEnvironmentVariable("GEOFILES");
    }

    default String getHome() {
        return EnvironmentVariables.getEnvironmentVariable("GEOSUPPORT_HOME");
    }

    default String getLibraryPath() {
        return EnvironmentVariables.getEnvironmentVariable("GS_LIBRARY_PATH");
    }
}
