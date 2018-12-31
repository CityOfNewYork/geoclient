package gov.nyc.doitt.gis.geoclient.gradle;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class GeosupportExtension {

    private final Property<String> geofiles;
    private final Property<String> home;
    private final Property<String> libraryPath;

    public GeosupportExtension(Project project) {
        this.geofiles = project.getObjects().property(String.class);
        this.home = project.getObjects().property(String.class);
        this.libraryPath = project.getObjects().property(String.class);
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
