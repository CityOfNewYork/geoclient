package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import gov.nyc.doitt.gis.geoclient.gradle.annotation.Property;

public class ConfigurationContext {

    private static final Logger logger = Logging.getLogger(ConfigurationContext.class);

    private final List<Configuration> configurations;

    public ConfigurationContext() {
        super();
        this.configurations = new ArrayList<>();
    }

    public boolean resolve(Property property) {
        logger.info("Resolving {}", property);
        Configuration configuration = new Configuration(property).resolve();
        logger.info("Result: {}", configuration);
        return this.configurations.add(configuration);
    }

    public List<Configuration> getConfigurations() {
        return Collections.unmodifiableList(this.configurations);
    }
}
