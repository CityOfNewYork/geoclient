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

    // Sets this.value but does not add it to this.sources
    public void setValue(PropertySource value) {
        System.out.println(String.format("Setting final value (%s)", value));
        this.value.set(value);
    }

    // Sets this.value and calls this.sources.empty() to initialize this.sources
    // with an empty list
    public void setConventions(PropertySource value) {
        System.out.println(String.format("Setting value with convention(%s)", value));
        this.value.convention(value);
        this.sources.empty();
    }

    // Adds this.value to this.sources and calls finalize on both properties
    // This should be called if none of the user-supplied PropertySources in
    // this.sources can be resolved
    public PropertySource finalizeWithCurrentValue() {
        if (!this.value.isPresent()) {
            throw new IllegalStateException(this + " 'value' is null");
        }
        if (!this.sources.isPresent()) {
            throw new IllegalStateException(this + " 'sources' is null");
        }
        this.value.finalizeValue();
        this.getSources().add(this.value);
        this.sources.finalizeValue();
        return this.value.get();
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