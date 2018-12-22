package gov.nyc.doitt.gis.geoclient.gradle;

public class GeosupportExtension {

    private String libraryPath;
    private String geofiles;

    public String getLibraryPath() {
        return this.libraryPath;
    }

    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    public void setGeofiles(String geofiles) {
        this.geofiles = geofiles;
    }

    public String getGeofiles() {
        return this.geofiles;
    }

}