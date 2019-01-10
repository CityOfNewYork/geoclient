package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;

import gov.nyc.doitt.gis.geoclient.gradle.annotation.Property;
import gov.nyc.doitt.gis.geoclient.gradle.annotation.Source;

public abstract class AbstractPluginExtension implements PluginExtension {

    private final Project project;
    private final List<Property> properties;

    public AbstractPluginExtension(Project project) {
        super();
        this.project = project;
        this.properties = new ArrayList<>();
        initialize(properties);
    }

    abstract protected void initialize(List<Property> properties);

    protected <T> org.gradle.api.provider.Property<T> getExtensionProperty(Class<T> clazz) {
        return project.getObjects().property(clazz);
    }

    protected DirectoryProperty directoryProperty() {
        return project.getObjects().directoryProperty();
    }

    protected Property withIdFromSource(String id, Source source) {
        return new Property(String.format("%s%s", getNamespace(), id), source);
    }

    @Override
    public List<Property> getProperties() {
        return Collections.unmodifiableList(this.properties);
    }

    @Override
    public SystemProperties getSystemProperties() {
        getProperties().stream().filter(new Predicate<Property>() {
            @Override
            public boolean test(Property t) {
                return t.getSource().equals(Source.SYSTEM_PROPERTY);
            }
        });
        return null;
    }

    @Override
    public EnvironmentVariables getEnvironmentVariables() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GradleProjectProperties getGradleProjecties() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

}
