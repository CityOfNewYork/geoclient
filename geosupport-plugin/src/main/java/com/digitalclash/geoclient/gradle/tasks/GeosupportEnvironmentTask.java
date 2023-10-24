package com.digitalclash.geoclient.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

public abstract class GeosupportEnvironmentTask extends DefaultTask {

    private final Property<Boolean> validateBuildtime = getProject().getObjects().property(Boolean.class);
    private final Property<Boolean> validateRuntime = getProject().getObjects().property(Boolean.class);

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
    public final Property<Boolean> isValidateBuildtime() {
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
    public final Property<Boolean> isValidateRuntime() {
        return validateRuntime;
    }
}
