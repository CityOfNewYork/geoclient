package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.property.Source.EXTENSION_PROPERTY;
import static gov.nyc.doitt.gis.geoclient.gradle.property.Source.SYSTEM_PROPERTY;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;

import gov.nyc.doitt.gis.geoclient.gradle.property.AnnotatedPluginExtension;
import gov.nyc.doitt.gis.geoclient.gradle.property.ValueSource;

public class GeoclientExtension extends AnnotatedPluginExtension {

    private final DirectoryProperty nativeTempDir;

    public GeoclientExtension(Project project) {
        super(project);
        this.nativeTempDir = directoryProperty();
    }

    @ValueSource(SYSTEM_PROPERTY)
    @ValueSource(EXTENSION_PROPERTY)
    public DirectoryProperty getNativeTempDir() {
        return this.nativeTempDir;
    }

}
