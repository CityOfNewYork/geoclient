package com.digitalclash.geoclient.gradle;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;

import com.digitalclash.geoclient.gradle.internal.ExtensionUtils;
import com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver;
import com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver;
import com.digitalclash.geoclient.gradle.tasks.GeosupportApplicationAware;

/**
 * Provides configurable Geosupport build and runtime configuration with
 * sensible defaults.
 */
public class GeosupportPlugin implements Plugin<Project> {

    static final String GEOSUPPORT_ENVIRONMENT_TASK_NAME = "geosupportEnvironment";
    static final String DEFAULT_INTEGRATION_TEST_NAME = "geosupportIntegrationTest";
    static final String DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME = "geosupportIntegrationTest";
    static final String DEFAULT_INTEGRATION_TEST_JAVA_SOURCE_DIR = String.format("src/%s/java", DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME);
    static final String DEFAULT_INTEGRATION_TEST_RESOURCES_SOURCE_DIR = String.format("src/%s/resources", DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME);

    @Override
    public void apply(final Project project) {
        GeosupportExtension geosupport = createGeosupportExtension(project);
        GeosupportIntegrationTestOptions geosupportIntegrationTestOptions = createGeosupportIntegrationTestOptions(project);
        GeosupportApplication geosupportApplication = createGeosupportApplication(project, geosupport, geosupportIntegrationTestOptions);
        GeoclientExtension geoclient = createGeoclientExtension(project);
    }

    private GeoclientExtension createGeoclientExtension(final Project project) {
        GeoclientExtension geoclient = project.getExtensions().create("geoclient", GeoclientExtension.class);
        geoclient.getJniVersion().convention(new GeoclientConfigResolver().getJniVersion());
        return geoclient;
    }

    private GeosupportApplication createGeosupportApplication(final Project project, final GeosupportExtension geosupportExtension, final GeosupportIntegrationTestOptions integrationTestOptions) {
        GeosupportApplication geosupportApplication = project.getExtensions().create("geosupportApplication", GeosupportApplication.class);
        geosupportApplication.geosupport(ExtensionUtils.conventionsFrom(geosupportExtension));
        geosupportApplication.integrationTestOptions(ExtensionUtils.conventionsFrom(integrationTestOptions));
        return geosupportApplication;
    }

    private GeosupportExtension createGeosupportExtension(final Project project) {
        GeosupportExtension geosupport = project.getExtensions().create("geosupport", GeosupportExtension.class);
        //rootProject.layout.getProjectDirectory
        GeosupportConfigResolver config = new GeosupportConfigResolver();
        geosupport.getHome().convention(config.getHome());
        File includePathFile = project.file(config.getIncludePath());
        if(includePathFile != null) {
            ObjectFactory objectFactory = project.getObjects();
            geosupport.getIncludePath().convention(objectFactory.directoryProperty().fileValue(includePathFile));
        }
        geosupport.getLibraryPath().convention(config.getLibraryPath());
        geosupport.getGeofiles().convention(config.getGeofiles());
        return geosupport;
    }

    private GeosupportIntegrationTestOptions createGeosupportIntegrationTestOptions(final Project project) {
        GeosupportIntegrationTestOptions integrationTestOptions = project.getExtensions().create("integrationTestOptions", GeosupportIntegrationTestOptions.class);
        integrationTestOptions.getTestName().convention(DEFAULT_INTEGRATION_TEST_NAME);
        integrationTestOptions.getSourceSetName().convention(DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME);
        ObjectFactory objectFactory = project.getObjects();
        File javaSourceDir = project.file(DEFAULT_INTEGRATION_TEST_JAVA_SOURCE_DIR);
        if(javaSourceDir != null) {
            integrationTestOptions.getJavaSourceDir().convention(objectFactory.directoryProperty().fileValue(javaSourceDir));
        }
        File resourcesSourceDir = project.file(DEFAULT_INTEGRATION_TEST_RESOURCES_SOURCE_DIR);
        if(resourcesSourceDir != null) {
            integrationTestOptions.getResourcesSourceDir().convention(objectFactory.directoryProperty().fileValue(resourcesSourceDir));
        }
        integrationTestOptions.getExportLdLibraryPath().convention(false);
        integrationTestOptions.getUseJavaLibraryPath().convention(false);
        integrationTestOptions.getValidate().convention(false);
        return integrationTestOptions;
    }

    //
    // Based on https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/DockerRemoteApiPlugin.java
    //
    //private void configureGeosupportExtensionAwareTasks(Project project, final GeosupportExtension geosupportExtension) {
    //    project.getTasks().withType(GeosupportExtensionAware.class).configureEach(new Action<GeosupportExtension>() {
    //        @Override
    //        public void execute(GeosupportExtensionAware task) {
    //            task.getGeosupportExtension().getGeofiles().convention(geosupportExtension.getGeofiles());
    //            task.getGeosupportExtension().getHome().convention(geosupportExtension.getHome());
    //            task.getGeosupportExtension().getIncludePath().convention(geosupportExtension.getIncludePath());
    //            task.getGeosupportExtension().getLibraryPath().convention(geosupportExtension.getLibraryPath());
    //        }
    //    });
    //}
}
