package gov.nyc.doitt.gis.geoclient.gradle.property;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public abstract class AnnotatedPluginExtension implements PluginExtension {

    private final Project project;

    public AnnotatedPluginExtension(Project project) {
        super();
        this.project = project;
    }

    protected <T> Property<T> providerProperty(Class<T> clazz) {
        return project.getObjects().property(clazz);
    }

    protected DirectoryProperty directoryProperty() {
        return project.getObjects().directoryProperty();
    }

}
