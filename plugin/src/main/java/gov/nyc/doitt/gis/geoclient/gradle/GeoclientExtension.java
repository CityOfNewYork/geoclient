package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.defaulted;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.system;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public class GeoclientExtension extends AbstractRuntimePropertyExtension {

    public static final String GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR = "nativeTempDir";
    public static final String GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR = "temp/jni";
    public static final String GEOCLIENT_GRADLE_PROPERTY_NAME_NATIVE_TEMP_DIR = "gcNativeTempDir";
    public static final String GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR = "gc.jni.version";

    private final Logger logger;

    public GeoclientExtension(String name, Project project) {
        super(name, project);
        logger = project.getLogger();
        logger.lifecycle("'{}' constructed", getClass().getName());
    }

    @Override
    protected List<DeferredContainerItemInfo> getContainerItems() {
        List<DeferredContainerItemInfo> result = new ArrayList<>();
        result.add(new DeferredContainerItemInfo(GEOCLIENT_CONTAINER_ITEM_NATIVE_TEMP_DIR, new PropertySource(
                GEOCLIENT_SYSPROP_NATIVE_TEMP_DIR, GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR, system, defaulted)));
        return result;
    }

}
