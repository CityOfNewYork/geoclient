package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.environment;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.system;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class GeoclientPlugin implements Plugin<Project> {

    private static final Logger logger = Logging.getLogger(GeoclientPlugin.class);

    // @formatter:off
    public static final boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");

    public static final String DEFAULT_REPORT_FILE_NAME_FORMAT = "%s-runtime-properties.txt";
    public static final String RUNTIME_REPORT_TASK_NAME_FORMAT = "%sRuntimeReport";

    public static final String SYSPROP_JAVA_IO_TEMPDIR = "java.io.tempdir";
    public static final String SYSPROP_JAVA_LIBRARY_PATH = "java.library.path";

    public static final String GEOCLIENT_CONTAINER_NAME = "geoclient";
    public static final String GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR = "nativeTempDir";

    public static final String GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR = "temp/jni";

    public static final String GEOCLIENT_GRADLE_PROPERTY_NAME_NATIVE_TEMP_DIR = "gcNativeTempDir";

    public static final String GEOCLIENT_REPORT_FILE_NAME = String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, GEOCLIENT_CONTAINER_NAME);
    public static final String GEOCLIENT_REPORT_TASK_NAME = String.format(RUNTIME_REPORT_TASK_NAME_FORMAT, GEOCLIENT_CONTAINER_NAME);

    public static final String GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR = "gc.jni.version";

    public static final String GEOSUPPORT_CONTAINER_NAME = "geosupport";
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

    public static final String GEOSUPPORT_REPORT_FILE_NAME = String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, GEOSUPPORT_CONTAINER_NAME);
    public static final String GEOSUPPORT_REPORT_TASK_NAME = String.format(RUNTIME_REPORT_TASK_NAME_FORMAT, GEOSUPPORT_CONTAINER_NAME);
    // @formatter:on

    public void apply(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> geoclientContainer = createGeoclientContainer(project);
        logger.info("Geoclient RuntimeProperty container configured successfully");
        createRuntimeReportTask(project, GEOCLIENT_REPORT_TASK_NAME, GEOCLIENT_CONTAINER_NAME, geoclientContainer);
        NamedDomainObjectContainer<RuntimeProperty> geosupportContainer = createGeosupportContainer(project);
        logger.info("Geosupport RuntimeProperty container configured successfully");
        createRuntimeReportTask(project, GEOSUPPORT_REPORT_TASK_NAME, GEOSUPPORT_CONTAINER_NAME, geosupportContainer);
    }

    void createRuntimeReportTask(Project project, String taskName, String fileName,
            NamedDomainObjectContainer<RuntimeProperty> container) {
        project.getTasks().create(taskName, RuntimePropertyReport.class, fileName, container, project);
    }

    NamedDomainObjectContainer<RuntimeProperty> createGeoclientContainer(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> container = project.container(RuntimeProperty.class,
                new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        RuntimeProperty result = new RuntimeProperty(name, project.getObjects());
                        logger.debug("Created RuntimeProperty {}", result);
                        return result;
                    }
                });
        nativeTempDirDefaults(project, container);
        project.getExtensions().add(GEOCLIENT_CONTAINER_NAME, container);
        return container;
    }

    void nativeTempDirDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        RuntimeProperty nativeTempDir = container.create(GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR);
        String defaultDir = FormatUtils.normalize(project.getBuildDir(), GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR);
        PropertySource source = new PropertySource(project.getObjects());
        source.defaultTo(GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR, defaultDir, system);
        logDefaultInstanceCreationOf(source);
        nativeTempDir.defaultTo(source, GEOCLIENT_GRADLE_PROPERTY_NAME_NATIVE_TEMP_DIR);
        logDefaultInstanceCreationOf(nativeTempDir);
        logger.quiet(source.format());
    }

    NamedDomainObjectContainer<RuntimeProperty> createGeosupportContainer(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> container = project.container(RuntimeProperty.class,
                new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        // logger.quiet("BBBBBBBBBBBBBBBBBBBBBBBB");
        geosupportGeofilesDefaults(project, container);
        // logger.quiet("AAAAAAAAAAAAAAAAAAAAAAAA");
        geosupportHomeDefaults(project, container);
        geosupportlibraryPathDefaults(project, container);
        project.getExtensions().add(GEOSUPPORT_CONTAINER_NAME, container);
        return container;
    }

    void geosupportGeofilesDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        PropertySource source = new PropertySource(project.getObjects());
        source.defaultTo(GEOSUPPORT_ENV_VAR_GEOFILES, GEOSUPPORT_DEFAULT_GEOFILES, environment);
        RuntimeProperty geofiles = container.create(GEOSUPPORT_CONTAINER_ITEM_GEOFILES);
        geofiles.defaultTo(source, GEOSUPPORT_GRADLE_PROPERTY_NAME_GEOFILES);
    }

    void geosupportHomeDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        PropertySource source = new PropertySource(project.getObjects());
        source.defaultTo(GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME, GEOSUPPORT_DEFAULT_HOME, environment);
        RuntimeProperty home = container.create(GEOSUPPORT_CONTAINER_ITEM_HOME);
        home.defaultTo(source, GEOSUPPORT_GRADLE_PROPERTY_NAME_HOME);
    }

    void geosupportlibraryPathDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        PropertySource source = new PropertySource(project.getObjects());
        source.defaultTo(GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH, GEOSUPPORT_DEFAULT_LIBRARY_PATH, environment);
        RuntimeProperty libraryPath = container.create(GEOSUPPORT_CONTAINER_ITEM_LIBRARY_PATH);
        libraryPath.defaultTo(source, GEOSUPPORT_GRADLE_PROPERTY_NAME_LIBRARY_PATH);
    }

    private void logDefaultInstanceCreationOf(Object instance) {
        logger.info("Created default {} instance {}", instance.getClass().getName(), instance);
    }

}