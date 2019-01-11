package gov.nyc.doitt.gis.geoclient.gradle.common;

public class Utils {

    public static boolean hasValue(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return hasStringValue(obj.toString());
        }
        return true;
    }

    public static boolean hasStringValue(String string) {
        if (string == null) {
            return false;
        }
        if (string.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private Utils() {
        super();
    }
}
