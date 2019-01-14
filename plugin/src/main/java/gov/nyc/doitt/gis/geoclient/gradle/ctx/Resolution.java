package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.time.LocalDateTime;
import java.util.Optional;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.Configuration;

public class Resolution {

    public enum Outcome {
        RESOLVED, UNRESOLVED
    }

    private final Configuration configuration;
    private Outcome resolution;
    private LocalDateTime timestamp;

    public Resolution(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Outcome getResolution() {
        return resolution;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public Optional<? extends Object> getValue() {
        return this.configuration.getValue();
    }

    public synchronized Resolution resolve() {
        if (this.configuration.getValue().isPresent()) {
            markResolved();
        } else {
            markUnresolved();
        }
        timestamp();
        return this;
    }

    private void markResolved() {
        this.resolution = Outcome.RESOLVED;
    }

    private void markUnresolved() {
        this.resolution = Outcome.UNRESOLVED;
    }

    private void timestamp() {
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Resolution [configuration=" + configuration + ", resolution=" + resolution + ", timestamp=" + timestamp
                + "]";
    }
}
