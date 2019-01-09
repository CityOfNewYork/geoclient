package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import org.gradle.api.Project;

public interface GradleProjectProperties {

    static public Object getProjectProperty(Project project, String propName) {
        if (project.hasProperty(propName)) {
            Object result = project.property(propName);
            return result;
        }
        return null;
    }
}
