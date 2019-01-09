package gov.nyc.doitt.gis.geoclient.gradle.ctx;

public interface SystemProperties {

    static public String getSystemProperty(String name) {
        String result = System.getProperty(name);
        if (result != null) {
            return result;
        }
        return null;
    }

    default String getJavaIoTempDir() {
        return getSystemProperty("java.io.tmpdir");
    }

    default String getJavaLibraryPath() {
        return getSystemProperty("java.library.path");
    }
}
