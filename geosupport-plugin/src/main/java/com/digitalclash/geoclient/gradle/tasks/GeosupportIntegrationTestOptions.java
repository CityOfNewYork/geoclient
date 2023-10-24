package com.digitalclash.geoclient.gradle.tasks;

import org.gradle.api.provider.Property;

public abstract class GeosupportIntegrationTestOptions {
    abstract public Property<String> getSourceSetName();
    abstract public Property<Boolean> isValidate();
}
