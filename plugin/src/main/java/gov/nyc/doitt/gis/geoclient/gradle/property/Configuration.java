package gov.nyc.doitt.gis.geoclient.gradle.property;

import java.util.Optional;

import gov.nyc.doitt.gis.geoclient.gradle.common.Utils;

public class Configuration {

    private final String id;
    private final Source source;
    private Optional<? extends Object> value;

    public Configuration(String id, Source source) {
        this(id, source, Optional.empty());
    }

    public Configuration(String id, Source source, Object value) {
        this(id, source, Optional.ofNullable(value));
    }

    public Configuration(String id, Source source, String value) {
        this(id, source, Optional.ofNullable(value));
    }

    public Configuration(String id, Source source, Optional<? extends Object> value) {
        super();
        this.id = id;
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

    public String getId() {
        return id;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (source != other.source)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Property [id=" + id + ", source=" + source + ", value=" + value + "]";
    }

}
