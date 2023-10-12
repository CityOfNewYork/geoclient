package com.digitalclash.geoclient.gradle;

import org.apache.tools.ant.taskdefs.condition.Os;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public abstract class GeosupportExtension {

    public static final String DEFAULT_HOME = Os.isFamily(Os.FAMILY_WINDOWS) ? "c:/opt/geosupport/current" : "/opt/geosupport/current";
    public static final String DEFAULT_GEOFILES = DEFAULT_HOME + "/fls/";
    public static final String DEFAULT_LIBRARY_PATH = DEFAULT_HOME + "/lib";
    public static final String DEFAULT_INCLUDE_PATH = "geoclient-jni/lib/geosupport/headers";

    abstract public Property<String> getGeofiles();
    abstract public Property<String> getHome();
    abstract public DirectoryProperty getIncludePath();
    abstract public Property<String> getLibraryPath();
}
