package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.BuildConfigurationResolver.ENV_VAR_GEOFILES;
import static gov.nyc.doitt.gis.geoclient.gradle.BuildConfigurationResolver.SYSPROP_GC_NATIVE_TEMP_DIR;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.testing.Test;

public class GeoclientPlugin implements Plugin<Project> {

    public static final String SYSPROP_JAVA_LIBRARY_PATH = "java.library.path";

    public void apply(Project project) {
        final GeoclientExtension geoclient = project.getExtensions().create("geoclient", GeoclientExtension.class, project);
        final GeosupportExtension geosupport = project.getExtensions().create("geosupport", GeosupportExtension.class, project);
        final BuildConfigurationResolver resolver = new BuildConfigurationResolver(project, geoclient, geosupport);

        project.getTasks().withType(Test.class).configureEach(new Action<Test>() {
            public void execute(Test test) {
                test.systemProperty(SYSPROP_GC_NATIVE_TEMP_DIR, resolver.resolveGeoclientNativeTempDir());
                test.systemProperty(SYSPROP_JAVA_LIBRARY_PATH, resolver.resolveGeosupportLibraryPath());
                test.environment(ENV_VAR_GEOFILES,  resolver.resolveGeosupportGeofiles());
            }
        });
    }
}
