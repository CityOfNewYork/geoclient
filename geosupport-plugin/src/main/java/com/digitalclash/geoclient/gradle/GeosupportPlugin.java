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
package com.digitalclash.geoclient.gradle;

import java.io.File;

import com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver;
import com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver;
import com.digitalclash.geoclient.gradle.tasks.GeosupportExtensionAware;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.model.ObjectFactory;

/**
 * Provides configurable Geosupport build and runtime configuration with
 * sensible defaults.
 */
public class GeosupportPlugin implements Plugin<Project> {

    private Logger logger = Logging.getLogger(GeosupportPlugin.class);

    @Override
    public void apply(final Project project) {
        GeosupportApplication geosupportApplication = createGeosupportApplication(project);
        configureGeosupportExtensionAwareTasks(project, geosupportApplication.getGeosupport());
        GeoclientExtension geoclient = createGeoclientExtension(project);
    }

    private GeoclientExtension createGeoclientExtension(final Project project) {
        GeoclientExtension geoclient = project.getExtensions().create("geoclient", GeoclientExtension.class);
        geoclient.getJniVersion().convention(new GeoclientConfigResolver().getJniVersion());
        return geoclient;
    }

    private GeosupportApplication createGeosupportApplication(final Project project) {
        GeosupportApplication geosupportApplication = project.getExtensions().create("geosupportApplication", GeosupportApplication.class, project.getObjects());
        logger.quiet("Created: geosupportApplication.getGeosupport(): {}.", geosupportApplication.getGeosupport());
        return geosupportApplication;
    }

    //
    // Based on https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/DockerRemoteApiPlugin.java
    //
    private void configureGeosupportExtensionAwareTasks(Project project, final GeosupportExtension geosupportExtension) {
        project.getTasks().withType(GeosupportExtensionAware.class).configureEach(new Action<GeosupportExtensionAware>() {
            @Override
            public void execute(GeosupportExtensionAware task) {
                task.getGeosupport().getGeofiles().convention(geosupportExtension.getGeofiles());
                task.getGeosupport().getHome().convention(geosupportExtension.getHome());
                task.getGeosupport().getIncludePath().convention(geosupportExtension.getIncludePath());
                task.getGeosupport().getLibraryPath().convention(geosupportExtension.getLibraryPath());
            }
        });
    }
}
