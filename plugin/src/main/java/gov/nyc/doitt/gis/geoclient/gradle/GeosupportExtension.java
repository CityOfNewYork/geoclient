package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.DEFAULTED;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.ENVIRONMENT;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;

public class GeosupportExtension extends AbstractRuntimePropertyExtension {

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

    public GeosupportExtension(String name, Resolver resolver, Project project) {
        super(name, resolver, project);
    }

    @Override
    protected List<DeferredContainerItemInfo> getContainerItems() {
        List<DeferredContainerItemInfo> result = new ArrayList<>();
        // GEOSUPPORT_HOME
        result.add(new DeferredContainerItemInfo(GEOSUPPORT_CONTAINER_ITEM_HOME, createHome()));
        // GEOFILES
        result.add(new DeferredContainerItemInfo(GEOSUPPORT_CONTAINER_ITEM_GEOFILES, createGeofiles()));
        // GS_LIBRARY_PATH
        result.add(new DeferredContainerItemInfo(GEOSUPPORT_CONTAINER_ITEM_LIBRARY_PATH, createLibraryPath()));

        return result;
    }

    PropertySource createHome() {
        return new PropertySource(GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME, GEOSUPPORT_DEFAULT_HOME, ENVIRONMENT, DEFAULTED);
    }

    PropertySource createGeofiles() {
        return new PropertySource(GEOSUPPORT_ENV_VAR_GEOFILES, GEOSUPPORT_DEFAULT_GEOFILES, ENVIRONMENT, DEFAULTED);
    }

    PropertySource createLibraryPath() {
        return new PropertySource(GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH, GEOSUPPORT_DEFAULT_LIBRARY_PATH, ENVIRONMENT,
                DEFAULTED);
    }

}
