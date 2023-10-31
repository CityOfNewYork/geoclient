/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.digitalclash.geoclient.gradle.tasks;

import com.digitalclash.geoclient.gradle.GeosupportExtension;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
//import org.gradle.api.tasks.PathSensitive;
//import org.gradle.api.tasks.PathSensitivity;
//import org.gradle.api.tasks.TaskAction;

public abstract class GeosupportEnvironmentTask extends DefaultTask implements GeosupportExtensionAware {

    private final GeosupportExtension geosupport;
    private final Property<Boolean> validateBuildtime;
    private final Property<Boolean> validateRuntime;

    public GeosupportEnvironmentTask() {
        geosupport= getProject().getObjects().newInstance(GeosupportExtension.class);
        validateBuildtime = getProject().getObjects().property(Boolean.class);
        validateRuntime = getProject().getObjects().property(Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void geosupport(Action<? super GeosupportExtension> action) {
        action.execute(geosupport);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeosupportExtension getGeosupport() {
        return geosupport;
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
