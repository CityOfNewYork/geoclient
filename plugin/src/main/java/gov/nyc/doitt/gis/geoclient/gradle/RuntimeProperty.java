package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public class RuntimeProperty {

    private final String name;
    private final Property<PropertySource> defaultValue;
    private final Property<PropertySource> exportValue;
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
        this.defaultValue = objectFactory.property(PropertySource.class);
        this.exportValue = objectFactory.property(PropertySource.class);
        this.sources = objectFactory.listProperty(PropertySource.class);
    }

    public String getName() {
        return name;
    }

    public Property<PropertySource> getDefaultValue() {
        return defaultValue;
    }

    public PropertySource getCurrentDefault() {
        return this.defaultValue.getOrNull();
    }

    public PropertySource getCurrentExport() {
        return this.exportValue.getOrNull();
    }

    public void defaultTo(PropertySource defaultValue) {
        this.defaultValue.set(defaultValue);
    }

    public Property<PropertySource> getExportValue() {
        return exportValue;
    }

    public void exportAs(PropertySource exportValue) {
        this.exportValue.set(exportValue);
    }

    public ListProperty<PropertySource> getSources() {
        return sources;
    }
}