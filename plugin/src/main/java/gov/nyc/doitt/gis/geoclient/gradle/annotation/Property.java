package gov.nyc.doitt.gis.geoclient.gradle.annotation;

import gov.nyc.doitt.gis.geoclient.gradle.ctx.Utils;

public class Property {

    private final String id;
    private final Source source;
    private Object value;

    public Property(String id, Source source) {
        this(id, source, null);
    }

    public Property(String id, Source source, Object value) {
        super();
        this.id = id;
        this.source = source;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public Source getSource() {
        return this.source;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public boolean hasValue() {
        return Utils.hasValueAsString(this.value);
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
        Property other = (Property) obj;
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
