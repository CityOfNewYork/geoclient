package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.DEFAULTED;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.SYSTEM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;

public class GeoclientExtension extends AbstractRuntimePropertyExtension {

    public static final String GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR = "nativeTempDir";
    public static final String GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR = "temp/jni";
    public static final String GEOCLIENT_GRADLE_PROPERTY_NAME_NATIVE_TEMP_DIR = "gcNativeTempDir";
    public static final String GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR = "gc.jni.version";

    public GeoclientExtension(String name, Resolver resolver, Project project) {
        super(name, resolver, project);
    }

    @Override
    protected List<DeferredContainerItemInfo> getContainerItems() {
        List<DeferredContainerItemInfo> result = new ArrayList<>();
        result.add(new DeferredContainerItemInfo(GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR,
                new PropertySource(GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR,
                        new File(getBuildDir(), GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR), SYSTEM, DEFAULTED)));
        return result;
    }

}
