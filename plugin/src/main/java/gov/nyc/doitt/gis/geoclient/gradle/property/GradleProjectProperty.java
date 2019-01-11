package gov.nyc.doitt.gis.geoclient.gradle.property;

import java.util.Optional;

import org.gradle.api.Project;

public class GradleProjectProperty extends Configuration {

    public GradleProjectProperty(String id, Source source, Optional<?> value) {
        super(id, source, value);
    }

    public GradleProjectProperty(String id, Source source, Object value) {
        super(id, source, value);
    }

    public GradleProjectProperty(String id, Source source, String value) {
        super(id, source, value);
    }

    public GradleProjectProperty(String id, Source source) {
        super(id, source, Optional.empty());
    }

    public Optional<?> getValue(Project project) {
        if (project.hasProperty(getId())) {
            return Optional.of(project.property(getId()));
        }
        return Optional.empty();
    }

}
