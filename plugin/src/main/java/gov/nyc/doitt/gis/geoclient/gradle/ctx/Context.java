package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import gov.nyc.doitt.gis.geoclient.gradle.property.Configuration;

public class Context {

    private static final Logger logger = Logging.getLogger(Context.class);

    private final List<Resolution> configurations;

    public Context() {
        super();
        this.configurations = new ArrayList<>();
    }

    public boolean resolve(Configuration property) {
        logger.info("Resolving {}", property);
        Resolution configuration = new Resolution(property).resolve();
        logger.info("Result: {}", configuration);
        return this.configurations.add(configuration);
    }

    public List<Resolution> getConfigurations() {
        return Collections.unmodifiableList(this.configurations);
    }
}
