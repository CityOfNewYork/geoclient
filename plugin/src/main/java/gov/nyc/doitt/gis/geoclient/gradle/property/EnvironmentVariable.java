package gov.nyc.doitt.gis.geoclient.gradle.property;

import java.util.Optional;

public class EnvironmentVariable extends Configuration {

    public EnvironmentVariable(String id, Source source, Object value) {
        super(id, source, value);
    }

    public EnvironmentVariable(String id, Source source, Optional<?> value) {
        super(id, source, value);
    }

    public EnvironmentVariable(String id, Source source, String value) {
        super(id, source, value);
    }

    public EnvironmentVariable(String id, Source source) {
        super(id, source);
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(System.getenv(getId()));
    }

}
