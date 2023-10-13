package com.digitalclash.geoclient.gradle;

import org.gradle.api.provider.Property;

public abstract class GeoclientExtension {
    abstract public Property<String> getJniVersion();
}
