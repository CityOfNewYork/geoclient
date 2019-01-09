package gov.nyc.doitt.gis.geoclient.gradle;

import gov.nyc.doitt.gis.geoclient.gradle.ctx.SystemProperties;

public interface GeoclientSystemProperties extends SystemProperties {

    default String nativeTempDir() {
        return SystemProperties.getSystemProperty("gc.jni.version");
    }
}
