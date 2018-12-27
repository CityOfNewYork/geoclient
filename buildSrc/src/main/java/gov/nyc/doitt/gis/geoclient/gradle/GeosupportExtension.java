package gov.nyc.doitt.gis.geoclient.gradle;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
public class GeosupportExtension {
    private final Property<String> libraryPath;
    private final Property<String> geofiles;
    public GeosupportExtension(Project project) {
        this.geofiles = project.getObjects().property(String.class);
        this.libraryPath = project.getObjects().property(String.class);
    }
    public Property<String> getLibraryPath() {
        return this.libraryPath;
    }
    public Property<String> getGeofiles() {
        return this.geofiles;
    }
}