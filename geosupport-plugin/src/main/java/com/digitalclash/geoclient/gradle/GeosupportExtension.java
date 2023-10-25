package com.digitalclash.geoclient.gradle;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public abstract class GeosupportExtension {

    @Input
    @Optional
    abstract public Property<String> getGeofiles();
    @Input
    @Optional
    abstract public Property<String> getHome();
    @InputDirectory
    @Optional
    abstract public DirectoryProperty getIncludePath();
    @Input
    @Optional
    abstract public Property<String> getLibraryPath();
}
