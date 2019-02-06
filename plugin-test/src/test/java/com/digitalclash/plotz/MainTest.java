package com.digitalclash.plotz;

import org.junit.jupiter.api.Test;

public class MainTest {

    private Main main = new Main();

    @Test
    public void testGetSystemProperty() {
        System.out.println("System Properties");
        String nativeTempDir = main.getSystemProperty("gc.jni.version");
        System.out.println(nativeTempDir);
    }

    @Test
    public void testGetEnvironmentVariable() {
        System.out.println("Environment Variables");
        String geofiles = main.getEnvironmentVariable("GEOFILES");
        System.out.println(geofiles);
    }
}
