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
    private final Resolver resolver;
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
    public AbstractRuntimePropertyExtension(String name, Resolver resolver, Project project) {
        super();
        Objects.requireNonNull(name, "String argument 'name' cannot be null");
        this.logger = project.getLogger();
        logger.debug("Constructing EXTENSION {} in class {}", name, getClass().getName());
        this.name = name;
        this.resolver = resolver;
        this.project = project;
        this.runtimeProperties = initRuntimeProperties();
        logger.debug("Container '{}' created for '{}' extension", this.runtimeProperties, name);
    }

    protected abstract List<DeferredContainerItemInfo> getContainerItems();

    protected NamedDomainObjectContainer<RuntimeProperty> initRuntimeProperties() {
        NamedDomainObjectContainer<RuntimeProperty> container = createContainer(project);
        createContainerItems(container);
        container.configureEach(getFinalizeRuntimePropertyAction());
        return container;
    }

    protected void createContainerItems(NamedDomainObjectContainer<RuntimeProperty> container) {
        List<DeferredContainerItemInfo> containerItems = getContainerItems();
        for (DeferredContainerItemInfo item : containerItems) {
            container.create(item.containerItemName, new Action<RuntimeProperty>() {
                @Override
                public void execute(RuntimeProperty runtimeProperty) {
                    logger.info("Setting conventions for RuntimeProperty '{}' using {}", item.containerItemName,
                            item.defaultPropertySource);
                    runtimeProperty.setConventions(item.defaultPropertySource);
                }
            });

        }
    }

    protected NamedDomainObjectContainer<RuntimeProperty> createContainer(Project project) {
        return project.container(RuntimeProperty.class, new NamedDomainObjectFactory<RuntimeProperty>() {
            public RuntimeProperty create(String name) {
                logger.info("Placeholder for RuntimeProperty '{}' created", name);
                return new RuntimeProperty(name, project.getObjects());
            }
        });
    }

    // TODO Need to listen to EXTENSION/container events to know how the Resolution
    // should be set
    protected PropertySource resolve(PropertySource propertySource) {
        return getResolver().resolve(propertySource);
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
                        // If user has not configured a particular ExportType, use defaults
                        if (!runtimeProperty.isExportTypeSpecified()) {
                            SourceType sourceType = finalPropertySource.getType();
                            runtimeProperty.setExportType(sourceType.defaultExportType());
                        }
                        logger.lifecycle("Resolved {} from sources", finalPropertySource);
                        break;
                    }
                }
                // Finalize RuntimeProperty's PropertySource
                // If it was found above then that will be used, otherwise the convention
                // (default) value is used
                finalPropertySource = runtimeProperty.finalizeWithCurrentValue();
                logger.info("Resolved {} using default value", runtimeProperty.currentValue());
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

    @Override
    public Resolver getResolver() {
        return this.resolver;
    }

    protected File getBuildDir() {
        return this.project.getBuildDir();
    }

    protected Project getProject() {
        return this.project;
    }
}