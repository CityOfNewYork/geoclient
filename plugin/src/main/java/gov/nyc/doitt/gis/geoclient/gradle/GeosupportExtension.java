package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.computed;
import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.defaulted;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.environment;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.gradle;

import org.gradle.api.Project;

public class GeosupportExtension extends BaseRuntimePropertyExtension {

    // @formatter:off
    public static final boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
    public static final String GEOSUPPORT_CONTAINER_ITEM_HOME = "home";
    public static final String GEOSUPPORT_CONTAINER_ITEM_GEOFILES = "geofiles";
    public static final String GEOSUPPORT_CONTAINER_ITEM_LIBRARY_PATH = "libraryPath";

    public static final String GEOSUPPORT_DEFAULT_LINUX_HOME = "/opt/geosupport";
    public static final String GEOSUPPORT_DEFAULT_WINDOWS_HOME = "c:/lib/geosupport/current";
    public static final String GEOSUPPORT_DEFAULT_HOME = isWindows ? GEOSUPPORT_DEFAULT_WINDOWS_HOME : GEOSUPPORT_DEFAULT_LINUX_HOME;
    public static final String GEOSUPPORT_DEFAULT_GEOFILES = GEOSUPPORT_DEFAULT_HOME + "/fls/"; // Trailing slash required!
    public static final String GEOSUPPORT_DEFAULT_LIBRARY_PATH = GEOSUPPORT_DEFAULT_HOME + "/lib";;

    public static final String GEOSUPPORT_ENV_VAR_GEOFILES = "GEOFILES";
    public static final String GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME = "GEOSUPPORT_HOME";
    public static final String GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH = "GS_LIBRARY_PATH";

    public static final String GEOSUPPORT_GRADLE_PROPERTY_NAME_HOME = "gsHome";
    public static final String GEOSUPPORT_GRADLE_PROPERTY_NAME_GEOFILES = "gsGeofiles";
    public static final String GEOSUPPORT_GRADLE_PROPERTY_NAME_LIBRARY_PATH = "gsLibraryPath";
    // @formatter:on

    public GeosupportExtension(String name, Project project) {
        super(name, project);
        add(createHome());
        add(createGeofiles());
        add(createLibraryPath());
    }

    RuntimeProperty createHome() {
        RuntimeProperty runtimeProperty = create(GEOSUPPORT_CONTAINER_ITEM_HOME);
        runtimeProperty.defaultTo(new PropertySource(GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME, GEOSUPPORT_DEFAULT_HOME, environment, defaulted));
        runtimeProperty.exportAs(new PropertySource(GEOSUPPORT_GRADLE_PROPERTY_NAME_HOME, GEOSUPPORT_DEFAULT_HOME, gradle, computed));
        return runtimeProperty;
    }

    RuntimeProperty createGeofiles() {
        RuntimeProperty runtimeProperty = create(GEOSUPPORT_CONTAINER_ITEM_GEOFILES);
        runtimeProperty.defaultTo(new PropertySource(GEOSUPPORT_ENV_VAR_GEOFILES, GEOSUPPORT_DEFAULT_GEOFILES, environment, defaulted));
        runtimeProperty.exportAs(new PropertySource(GEOSUPPORT_GRADLE_PROPERTY_NAME_GEOFILES, GEOSUPPORT_DEFAULT_GEOFILES, gradle, computed));
        return runtimeProperty;
    }

    RuntimeProperty createLibraryPath() {
        RuntimeProperty runtimeProperty = create(GEOSUPPORT_CONTAINER_ITEM_LIBRARY_PATH);
        runtimeProperty.defaultTo(new PropertySource(GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH, GEOSUPPORT_DEFAULT_LIBRARY_PATH, environment, defaulted));
        runtimeProperty.exportAs(new PropertySource(GEOSUPPORT_GRADLE_PROPERTY_NAME_LIBRARY_PATH, GEOSUPPORT_DEFAULT_LIBRARY_PATH, gradle, computed));
        return runtimeProperty;
    }

}
