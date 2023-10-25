package com.digitalclash.geoclient.gradle.tasks;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

import com.digitalclash.geoclient.gradle.GeosupportApplication;
import com.digitalclash.geoclient.gradle.GeosupportExtension;
import com.digitalclash.geoclient.gradle.GeosupportIntegrationTestOptions;

public abstract class GeosupportEnvironmentTask extends DefaultTask implements GeosupportApplicationAware {

    private final GeosupportApplication geosupportApplication;
    private final Property<Boolean> validateBuildtime;
    private final Property<Boolean> validateRuntime;

    public GeosupportEnvironmentTask() {
        geosupportApplication = getProject().getObjects().newInstance(GeosupportApplication.class);
        validateBuildtime = getProject().getObjects().property(Boolean.class);
        validateRuntime = getProject().getObjects().property(Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void geosupportApplication(Action<? super GeosupportApplication> action) {
        action.execute(geosupportApplication);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeosupportApplication getGeosupportApplication() {
        return geosupportApplication;
    }

    /**
     * Validate build time configuration. Defaults to false.
     * Geosupport build time configuration:
     * <ul>
     * <li>include path</li>
     * <li>library path</li>
     * </ul>
     */
    @Input
    @Optional
    public final Property<Boolean> getValidateBuildtime() {
        return validateBuildtime;
    }

    /**
     * Validate runtime configuration. Defaults to false.
     * Geosupport runtime configuration:
     * <ul>
     * <li>GEOFILES environment variable</li>
     * <li>library path</li>
     * </ul>
     */
    @Input
    @Optional
    public final Property<Boolean> getValidateRuntime() {
        return validateRuntime;
    }
}
