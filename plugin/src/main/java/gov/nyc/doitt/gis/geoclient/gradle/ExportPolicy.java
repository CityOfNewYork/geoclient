package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.tasks.util.PatternFilterable;

public interface ExportPolicy {

    boolean exportToTests(PatternFilterable patternFilterable);
    void setExportToTests(boolean exportToTests);
}
