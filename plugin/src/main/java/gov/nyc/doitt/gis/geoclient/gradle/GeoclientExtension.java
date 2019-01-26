package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.computed;
import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.defaulted;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.gradle;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.system;

import java.io.File;

import org.gradle.api.Project;

public class GeoclientExtension extends BaseRuntimePropertyExtension {

    public static final String GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR = "nativeTempDir";
    public static final String GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR = "temp/jni";
    public static final String GEOCLIENT_GRADLE_PROPERTY_NAME_NATIVE_TEMP_DIR = "gcNativeTempDir";
    public static final String GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR = "gc.jni.version";

    public GeoclientExtension(String name, Project project) {
        super(name, project);
        add(createNativeTempDir());
    }

    RuntimeProperty createNativeTempDir() {
        RuntimeProperty runtimeProperty = create(GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR);
        runtimeProperty.defaultTo(new PropertySource(GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR, new File(getBuildDir(), GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR), system, defaulted));
        runtimeProperty.exportAs(new PropertySource(GEOCLIENT_GRADLE_PROPERTY_NAME_NATIVE_TEMP_DIR, new File(getBuildDir(), GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR), gradle, computed));
        return runtimeProperty;
    }

}
