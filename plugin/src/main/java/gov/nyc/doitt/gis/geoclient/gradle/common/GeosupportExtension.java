package gov.nyc.doitt.gis.geoclient.gradle.common;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class GeosupportExtension {

    private final Property<String> geofiles;
    private final Property<String> home;
    private final Property<String> libraryPath;

    @javax.inject.Inject
    public GeosupportExtension(ObjectFactory objects) {
        super();
        this.geofiles = objects.property(String.class);
        this.home = objects.property(String.class);
        this.libraryPath = objects.property(String.class);
    }

    public Property<String> getGeofiles() {
        return this.geofiles;
    }

    public Property<String> getHome() {
        return this.home;
    }

    public Property<String> getLibraryPath() {
        return this.libraryPath;
    }
}
