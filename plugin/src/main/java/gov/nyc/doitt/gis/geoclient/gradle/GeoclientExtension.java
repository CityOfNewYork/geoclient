package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;

public class GeoclientExtension {

    private final DirectoryProperty nativeTempDir;

    @javax.inject.Inject
    public GeoclientExtension(ObjectFactory objects) {
        super();
        this.nativeTempDir = objects.directoryProperty();
    }

    public DirectoryProperty getNativeTempDir() {
        return this.nativeTempDir;
    }

}
