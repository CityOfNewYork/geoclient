package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.File;
import java.util.Objects;

public class FormatUtils {

    public static String normalize(File parentDir, String subPath) {
        return normalize(String.format("%s/%s", parentDir, subPath));
    }

    public static String normalize(String path) {
        Objects.requireNonNull(path, "Path string cannot be null");
        return path.replaceAll("\\\\", "/");
    }

    private FormatUtils() {
    }
}
