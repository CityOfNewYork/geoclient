package gov.nyc.doitt.gis.geoclient.gradle.property;

import java.util.Optional;

public class SystemProperty extends Configuration {

    public SystemProperty(String id, Source source, Object value) {
        super(id, source, value);
    }

    public SystemProperty(String id, Source source, Optional<?> value) {
        super(id, source, value);
    }

    public SystemProperty(String id, Source source, String value) {
        super(id, source, value);
    }

    public SystemProperty(String id, Source source) {
        super(id, source);
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(System.getProperty(getId()));
    }

}
