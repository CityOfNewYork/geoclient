package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.File;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

public class BaseRuntimePropertyExtension {

    private final String name;
    private final Project project;
    private final NamedDomainObjectContainer<RuntimeProperty> runtimeProperties;

    /**
     * Creates a convenient named base class which holds a reference to the
     * {@linkplain Project} and contains a {@linkplain NamedDomainObjectContainer}
     * for ${@link RuntimeProperty} instances.
     * 
     * @param name    unique name for an instance
     * @param project Gradle Project reference
     */
    public BaseRuntimePropertyExtension(String name, Project project) {
        super();
        this.name = name;
        this.project = project;
        this.runtimeProperties = project.container(RuntimeProperty.class);
    }

    protected void add(RuntimeProperty runtimeProperty) {
        this.runtimeProperties.add(runtimeProperty);
    }

    protected RuntimeProperty create(String name) {
        return new RuntimeProperty(name, project.getObjects());
    }

    public String getName() {
        return name;
    }

    public NamedDomainObjectContainer<RuntimeProperty> getRuntimeProperties() {
        return runtimeProperties;
    }

    protected File getBuildDir() {
        return this.project.getBuildDir();
    }

    protected Project getProject() {
        return this.project;
    }
}