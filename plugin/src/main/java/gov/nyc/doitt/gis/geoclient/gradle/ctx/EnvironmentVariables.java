package gov.nyc.doitt.gis.geoclient.gradle.ctx;

public interface EnvironmentVariables {

    static public String getEnvironmentVariable(String name) {
        String result = System.getenv(name);
        if (result != null) {
            return result;
        }
        return null;
    }
}
