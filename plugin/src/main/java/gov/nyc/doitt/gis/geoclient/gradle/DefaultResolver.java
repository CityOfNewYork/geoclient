package gov.nyc.doitt.gis.geoclient.gradle;

import java.util.Objects;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class DefaultResolver implements Resolver {

    private static final Logger logger = Logging.getLogger(DefaultResolver.class);

    private final Project project;

    public DefaultResolver(Project project) {
        super();
        this.project = project;
    }

    @Override
    public PropertySource resolve(PropertySource propertySource) {
        Objects.requireNonNull(propertySource, "PropertySource argument cannot be null");
        logger.info("Resolving {}", propertySource);
        Object newValue = null;
        SourceType type = propertySource.getType();
        switch (type) {
        case system:
            newValue = System.getProperty(propertySource.getName());
            if (newValue != null) {
                return new PropertySource(propertySource.getName(), newValue, SourceType.system, Resolution.computed);
            }
            break;
        case gradle:
            newValue = project.findProperty(propertySource.getName());
            if (newValue != null) {
                return new PropertySource(propertySource.getName(), newValue, SourceType.gradle, Resolution.computed);
            }
            break;
        case extension:
            // If value is already set, then it's from the explicit dsl configuration
            if (propertySource.getValue() != null) {
                return new PropertySource(propertySource.getName(), propertySource.getValue(), SourceType.extension, Resolution.direct);
            }
            break;
        case environment:
            // Argument propertySource.getValue() is null. Try the environment as a last
            // resource
            newValue = System.getenv(propertySource.getName());
            if (newValue != null) {
                return new PropertySource(propertySource.getName(), newValue, SourceType.environment, Resolution.computed);
            }
            break;
        default:
            break;
        }
        logger.error("Could not resolve {}", propertySource);
        // Fail
        return null;
    }

}
