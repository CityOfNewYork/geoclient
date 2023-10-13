package com.digitalclash.geoclient.gradle.internal;

public class GeoclientConfigResolver extends AbstractConfigResolver {

    public static final String DEFAULT_JNI_VERSION = "geoclient-jni-2";
    public static final String GC_JNI_VERSION_ENVVAR = "GC_JNI_VERSION";
    public static final String GC_JNI_VERSION_SYSTEM = "gc.jni.version";

    public String getJniVersion() {
        String jniVersion = getSystemProperty(GeoclientConfigResolver.GC_JNI_VERSION_SYSTEM);
        if (jniVersion != null) {
            return jniVersion;
        }
        jniVersion = getEnv(GeoclientConfigResolver.GC_JNI_VERSION_SYSTEM);
        if (jniVersion != null) {
            return jniVersion;
        }
        return GeoclientConfigResolver.DEFAULT_JNI_VERSION;
    }
}
