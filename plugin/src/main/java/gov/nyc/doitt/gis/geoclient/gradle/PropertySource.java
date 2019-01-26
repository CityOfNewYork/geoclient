package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.Resolution.unresolved;

public class PropertySource {
    private final String name;
    private final Object value;
    private final SourceType type;
    private Resolution resolution;

    /**
     * Build property which is taken from an environment variable or Java system
     * property.
     * 
     * @param name  unique name
     * @param value populated by the Gradle plugin dsl
     * @param type  Java system property or environment variable
     */
    public PropertySource(String name, Object value, SourceType type) {
        this(name, value, type, unresolved);
    }

    /**
     * Build property which is taken from an environment variable or Java system
     * property.
     * 
     * @param name  unique name
     * @param value populated by the Gradle plugin dsl
     * @param type  Java system property or environment variable
     * @param resolution how the value was ultimately resolved
     */
    public PropertySource(String name, Object value, SourceType type, Resolution resolution) {
        super();
        this.name = name;
        this.value = value;
        this.type = type;
        this.resolution = resolution;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public SourceType getType() {
        return type;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "PropertySource [name=" + name + ", value=" + value + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        PropertySource other = (PropertySource) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
}