package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public abstract class AbstractRuntimePropertyExtension {

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
        this.logger = project.getLogger();
        logger.debug("Constructing extension {} in class {}", name, getClass().getName());
        this.name = name;
        this.project = project;
        this.runtimeProperties = initRuntimeProperties();
        logger.debug("NamedDomainObjectContainer<RuntimeProperty> '{}' created for '{}' extension",
                this.runtimeProperties, name);
        logger.debug("Calling abstract template method registerRuntimePropreties on '{}' extension", name);
        logger.debug("Registration complete");
    }

    protected NamedDomainObjectContainer<RuntimeProperty> initRuntimeProperties() {
        NamedDomainObjectContainer<RuntimeProperty> container = project.container(RuntimeProperty.class,
                new NamedDomainObjectFactory<RuntimeProperty>() {
                    public RuntimeProperty create(String name) {
                        logger.lifecycle(
                                "[CREATE_CONTAINER] NamedDomainObjectFactory<RuntimeProperty>#create('{}', '{}')", name,
                                project);
                        return new RuntimeProperty(name, project.getObjects());
                    }
                });
        List<DeferredContainerItemInfo> containerItems = getContainerItems();
        for (DeferredContainerItemInfo item : containerItems) {
            container.create(item.containerItemName, new Action<RuntimeProperty>() {
                @Override
                public void execute(RuntimeProperty rProp) {
                    logger.lifecycle("[SET_CONTAINER_ITEM_DEFAULT] Defaulting '{}' to '{}'", item.containerItemName,
                            item.defaultPropertySource);
                    rProp.setConventions(item.defaultPropertySource);
                }
            });

        }
        container.configureEach(getDefaultRuntimePropertyAction());
        return container;
    }

    protected abstract List<DeferredContainerItemInfo> getContainerItems();

    // TODO Need to listen to extension/container events to know how the Resolution
    // should be set
    protected PropertySource resolve(PropertySource ps) {
        Objects.requireNonNull(ps, "PropertySource argument cannot be null");
        logger.lifecycle("[RESOLVE_CONTAINER_ITEM] Resolving {}", ps);
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

    protected Action<RuntimeProperty> getDefaultRuntimePropertyAction() {
        return new Action<RuntimeProperty>() {
            @Override
            public void execute(RuntimeProperty runtimeProperty) {
                logger.lifecycle("[RESOLVING_FINAL_VALUE] {}", runtimeProperty);
                PropertySource finalPropertySource = null;
                List<PropertySource> propertySources = runtimeProperty.getSources().get();
                logger.lifecycle("[RESOLVING_FROM_SOURCE_LIST] {} item(s) to try", propertySources.size());
                for (PropertySource ps : propertySources) {
                    logger.lifecycle("[CHECKING_PROPERTY_SOURCE] {}", ps);
                    finalPropertySource = resolve(ps);
                    if (finalPropertySource != null) {
                        // Replace default PropertySource (RuntimeProperty.value) with successfully
                        // resolved one from
                        // user (i.e., in RuntimeProperty.sources)
                        runtimeProperty.setValue(finalPropertySource);
                        logger.lifecycle("[RESOLVED_FROM_SOURCE_LIST] {}", finalPropertySource);
                        break;
                    }
                }
                // Finalize RuntimeProperty's PropertySource
                // If it was found above then that will be used, otherwise the convention
                // (default) value is used
                finalPropertySource = runtimeProperty.finalizeWithCurrentValue();
                logger.lifecycle("[RESOLVED_FROM_DEFAULT] {}", runtimeProperty.getDefaultValue());
                // Add to sources list
                runtimeProperty.getSources();
            }
        };
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