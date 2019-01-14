package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.ENVIRONMENT_VARIABLE;

import java.util.Optional;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.Configuration;

public class EnvironmentVariable extends Configuration {

    public EnvironmentVariable(String key) {
        super(key, ENVIRONMENT_VARIABLE);
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(System.getenv(getKey()));
    }

}
