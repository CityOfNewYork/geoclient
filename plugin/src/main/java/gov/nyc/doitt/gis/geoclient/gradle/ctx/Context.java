package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import gov.nyc.doitt.gis.geoclient.gradle.property.Configuration;
import gov.nyc.doitt.gis.geoclient.gradle.property.PluginExtension;

public class Context {

    private static final Logger logger = Logging.getLogger(Context.class);

    private final List<Resolution> resolutions;

    public Context() {
        super();
        this.resolutions = new ArrayList<>();
    }

    public void resolve(Class<? extends PluginExtension> clazz) {
	
    }
    public boolean resolve(Configuration config) {
        logger.info("Resolving {}", config);
        Resolution resolution = new Resolution(config).resolve();
        logger.info("Result: {}", resolution);
        return this.resolutions.add(resolution);
    }

    public List<Resolution> getConfigurations() {
        return Collections.unmodifiableList(this.resolutions);
    }
}
