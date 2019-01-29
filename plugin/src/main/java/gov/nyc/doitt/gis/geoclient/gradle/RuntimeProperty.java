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
    }

    public String getName() {
        return name;
    }

    public Property<PropertySource> getValue() {
        return value;
    }

    public void setValue(PropertySource value) {
        this.value.set(value);
    }

    public ListProperty<PropertySource> getSources() {
        return sources;
    }
}