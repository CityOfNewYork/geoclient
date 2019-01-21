package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class PropertySource {
    private final Property<String> name;
    private final Property<Object> value;
    private final Property<SourceType> type;

    /**
     * Build property which is taken from an environment variable or Java system
     * property.
     * 
     * @param name  unique name
     * @param value populated by the Gradle plugin dsl
     * @param type  Java system property or environment variable
     */
    @javax.inject.Inject
    public PropertySource(ObjectFactory objectFactory) {
        super();
        this.name = objectFactory.property(String.class);
        this.value = objectFactory.property(Object.class);
        this.type = objectFactory.property(SourceType.class);
    }

    public Property<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Property<? extends Object> getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value.set(value);
    }

    public Property<SourceType> getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type.set(type);
    }

    public String nullSafeName() {
        return this.name.getOrElse("");
    }

    public String nullSafeType() {
        return this.type.isPresent() ? this.type.get().toString() : "";
    }

    public String nullSafeValue() {
        return this.value.isPresent() ? this.value.get().toString() : "";
    }

    void defaultTo(String name, Object value, SourceType type) {
        this.name.convention(name);
        this.value.convention(value);
        this.type.convention(type);
    }

    public String format() {
        return FormatUtils.format(this);
    }

    @Override
    public String toString() {
        return "PropertySource [name=" + name + ", value=" + value + ", type=" + type + "]";
    }
}