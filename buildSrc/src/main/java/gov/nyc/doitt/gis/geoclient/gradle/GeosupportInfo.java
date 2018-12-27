package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class GeosupportConfigurationTask extends DefaultTask {

    private String version;
    private String release;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRelease() {
        return this.release;
    }

    @TaskAction
    void showVersion() {
        System.out.println(String.format("%s_%s%n", getRelease(), getVersion()));
    }

}