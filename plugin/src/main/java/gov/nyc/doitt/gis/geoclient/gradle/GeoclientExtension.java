package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;

public class GeoclientExtension {

    private final DirectoryProperty nativeTempDir;

    public GeoclientExtension(Project project) {
        this.nativeTempDir = project.getObjects().directoryProperty();
    }

    public DirectoryProperty getNativeTempDir() {
        return this.nativeTempDir;
    }
}
