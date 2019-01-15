package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.testing.Test;

public class GeoclientPlugin implements Plugin<Project> {

    private static final Logger logger = Logging.getLogger(GeoclientPlugin.class);

    public static final String SYSPROP_JAVA_IO_TEMPDIR = "java.io.tempdir";
    public static final String SYSPROP_JAVA_LIBRARY_PATH = "java.library.path";
    public static final String DEFAULT_SUBDIR_GS_GEOFILES = "fls"; // Requires trailing slash!
    public static final String DEFAULT_SUBDIR_GS_LIBRARY_PATH = "lib";
    public static final String ENV_VAR_GEOFILES = "GEOFILES";
    public static final String ENV_VAR_GEOSUPPORT_HOME = "GEOSUPPORT_HOME";
    public static final String ENV_VAR_GS_LIBRARY_PATH = "GS_LIBRARY_PATH";
    public static final String SYSPROP_GC_NATIVE_TEMP_DIR = "gc.jni.version";

    public void apply(Project project) {
        NamedDomainObjectContainer<RuntimeProperty> geoclientPropertyContainer = project
                .container(RuntimeProperty.class, new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        // project.getConvention().
        project.getExtensions().add("geoclient", geoclientPropertyContainer);

        NamedDomainObjectContainer<RuntimeProperty> geosupportPropertyContainer = project
                .container(RuntimeProperty.class, new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        project.getExtensions().add("geosupport", geosupportPropertyContainer);

        final GeoclientExtension geoclient = createGeoclientExtension(project);
        logger.info("GeoclientExtension configured successfully");
        final GeosupportExtension geosupport = configureGeosupportExtension(project);
        logger.info("GeosupportExtension configured successfully");

        project.getTasks().withType(Test.class).configureEach(new Action<Test>() {
            public void execute(Test test) {
                test.systemProperty(SYSPROP_GC_NATIVE_TEMP_DIR, geoclient.getNativeTempDir());
                test.systemProperty(SYSPROP_JAVA_LIBRARY_PATH, geosupport.getLibraryPath());
                test.environment(ENV_VAR_GEOFILES, geosupport.getGeofiles());
            }
        });

        project.getTasks().create("geosupportInfo", GeosupportInfo.class, project);
    }

    GeoclientExtension createGeoclientExtension(Project project) {
        logger.debug("Configuring GeoclientExtension...");
        return project.getExtensions().create("geoclient", GeoclientExtension.class, project.getObjects());
    }

    GeosupportExtension configureGeosupportExtension(Project project) {
        logger.debug("Configuring GeosupportExtension...");
        return project.getExtensions().create("geosupport", GeosupportExtension.class, project.getObjects());
    }
}
