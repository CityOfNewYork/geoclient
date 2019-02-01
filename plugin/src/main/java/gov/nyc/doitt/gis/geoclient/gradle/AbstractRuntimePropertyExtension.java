package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public abstract class AbstractRuntimePropertyExtension implements RuntimePropertyExtension {

    private final Logger logger;
    private final String name;
    private final Project project;
    private final NamedDomainObjectContainer<RuntimeProperty> runtimeProperties;

    static class DeferredContainerItemInfo {
        private final String containerItemName;
        private final PropertySource defaultPropertySource;

        /**
         * @param containerItemName
         * @param defaultPropertySource
         */
        public DeferredContainerItemInfo(String containerItemName, PropertySource defaultPropertySource) {
            super();
            this.containerItemName = containerItemName;
            this.defaultPropertySource = defaultPropertySource;
        }

        public String getContainerItemName() {
            return containerItemName;
        }

        public PropertySource getDefaultPropertySource() {
            return defaultPropertySource;
        }

        @Override
        public String toString() {
            return "DeferredContainerItemInfo [containerItemName=" + containerItemName + ", defaultPropertySource="
                    + defaultPropertySource + "]";
        }

    }

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
        Objects.requireNonNull(name, "String argument 'name' cannot be null");
        this.logger = project.getLogger();
        logger.debug("Constructing extension {} in class {}", name, getClass().getName());
        this.name = name;
        this.project = project;
        this.runtimeProperties = initRuntimeProperties();
        logger.debug("Container '{}' created for '{}' extension", this.runtimeProperties, name);
    }

    protected NamedDomainObjectContainer<RuntimeProperty> initRuntimeProperties() {
        NamedDomainObjectContainer<RuntimeProperty> container = project.container(RuntimeProperty.class,
                new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        logger.info("Placeholder for RuntimeProperty '{}' created", name);
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        List<DeferredContainerItemInfo> containerItems = getContainerItems();
        for (DeferredContainerItemInfo item : containerItems) {
            container.create(item.containerItemName, new Action<RuntimeProperty>() {
                @Override
                public void execute(RuntimeProperty rProp) {
                    logger.info("Setting conventions for RuntimeProperty '{}' using {}", item.containerItemName,
                            item.defaultPropertySource);
                    rProp.setConventions(item.defaultPropertySource);
                }
            });

        }
        container.configureEach(getFinalizeRuntimePropertyAction());
        return container;
    }

    protected abstract List<DeferredContainerItemInfo> getContainerItems();

    // TODO Need to listen to extension/container events to know how the Resolution
    // should be set
    protected PropertySource resolve(PropertySource ps) {
        Objects.requireNonNull(ps, "PropertySource argument cannot be null");
        logger.info("Resolving {}", ps);
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

    protected Action<RuntimeProperty> getFinalizeRuntimePropertyAction() {
        return new Action<RuntimeProperty>() {
            @Override
            public void execute(RuntimeProperty runtimeProperty) {
                logger.info("Finalizing {}", runtimeProperty);
                PropertySource finalPropertySource = null;
                List<PropertySource> propertySources = runtimeProperty.getSources().get();
                logger.debug("RuntimeProperty instance has {} configured source(s)", propertySources.size());
                for (PropertySource ps : propertySources) {
                    logger.debug("Checking {}", ps);
                    finalPropertySource = resolve(ps);
                    if (finalPropertySource != null) {
                        // Replace default PropertySource (RuntimeProperty.value) with successfully
                        // resolved one from
                        // user (i.e., in RuntimeProperty.sources)
                        runtimeProperty.setValue(finalPropertySource);
                        logger.lifecycle("Resolved {} from sources", finalPropertySource);
                        break;
                    }
                }
                // Finalize RuntimeProperty's PropertySource
                // If it was found above then that will be used, otherwise the convention
                // (default) value is used
                finalPropertySource = runtimeProperty.finalizeWithCurrentValue();
                logger.info("Resolved {} using default value", runtimeProperty.getDefaultValue());
                // Add to sources list
                runtimeProperty.getSources();
            }
        };
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
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