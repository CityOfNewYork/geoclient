package gov.nyc.doitt.gis.geoclient.gradle;

public enum SourceType {
    SYSTEM, GRADLE, EXTENSION, ENVIRONMENT;

    public ExportType defaultExportType() {
        ExportType result = null;
        switch (this.ordinal()) {
        case 0:
            result = ExportType.SYSTEM;
            break;
        case 1:
            result = ExportType.GRADLE;
            break;
        case 2:
            // SourceType.EXTENSION is not an "export-able" type so it defaults to
            // ExportType.SYSTEM
            result = ExportType.SYSTEM;
            break;
        case 3:
            result = ExportType.ENVIRONMENT;
            break;
        default:
            // Returns null
            break;
        }
        return result;
    }
}