package gov.nyc.doitt.gis.geoclient.gradle;

public class Utils {

    public static boolean hasValue(String string) {
        if (string == null) {
            return false;
        }
        if(string.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private Utils() { super(); }
}
