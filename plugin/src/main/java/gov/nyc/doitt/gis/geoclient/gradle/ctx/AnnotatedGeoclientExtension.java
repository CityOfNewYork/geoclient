package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.EXTENSION_PROPERTY;
import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.SYSTEM_PROPERTY;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.ValueSource;

public class AnnotatedGeoclientExtension {

    private final DirectoryProperty nativeTempDir;

    @javax.inject.Inject
    public AnnotatedGeoclientExtension(ObjectFactory objects) {
        super();
        this.nativeTempDir = objects.directoryProperty();
    }

    @ValueSource(key = "gc.jni.tmpdir", source = SYSTEM_PROPERTY)
    @ValueSource(source = EXTENSION_PROPERTY)
    public DirectoryProperty getNativeTempDir() {
        return this.nativeTempDir;
    }

}
