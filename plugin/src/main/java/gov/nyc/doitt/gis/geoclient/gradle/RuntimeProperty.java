package gov.nyc.doitt.gis.geoclient.gradle;

import java.util.Arrays;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;

public class RuntimeProperty {

    private final String name;
    private final ListProperty<PropertySource> sources;
    private PropertySource value;

    /**
     * Creates a named bean-style object suitable for use with Gradle's
     * {@linkplain NamedDomainObjectContainer} and ${@linkplain project#container}
     * API.
     * 
     * @param name unique name for an instance
     */
    @javax.inject.Inject
    public RuntimeProperty(String name, PropertySource defaultValue, ObjectFactory objectFactory) {
        super();
        this.name = name;
        this.value = defaultValue;
        this.sources = objectFactory.listProperty(PropertySource.class);
        this.sources.convention(Arrays.asList(this.value));
    }

    public String getName() {
        return name;
    }

    public PropertySource getValue() {
        return value;
    }

    public void setValue(PropertySource value) {
        this.value = value;
    }

    public ListProperty<PropertySource> getSources() {
        return sources;
    }
}