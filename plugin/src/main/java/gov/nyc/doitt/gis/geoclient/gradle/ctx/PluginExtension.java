package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.gradle.annotation.Property;

public interface PluginExtension {

    List<Property> getProperties();

    SystemProperties getSystemProperties();

    EnvironmentVariables getEnvironmentVariables();

    GradleProjectProperties getGradleProjecties();

    String getNamespace();
}