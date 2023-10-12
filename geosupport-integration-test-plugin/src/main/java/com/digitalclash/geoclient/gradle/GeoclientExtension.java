package com.digitalclash.geoclient.gradle;

import org.gradle.api.provider.Property;

public abstract class GeoclientExtension {
    public static final String DEFAULT_JNI_VERSION = "geoclient-jni-2";
    abstract public Property<String> getJniVersion();
}
