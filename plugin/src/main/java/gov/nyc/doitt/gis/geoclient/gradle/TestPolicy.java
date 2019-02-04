package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.tasks.util.PatternSet;

public class TestPolicy extends PatternSet {

    public static final String[] DEFAULT_INCLUDE_PATTERNS = { "**/*FunctionalTest*/**", "**/*IntegrationTest*/**" };

    private boolean export;

    public TestPolicy() {
        super();
        this.export = true;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public void useDefaultIncludePatterns() {
        this.include(DEFAULT_INCLUDE_PATTERNS);
    }

    @Override
    public String toString() {
        return "TestPolicy [export=" + export + ", " + super.toString() + "]";
    }
}
