package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.environment;
import static gov.nyc.doitt.gis.geoclient.gradle.SourceType.system;

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

    public String environmentValue() {
        SourceType sourceType = this.type.getOrNull();
        if (this.value.isPresent() && sourceType != null && sourceType.equals(environment)) {
            return this.value.toString();
        }
        return null;
    }

    public String systemValue() {
        SourceType sourceType = this.type.getOrNull();
        if (this.value.isPresent() && sourceType != null && sourceType.equals(system)) {
            return this.value.toString();
        }
        return null;
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

    void defaultTo(String name, Object value, SourceType type) {
        this.name.convention(name);
        this.value.convention(value);
        this.type.convention(type);
    }
}