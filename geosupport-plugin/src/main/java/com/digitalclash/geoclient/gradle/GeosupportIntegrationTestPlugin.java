package com.digitalclash.geoclient.gradle;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.testing.Test;

import com.digitalclash.geoclient.gradle.tasks.GeosupportIntegrationTestOptions;

public class GeosupportIntegrationTestPlugin implements Plugin<Project> {
    static final String TEST_NAME = "geosupportIntegrationTest";
    static final String SOURCE_SET_NAME = "geosupportIntegrationTest";
    static final String JAVA_SOURCE_SET_DIR = String.format("src/%s/java", SOURCE_SET_NAME);
    static final String JAVA_RESOURCES_SOURCE_SET_DIR = String.format("src/%s/resources", SOURCE_SET_NAME);

    @Override
    public void apply(final Project project) {
        project.getPlugins().apply(GeosupportPlugin.class);
        GeosupportExtension geosupportExtension = project.getExtensions().getByType(GeosupportExtension.class);
        GeoclientExtension geoclientExtension = project.getExtensions().getByType(GeoclientExtension.class);
        project.getPlugins().withType(JavaPlugin.class).configureEach(javaPlugin -> {
            SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            SourceSet sourceSet = sourceSets.create(SOURCE_SET_NAME);
            sourceSet.getJava().srcDir(JAVA_SOURCE_SET_DIR);
            sourceSet.getResources().srcDir(JAVA_RESOURCES_SOURCE_SET_DIR);
            sourceSet.getCompileClasspath().plus(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getOutput());
            sourceSet.getRuntimeClasspath().plus(sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getOutput());
            Configuration implementation = project.getConfigurations().getByName(sourceSet.getImplementationConfigurationName());
            implementation.extendsFrom(project.getConfigurations().getByName(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME));
            Configuration runtimeOnly = project.getConfigurations().getByName(sourceSet.getRuntimeOnlyConfigurationName());
            TaskProvider<Test> test = project.getTasks().register(TEST_NAME, Test.class, new Action<Test>(){
                public void execute(Test test){
                    test.setDescription("Runs tests which call Geosupport native code using JNI.");
                    test.setGroup("verification");
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
}
