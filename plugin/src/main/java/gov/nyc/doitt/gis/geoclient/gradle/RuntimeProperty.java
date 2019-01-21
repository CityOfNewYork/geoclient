package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class RuntimeProperty {

    private final String name;
    private final Property<PropertySource> source;
    private final Property<String> gradleProperty;

    /**
     * Creates a named bean-style object suitable for use with Gradle's
     * {@linkplain NamedDomainObjectContainer} and ${@linkplain project#container}
     * API.
     * 
     * @param name unique name for an instance
     */
    public RuntimeProperty(String name, ObjectFactory objectFactory) {
        super();
        this.name = name;
        this.source = objectFactory.property(PropertySource.class);
        this.gradleProperty = objectFactory.property(String.class);
    }

    public String getName() {
        return name;
    }

    public Property<PropertySource> getSource() {
        return source;
    }

    public void setSource(PropertySource source) {
        this.source.set(source);
    }

    public Property<String> getGradleProperty() {
        return gradleProperty;
    }

    void defaultTo(PropertySource source, String gradleProperty) {
        this.source.convention(source);
        this.gradleProperty.convention(gradleProperty);
    }

    public String format() {
	return FormatUtils.format(this);
    }

    @Override
    public String toString() {
        return "RuntimeProperty [name=" + name + ", source=" + source + ", gradleProperty=" + gradleProperty + "]";
    }

}
