package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.property.Source.ENVIRONMENT_VARIABLE;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

import gov.nyc.doitt.gis.geoclient.gradle.property.AnnotatedPluginExtension;
import gov.nyc.doitt.gis.geoclient.gradle.property.Source;
import gov.nyc.doitt.gis.geoclient.gradle.property.ValueSource;

public class GeosupportExtension extends AnnotatedPluginExtension {

    private final Property<String> geofiles;
    private final Property<String> home;
    private final Property<String> libraryPath;

    public GeosupportExtension(Project project) {
        super(project);
        this.geofiles = providerProperty(String.class);
        this.home = providerProperty(String.class);
        this.libraryPath = providerProperty(String.class);
    }

    @ValueSource(ENVIRONMENT_VARIABLE)
    public Property<String> getGeofiles() {
        return this.geofiles;
    }

    @ValueSource(ENVIRONMENT_VARIABLE)
    public Property<String> getHome() {
        return this.home;
    }

    @ValueSource(Source.ENVIRONMENT_VARIABLE)
    public org.gradle.api.provider.Property<String> getLibraryPath() {
        return this.libraryPath;
    }
}
