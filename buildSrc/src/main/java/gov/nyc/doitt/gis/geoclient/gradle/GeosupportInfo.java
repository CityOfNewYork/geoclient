package gov.nyc.doitt.gis.geoclient.gradle;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
public class GeosupportInfo extends DefaultTask {
    private String libraryPath;
    private String geofiles;
    @Input
    public String getLibraryPath() {
        return this.libraryPath;
    }
    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }
    @Input
    public String getGeofiles() {
        return this.geofiles;
    }
    public void setGeofiles(String geofiles) {
        this.geofiles = geofiles;
    }
    @TaskAction
    void geosupportInfo() {
        System.out.println(String.format("GeosupportInfo { geofiles: '%s', libraryPath: '%s'}", getGeofiles(), getLibraryPath()));
    }
}