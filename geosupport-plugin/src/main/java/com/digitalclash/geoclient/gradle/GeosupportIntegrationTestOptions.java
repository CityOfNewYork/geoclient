package com.digitalclash.geoclient.gradle;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;

public abstract class GeosupportIntegrationTestOptions {
    @Input
    @Optional
    abstract public Property<String> getTestName();
    @Input
    @Optional
    abstract public Property<String> getSourceSetName();
    @InputDirectory
    @Optional
    abstract public DirectoryProperty getJavaSourceDir();
    @InputDirectory
    @Optional
    abstract public DirectoryProperty getResourcesSourceDir();
    @Input
    @Optional
    abstract public Property<Boolean> getValidate();
    @Input
    @Optional
    abstract public Property<Boolean> getUseJavaLibraryPath();
    @Input
    @Optional
    abstract public Property<Boolean> getExportLdLibraryPath();
}
