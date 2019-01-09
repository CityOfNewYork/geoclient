package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.time.LocalDateTime;

import gov.nyc.doitt.gis.geoclient.gradle.annotation.Property;

public class Configuration {

    public enum Resolution {
        RESOLVED, UNRESOLVED
    }

    private final Property property;
    private Resolution resolution;
    private LocalDateTime timestamp;

    public Configuration(Property property) {
        super();
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public synchronized Configuration resolve() {
        if (Utils.hasValueAsString(this.property.getValue())) {
            markResolved();
        } else {
            markUnresolved();
        }
        timestamp();
        return this;
    }

    private void markResolved() {
        this.resolution = Resolution.RESOLVED;
    }

    private void markUnresolved() {
        this.resolution = Resolution.UNRESOLVED;
    }

    private void timestamp() {
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Configuration [property=" + property + ", resolution=" + resolution + ", timestamp=" + timestamp + "]";
    }
}
