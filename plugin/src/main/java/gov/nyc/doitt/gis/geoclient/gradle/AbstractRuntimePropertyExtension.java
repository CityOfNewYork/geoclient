package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public abstract class AbstractRuntimePropertyExtension {

    private final Logger logger;
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
    public AbstractRuntimePropertyExtension(String name, Project project) {
        super();
        this.logger = project.getLogger();
        this.name = name;
        this.project = project;
        this.runtimeProperties = project.container(RuntimeProperty.class);
        configure();
    }

    protected abstract void configure();

    protected PropertySource resolve(PropertySource ps) {
        Objects.requireNonNull(ps, "PropertySource argument cannot be null");
        logger.lifecycle("Resolving {}", ps);
        Object newValue = null;
        SourceType type = ps.getType();
        switch (type) {
        case system:
            newValue = System.getProperty(ps.getName());
            if (newValue != null) {
                return new PropertySource(ps.getName(), newValue, SourceType.system, Resolution.computed);
            }
            break;
        case gradle:
            newValue = project.findProperty(ps.getName());
            if (newValue != null) {
                return new PropertySource(ps.getName(), newValue, SourceType.gradle, Resolution.computed);
            }
            break;
        case extension:
            // If value is already set, then it's from the explicit dsl configuration
            if (ps.getValue() != null) {
                return new PropertySource(ps.getName(), ps.getValue(), SourceType.extension, Resolution.direct);
            }
            break;
        case environment:
            // Argument propertySource.getValue() is null. Try the environment as a last
            // resource
            newValue = System.getenv(ps.getName());
            if (newValue != null) {
                return new PropertySource(ps.getName(), newValue, SourceType.environment, Resolution.computed);
            }
            break;
        default:
            break;
        }
        logger.error("Could not resolve {}", ps);
        // Fail
        return null;
    }

    protected void configureContainerItem(String name) {
        logger.lifecycle("Configuring container item {}", name);
        this.getRuntimeProperties().getByName(name, new Action<RuntimeProperty>() {
            @Override
            public void execute(RuntimeProperty rp) {
                logger.lifecycle("Executing Action<RuntimeProperty> with {}", rp);
                List<PropertySource> propertySources = rp.getSources().get();
                for (PropertySource ps : propertySources) {
                    PropertySource resolvedPropertySource = resolve(ps);
                    if (resolvedPropertySource != null) {
                        rp.setValue(resolvedPropertySource);
                        logger.lifecycle("Resolved {}", resolvedPropertySource);
                    }
                }
            }
        });
    }

    protected void export(PropertySource src, PropertySource target) {

    }

    protected void add(RuntimeProperty runtimeProperty) {
        this.runtimeProperties.add(runtimeProperty);
    }

    protected RuntimeProperty create(String name, PropertySource defaultValue) {
        return new RuntimeProperty(name, defaultValue, project.getObjects());
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