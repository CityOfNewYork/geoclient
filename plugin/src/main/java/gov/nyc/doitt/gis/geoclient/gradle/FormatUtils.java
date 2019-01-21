package gov.nyc.doitt.gis.geoclient.gradle;

public class FormatUtils {

    public static String format(PropertySource s) {
        return format("%16s %-16s: %-32s", "[" + s.nullSafeType().toUpperCase() + "]", s.nullSafeName(), s.nullSafeValue());	
    }

    public static String format(String template, Object... args) {
        return String.format(template, args);
    }
    
    public static String format(RuntimeProperty p) {
        String exportedGradleProperty = p.getGradleProperty().getOrElse("");
        String source = p.getSource().get().format();
        return String.format("%s -> %s", source, exportedGradleProperty);
    }

    private FormatUtils() {}
}
