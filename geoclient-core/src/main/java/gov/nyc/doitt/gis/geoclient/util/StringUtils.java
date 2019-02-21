package gov.nyc.doitt.gis.geoclient.util;

import java.util.Optional;

public class StringUtils {

    private StringUtils() {
    }

    public static Optional<String> optionalStringValue(Object obj) {
        if (obj != null) {
            return Optional.of(obj.toString());
        }
        return Optional.empty();
    }

    public static String stringOrNullValueOf(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

}
