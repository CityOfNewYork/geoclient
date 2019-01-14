package gov.nyc.doitt.gis.geoclient.gradle.configuration;

import java.util.Optional;

import gov.nyc.doitt.gis.geoclient.gradle.common.Utils;

public class Configuration {

    private final String key;
    private final Source source;
    private Optional<? extends Object> value;

    public Configuration(String key, Source source) {
        this(key, source, Optional.empty());
    }

    public Configuration(String key, Source source, Object value) {
        this(key, source, Optional.ofNullable(value));
    }

    public Configuration(String key, Source source, String value) {
        this(key, source, Optional.ofNullable(value));
    }

    public Configuration(String key, Source source, Optional<? extends Object> value) {
        super();
        this.key = key;
        this.source = source;
        this.value = (value != null) ? value : Optional.empty();
    }

    public Optional<? extends Object> getValue() {
        return value;
    }

    public Source getSource() {
        return this.source;
    }

    public void setValue(Optional<? extends Object> value) {
        this.value = (value != null) ? value : Optional.empty();
    }

    public String getKey() {
        return key;
    }

    public boolean hasValue() {
        if (this.value.isPresent()) {
            return Utils.hasValue(this.value.get());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Configuration other = (Configuration) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (source != other.source)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Property [key=" + key + ", source=" + source + ", value=" + value + "]";
    }

}
