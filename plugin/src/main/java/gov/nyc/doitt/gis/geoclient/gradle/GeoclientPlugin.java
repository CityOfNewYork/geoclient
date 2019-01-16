package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.environment;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.system;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class GeoclientPlugin implements Plugin<Project> {

    private static final Logger logger = Logging.getLogger(GeoclientPlugin.class);

    // @formatter:off
    public static final boolean isLinux = "isLinux".equals(System.getProperty("os.name").toLowerCase());

    public static final String GEOCLIENT_CONTAINER_NAME = "geoclient";
    public static final String GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR = "nativeTempDir";
    public static final String GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR = "temp/jni";
    public static final String GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR = "gc.jni.version";

    public static final String GEOSUPPORT_CONTAINER_NAME = "geosupport";
    public static final String GEOSUPPORT_CONTAINER_ITEM_HOME = "home";
    public static final String GEOSUPPORT_CONTAINER_ITEM_GEOFILES = "geofiles";
    public static final String GEOSUPPORT_CONTAINER_ITEM_LIBRARY_PATH = "libraryPath";
    public static final String GEOSUPPORT_DEFAULT_LINUX_HOME = "/opt/geosupport";
    public static final String GEOSUPPORT_DEFAULT_WINDOWS_HOME = "c:/lib/geosupport/current";
    public static final String GEOSUPPORT_DEFAULT_HOME = isLinux ? GEOSUPPORT_DEFAULT_LINUX_HOME : GEOSUPPORT_DEFAULT_WINDOWS_HOME;
    public static final String GEOSUPPORT_DEFAULT_GEOFILES = GEOSUPPORT_DEFAULT_HOME + "/fls/"; // Trailing slash required!
    public static final String GEOSUPPORT_DEFAULT_LIBRARY_PATH = GEOSUPPORT_DEFAULT_HOME + "/lib";;
    public static final String GEOSUPPORT_ENV_VAR_GEOFILES = "GEOFILES";
    public static final String GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME = "GEOSUPPORT_HOME";
    public static final String GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH = "GS_LIBRARY_PATH";

    public static final String SYSPROP_JAVA_IO_TEMPDIR = "java.io.tempdir";
    public static final String SYSPROP_JAVA_LIBRARY_PATH = "java.library.path";
    // @formatter:on

    public void apply(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> geoclientContainer = createGeoclientContainer(project);
        geoclientContainer.all(new Action<RuntimeProperty>() {
            @Override
            public void execute(RuntimeProperty property) {
                String name = property.getName();
                String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
                String taskName = "show" + capitalizedName;
                project.getTasks().register(taskName, RuntimePropertyReport.class, new Action<RuntimePropertyReport>() {
                    public void execute(RuntimePropertyReport task) {
                    }
                });
                logger.quiet("");
            }
        });
        logger.info("GeoclientExtension configured successfully");

        NamedDomainObjectContainer<RuntimeProperty> geosupportContainer = createGeosupportContainer(project);
        logger.info("GeosupportExtension configured successfully");

        // project.getTasks().create("geosupportInfo", GeosupportInfo.class, project);
    }

    NamedDomainObjectContainer<RuntimeProperty> createGeoclientContainer(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> container = project.container(RuntimeProperty.class,
                new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        nativeTempDirDefaults(project, container);
        project.getExtensions().add(GEOCLIENT_CONTAINER_NAME, container);
        return container;
    }

    void nativeTempDirDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        RuntimeProperty nativeTempDir = container.create(GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR);
        PropertySource source = nativeTempDir.getSource().get();
        File dir = project.getLayout().getBuildDirectory().get().dir(GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR)
                .getAsFile();
        source.defaultTo(GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR, dir, system);
    }

    NamedDomainObjectContainer<RuntimeProperty> createGeosupportContainer(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> container = project.container(RuntimeProperty.class,
                new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        geosupportGeofilesDefaults(project, container);
        geosupportHomeDefaults(project, container);
        geosupportlibraryPathDefaults(project, container);
        project.getExtensions().add(GEOSUPPORT_CONTAINER_NAME, container);
        return container;
    }

    void geosupportGeofilesDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        RuntimeProperty geofiles = container.create(GEOSUPPORT_CONTAINER_ITEM_GEOFILES);
        PropertySource source = geofiles.getSource().get();
        source.defaultTo(GEOSUPPORT_ENV_VAR_GEOFILES, GEOSUPPORT_DEFAULT_GEOFILES, environment);
    }

    void geosupportHomeDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        RuntimeProperty home = container.create(GEOSUPPORT_CONTAINER_ITEM_HOME);
        PropertySource source = home.getSource().get();
        source.defaultTo(GEOSUPPORT_ENV_VAR_GEOSUPPORT_HOME, GEOSUPPORT_DEFAULT_HOME, environment);
    }

    void geosupportlibraryPathDefaults(Project project, NamedDomainObjectContainer<RuntimeProperty> container) {
        RuntimeProperty libraryPath = container.create(GEOSUPPORT_CONTAINER_ITEM_LIBRARY_PATH);
        PropertySource source = libraryPath.getSource().get();
        source.defaultTo(GEOSUPPORT_ENV_VAR_GS_LIBRARY_PATH, GEOSUPPORT_DEFAULT_LIBRARY_PATH, environment);
    }

}