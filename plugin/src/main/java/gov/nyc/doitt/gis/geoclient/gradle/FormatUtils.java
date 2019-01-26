package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.File;
import java.util.Objects;

public class FormatUtils {

    public static String format(PropertySource s) {
        String type = s.getType() != null ? s.getType().toString() : "";
        String resolution = s.getResolution() != null ? s.getResolution().toString() : "";
        return format("%16s %-16s: %-20s %10s", "[" + type.toUpperCase() + "]", s.getName(), s.getValue(), "<" + resolution.toUpperCase() +">");
    }

    public static String format(String template, Object... args) {
        return String.format(template, args);
    }

    public static String format(RuntimeProperty p) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(p.getName()).append('\n');
        buffer.append("Default value: ").append(format(p.getCurrentDefault())).append('\n');
        buffer.append("  Exported to: ").append(format(p.getCurrentExport())).append('\n');
        p.getSources().get().forEach(e -> buffer.append(format(e)).append('\n'));
        return null;
    }


    public static String normalize(File parentDir, String subPath) {
        return normalize(String.format("%s/%s", parentDir, subPath));
    }

    public static String normalize(String path) {
        Objects.requireNonNull(path, "Path string cannot be null");
        return path.replaceAll("\\\\", "/");
    }

    private FormatUtils() {}
}
