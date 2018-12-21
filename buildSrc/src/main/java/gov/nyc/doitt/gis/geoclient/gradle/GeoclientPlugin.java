package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GeoclientPlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks().create("geosupport", Geosupport.class, (task) -> { 
            task.setVersion("18.4");
            task.setRelease("18d");                                
        });
    }
}