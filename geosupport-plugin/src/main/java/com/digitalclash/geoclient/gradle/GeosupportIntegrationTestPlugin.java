package com.digitalclash.geoclient.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver;

public class GeosupportIntegrationTestPlugin implements Plugin<Project> {


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
        return geosupport;
    }
}
