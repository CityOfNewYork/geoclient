package gov.nyc.doitt.gis.geoclient.gradle;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.testing.Test;
public class GeoclientPlugin implements Plugin<Project> {
    public static final String ENV_VAR_GEOFILES = "GEOFILES";
    public static final String ENV_VAR_GS_HOME = "GEOSUPPORT_HOME";
    public static final String PROP_GC_JNI_VERSION = "gcJniVersion";
    public static final String PROP_GS_HOME = "gsHome";
    public static final String PROP_GS_GEOFILES = "gsGeofiles";
    public static final String PROP_GS_LIBRARY_PATH = "gsLibraryPath";
    public static final String SYSPROP_GC_JNI_VERSION = "gc.jni.version";
    public static final String SYSPROP_JAVA_LIBRARY_PATH = "java.library.path";
    public void apply(Project project) {
        final GeosupportExtension geosupportExtension = project.getExtensions().create("geosupport", GeosupportExtension.class, project);
        final BuildConfigurationResolver resolver = new BuildConfigurationResolver(project);
//        resolveGeosupportHome(project, geosupportExtension, resolver);
        project.getTasks().withType(Test.class).configureEach(new Action<Test>() {
            public void execute(Test test) {
                test.systemProperty(SYSPROP_GC_JNI_VERSION, resolver.resolve(PROP_GC_JNI_VERSION, SYSPROP_GC_JNI_VERSION, null));
                Property<String> libPath = geosupportExtension.getLibraryPath();
                test.systemProperty(SYSPROP_JAVA_LIBRARY_PATH, resolver.resolve(PROP_GS_LIBRARY_PATH, SYSPROP_JAVA_LIBRARY_PATH, libPath.getOrNull()));
                Property<String> geofiles = geosupportExtension.getGeofiles();
                test.environment(ENV_VAR_GEOFILES, resolver.resolveFromEnv(PROP_GS_GEOFILES, ENV_VAR_GEOFILES, geofiles.getOrNull()));
            }
        });
    }

    boolean resolveGeosupportHome(Project project, GeosupportExtension geosupportExtension, BuildConfigurationResolver resolver) {
        String defaultValue = geosupportExtension.getHome().getOrNull();
        // 
        String geosupportHome = resolver.resolve(PROP_GS_HOME, ENV_VAR_GS_HOME, null);
        return (geosupportHome != null && !geosupportHome.isEmpty());
    }
    boolean resolveGeosupportLibraryPath(Project project, GeosupportExtension geosupportExtension, BuildConfigurationResolver resolver) {
        String defaultValue = null;
        if(project.hasProperty(PROP_GS_HOME)) {
            defaultValue = new File(project.property(PROP_GS_HOME), "lib").getCanonicalPath();
        }
        String geosupportLibraryPath = resolver.resolve(PROP_GS_HOME, ENV_VAR_GS_HOME, null);
        return false;
    }
    boolean resolveGeosupportGeofiles(Project project, GeosupportExtension geosupportExtension, BuildConfigurationResolver resolver) {
        
        return false;
    }
}