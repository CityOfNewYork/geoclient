package com.digitalclash.geoclient.gradle;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;

import com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver;
import com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver;

/**
 * Provides configurable Geosupport build and runtime configuration with
 * sensible defaults.
 */
public class GeosupportPlugin implements Plugin<Project> {


    @Override
    public void apply(final Project project) {
        GeoclientExtension geoclient = createGeoclientExtension(project);
        GeosupportExtension geosupport = createGeosupportExtension(project);
    }

    private GeoclientExtension createGeoclientExtension(final Project project) {
        GeoclientExtension geoclient = project.getExtensions().create("geoclient", GeoclientExtension.class);
        geoclient.getJniVersion().convention(new GeoclientConfigResolver().getJniVersion());
        return geoclient;
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
}
