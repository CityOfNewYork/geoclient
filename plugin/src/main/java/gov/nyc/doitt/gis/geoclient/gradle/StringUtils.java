package gov.nyc.doitt.gis.geoclient.gradle;

public class StringUtils {

    public static final String NULL_ARG_RETURN_STRING = "";

    public static String nullSafeString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return NULL_ARG_RETURN_STRING;
    }

    public static String fill(int length, char c) {
        if (length <= 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer(c); // length == 1
        int i = 0;
        while (i < length) {
            buffer.append(c);
            i++;
        }
        return buffer.toString();
    }

    private StringUtils() {
    }
}
