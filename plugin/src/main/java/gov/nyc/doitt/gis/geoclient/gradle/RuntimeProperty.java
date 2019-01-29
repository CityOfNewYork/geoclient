package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public class RuntimeProperty {

    private final String name;
    private final Property<PropertySource> value;
    private final ListProperty<PropertySource> sources;

    /**
     * Creates a named bean-style object suitable for use with Gradle's
     * {@linkplain NamedDomainObjectContainer} and ${@linkplain project#container}
     * API.
     * 
     * @param name unique name for an instance
     */
    @javax.inject.Inject
    public RuntimeProperty(String name, ObjectFactory objectFactory) {
        super();
        this.name = name;
        this.value = objectFactory.property(PropertySource.class);
        this.sources = objectFactory.listProperty(PropertySource.class);
        System.out.println(String.format("RuntimeProperty constructed %s", this));
    }

    public String getName() {
        return name;
    }

    public Property<PropertySource> getValue() {
        return value;
    }

    public void setValue(PropertySource value) {
        System.out.println(String.format("Setting final value (%s)", value));
        this.value.set(value);
    }

    public void setValueConvention(PropertySource value) {
        System.out.println(String.format("Setting value with convention(%s)", value));
        this.value.convention(value);
    }

    public PropertySource getDefaultValue() {
        if (this.value.isPresent()) {
            System.out.println(String.format("Returning default value (%s)", value));
            return this.value.get();
        }
        System.out.println("Returning null default value");
        return null;
    }

    public ListProperty<PropertySource> getSources() {
        return sources;
    }

    @Override
    public String toString() {
        return "RuntimeProperty [name=" + name + ", value=" + value + ", sources=" + sources + "]";
    }
}