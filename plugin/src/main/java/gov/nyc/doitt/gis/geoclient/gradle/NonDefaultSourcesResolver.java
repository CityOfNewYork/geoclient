package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.BUILD_ENVIRONMENT;
import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.CONFIGURED;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.ENVIRONMENT;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.EXTENSION;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.GRADLE;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.SYSTEM;

import java.util.Objects;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class NonDefaultSourcesResolver implements Resolver {

    private static final Logger logger = Logging.getLogger(NonDefaultSourcesResolver.class);

    private final Project project;

    public NonDefaultSourcesResolver(Project project) {
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
        case SYSTEM:
            newValue = System.getProperty(propertySource.getName());
            if (newValue != null) {
                return new PropertySource(propertySource.getName(), newValue, SYSTEM, BUILD_ENVIRONMENT);
            }
            break;
        case GRADLE:
            newValue = project.findProperty(propertySource.getName());
            if (newValue != null) {
                return new PropertySource(propertySource.getName(), newValue, GRADLE, BUILD_ENVIRONMENT);
            }
            break;
        case EXTENSION:
            // If value is already set, then it's from the explicit dsl configuration
            if (propertySource.getValue() != null) {
                return new PropertySource(propertySource.getName(), propertySource.getValue(), EXTENSION, CONFIGURED);
            }
            break;
        case ENVIRONMENT:
            // Argument propertySource.getValue() is null. Try the ENVIRONMENT as a last
            // resource
            newValue = System.getenv(propertySource.getName());
            if (newValue != null) {
                return new PropertySource(propertySource.getName(), newValue, ENVIRONMENT, BUILD_ENVIRONMENT);
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
