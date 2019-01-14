package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.PROJECT_PROPERTY;

import java.util.Optional;

import org.gradle.api.Project;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.Configuration;

public class ProjectProperty extends Configuration {

    private final Project project;

    public ProjectProperty(String id, Project project) {
        super(id, PROJECT_PROPERTY);
        this.project = project;
    }

    public Optional<?> getValue() {
        if (project.hasProperty(getKey())) {
            return Optional.of(project.property(getKey()));
        }
        return Optional.empty();
    }

}
