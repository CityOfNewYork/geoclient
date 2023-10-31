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

import com.digitalclash.geoclient.gradle.tasks.GeosupportIntegrationTest;
import com.digitalclash.geoclient.gradle.tasks.IntegrationTestOptionsAware;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;

public class GeosupportIntegrationTestPlugin implements Plugin<Project> {

    public static final String INTEGRATION_TEST_OPTIONS_EXTENSION_NAME = "integrationTestOptions";

    private Logger logger = Logging.getLogger(GeosupportIntegrationTestPlugin.class);

    @Override
    public void apply(final Project project) {
        project.getPlugins().apply(GeosupportPlugin.class);
        GeosupportApplication geosupportApplication = project.getExtensions().getByType(GeosupportApplication.class);
        final IntegrationTestOptions integrationTestOptions = ((ExtensionAware)geosupportApplication).getExtensions().create(INTEGRATION_TEST_OPTIONS_EXTENSION_NAME, IntegrationTestOptions.class, project.getObjects());
        configureIntegrationTestOptionsAwareTasks(project, integrationTestOptions, logger);

        final GeosupportExtension geosupportExtension = geosupportApplication.getGeosupport();
        logger.quiet("[ITEST] integrationTestOptions: {}.", integrationTestOptions);

        project.getPlugins().withType(JavaPlugin.class).configureEach(javaPlugin -> {
            SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            SourceSet sourceSet = sourceSets.create(integrationTestOptions.getSourceSetName().get());
            sourceSet.getJava().srcDir(integrationTestOptions.getJavaSourceDir());
            sourceSet.getResources().srcDir(integrationTestOptions.getResourcesSourceDir());
            sourceSet.getCompileClasspath().plus(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getOutput());
            sourceSet.getRuntimeClasspath().plus(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getOutput());
            Configuration implementation = project.getConfigurations().getByName(sourceSet.getImplementationConfigurationName());
            implementation.extendsFrom(project.getConfigurations().getByName(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME));
            Configuration runtimeOnly = project.getConfigurations().getByName(sourceSet.getRuntimeOnlyConfigurationName());
            TaskProvider<GeosupportIntegrationTest> test = project.getTasks().register(integrationTestOptions.getTestName().get(), GeosupportIntegrationTest.class, new Action<GeosupportIntegrationTest>(){
                public void execute(GeosupportIntegrationTest test){
                    test.setDescription("Runs tests which call Geosupport native code using JNI.");
                    test.setGroup(GeosupportPlugin.DEFAULT_TASK_GROUP);
                    test.setTestClassesDirs(sourceSet.getOutput().getClassesDirs());
                    test.setClasspath(sourceSet.getRuntimeClasspath());
                    test.shouldRunAfter(project.getTasks().named("test"));
                    test.environment("GEOFILES", geosupportExtension.getGeofiles().get());
                }
            });
            TaskProvider<Task> checkTask = project.getTasks().named("check");
            checkTask.get().dependsOn(test);
        });
    }

    //
    // Based on https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/DockerRemoteApiPlugin.java
    //
    private void configureIntegrationTestOptionsAwareTasks(Project project, final IntegrationTestOptions integrationTestOptions, final Logger logger) {
        project.getTasks().withType(IntegrationTestOptionsAware.class).configureEach(new Action<IntegrationTestOptionsAware>() {
            @Override
            public void execute(IntegrationTestOptionsAware task) {
                logger.quiet("[ITEST] Configuring task {}'s integrationTestOptions conventions using {}.", task.getName(), integrationTestOptions);
                task.getIntegrationTestOptions().getTestName().convention(integrationTestOptions.getTestName());
                task.getIntegrationTestOptions().getSourceSetName().convention(integrationTestOptions.getSourceSetName());
                task.getIntegrationTestOptions().getJavaSourceDir().convention(integrationTestOptions.getJavaSourceDir());
                task.getIntegrationTestOptions().getResourcesSourceDir().convention(integrationTestOptions.getResourcesSourceDir());
                task.getIntegrationTestOptions().getValidate().convention(integrationTestOptions.getValidate());
                task.getIntegrationTestOptions().getUseJavaLibraryPath().convention(integrationTestOptions.getUseJavaLibraryPath());
                task.getIntegrationTestOptions().getExportLdLibraryPath().convention(integrationTestOptions.getExportLdLibraryPath());
            }
        });
    }
}
