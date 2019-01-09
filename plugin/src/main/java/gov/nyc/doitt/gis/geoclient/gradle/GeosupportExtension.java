package gov.nyc.doitt.gis.geoclient.gradle;

import java.util.List;

import org.gradle.api.Project;

import gov.nyc.doitt.gis.geoclient.gradle.annotation.Property;
import gov.nyc.doitt.gis.geoclient.gradle.annotation.PropertySource;
import gov.nyc.doitt.gis.geoclient.gradle.annotation.Source;
import gov.nyc.doitt.gis.geoclient.gradle.ctx.AbstractPluginExtension;

public class GeosupportExtension extends AbstractPluginExtension {

    private final org.gradle.api.provider.Property<String> geofiles;
    private final org.gradle.api.provider.Property<String> home;
    private final org.gradle.api.provider.Property<String> libraryPath;

    public GeosupportExtension(Project project) {
        super(project);
        this.geofiles = getExtensionProperty(String.class);
        this.home = getExtensionProperty(String.class);
        this.libraryPath = getExtensionProperty(String.class);
    }

    @PropertySource(Source.ENVIRONMENT_VARIABLE)
    public org.gradle.api.provider.Property<String> getGeofiles() {
        return this.geofiles;
    }

    @PropertySource(Source.ENVIRONMENT_VARIABLE)
    public org.gradle.api.provider.Property<String> getHome() {
        return this.home;
    }

    @PropertySource(Source.ENVIRONMENT_VARIABLE)
    public org.gradle.api.provider.Property<String> getLibraryPath() {
        return this.libraryPath;
    }

    @Override
    protected void initialize(List<Property> props) {
        props.add(withIdFromSource("Geofiles", Source.ENVIRONMENT_VARIABLE));
        props.add(withIdFromSource("Home", Source.ENVIRONMENT_VARIABLE));
        props.add(withIdFromSource("LibraryPath", Source.ENVIRONMENT_VARIABLE));
    }

    @Override
    public String getNamespace() {
        return "gs";
    }
}
