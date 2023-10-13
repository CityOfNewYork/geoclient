package com.digitalclash.geoclient.gradle;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public abstract class GeosupportExtension {

    abstract public Property<String> getGeofiles();
    abstract public Property<String> getHome();
    abstract public DirectoryProperty getIncludePath();
    abstract public Property<String> getLibraryPath();
}
