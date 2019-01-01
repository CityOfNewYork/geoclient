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
    // TODO
    //public static final String SYSPROP_JAVA_IO_TEMPDIR = "java.io.tempdir";

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
    /*
    task showMe {
        doLast() {
            logger.lifecycle("Gradle Properties:")
            logger.lifecycle("      enableJavadoc: ${enableJavadoc}")
            logger.lifecycle("             nojdoc: ${nojdoc}")
            logger.lifecycle("       gcJniVersion: ${gcJniVersion}")
            logger.lifecycle("      gsIncludePath: ${gsIncludePath}")
            logger.lifecycle("      gsLibraryPath: ${gsLibraryPath}")
            logger.lifecycle("         gsGeofiles: ${gsGeofiles}")

            logger.lifecycle("Environment:")
            logger.lifecycle("    GEOSUPPORT_HOME: ${System.getenv('GEOSUPPORT_HOME')}")
            logger.lifecycle("           GEOFILES: ${System.getenv('GEOFILES')}")
            logger.lifecycle("    LD_LIBRARY_PATH: ${System.getenv('LD_LIBRARY_PATH')}")
            logger.lifecycle("               PATH: ${System.getenv('PATH')}")

            logger.lifecycle("Java SystemProperties:")
            logger.lifecycle("     gc.jni.version: ${System.getProperty('gc.jni.version')}")
            logger.lifecycle("  java.library.path: ${System.getProperty('java.library.path')}")
            logger.lifecycle("     java.io.tmpdir: ${System.getProperty('java.io.tmpdir')}")
        }
    }
    */
}
