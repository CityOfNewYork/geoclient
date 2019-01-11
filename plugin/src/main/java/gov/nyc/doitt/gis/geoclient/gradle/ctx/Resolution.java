package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.time.LocalDateTime;

import gov.nyc.doitt.gis.geoclient.gradle.property.Configuration;

public class Resolution {

    public enum Outcome {
        RESOLVED, UNRESOLVED
    }

    private final Configuration property;
    private Outcome resolution;
    private LocalDateTime timestamp;

    public Resolution(Configuration property) {
        super();
        this.property = property;
    }

    public Configuration getProperty() {
        return property;
    }

    public Outcome getResolution() {
        return resolution;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public synchronized Resolution resolve() {
        if (this.property.getValue().isPresent()) {
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
        return "Configuration [property=" + property + ", resolution=" + resolution + ", timestamp=" + timestamp + "]";
    }
}
