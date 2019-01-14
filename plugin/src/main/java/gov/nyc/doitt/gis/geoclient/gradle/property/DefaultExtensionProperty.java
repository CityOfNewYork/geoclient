package gov.nyc.doitt.gis.geoclient.gradle.property;

import java.util.Optional;

public class DefaultExtensionProperty extends Configuration {

    public DefaultExtensionProperty(String id, Source source, Object value) {
        super(id, source, value);
    }

    public DefaultExtensionProperty(String id, Source source, Optional<?> value) {
        super(id, source, value);
    }

    public DefaultExtensionProperty(String id, Source source, String value) {
        super(id, source, value);
    }

}
