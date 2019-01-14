package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.ENVIRONMENT_VARIABLE;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.ValueSource;

public class AnnotatedGeosupportExtension {

    private final Property<String> geofiles;
    private final Property<String> home;
    private final Property<String> libraryPath;

    @javax.inject.Inject
    public AnnotatedGeosupportExtension(ObjectFactory objects) {
        super();
        this.geofiles = objects.property(String.class);
        this.home = objects.property(String.class);
        this.libraryPath = objects.property(String.class);
    }

    @ValueSource(key = "GEOFILES", source = ENVIRONMENT_VARIABLE)
    public Property<String> getGeofiles() {
        return this.geofiles;
    }

    @ValueSource(key = "GEOSUPPORT_HOME", source = ENVIRONMENT_VARIABLE)
    public Property<String> getHome() {
        return this.home;
    }

    @ValueSource(key = "GS_LIBRARY_PATH", source = ENVIRONMENT_VARIABLE)
    public Property<String> getLibraryPath() {
        return this.libraryPath;
    }
}
