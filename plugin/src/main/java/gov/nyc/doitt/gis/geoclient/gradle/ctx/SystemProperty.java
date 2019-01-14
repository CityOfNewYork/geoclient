package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.SYSTEM_PROPERTY;

import java.util.Optional;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.Configuration;

public class SystemProperty extends Configuration {

    public SystemProperty(String key) {
        super(key, SYSTEM_PROPERTY);
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(System.getProperty(getKey()));
    }

}
