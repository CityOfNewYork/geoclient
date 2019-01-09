package gov.nyc.doitt.gis.geoclient.gradle.ctx;

public class Utils {

    public static boolean hasValueAsString(Object obj) {
        if (obj == null) {
            return false;
        }
        return hasValue(obj.toString());
    }

    public static boolean hasValue(String string) {
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
