package gov.nyc.doitt.gis.geoclient.gradle;

import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;

import gov.nyc.doitt.gis.geoclient.gradle.annotation.Property;
import gov.nyc.doitt.gis.geoclient.gradle.annotation.PropertySource;
import gov.nyc.doitt.gis.geoclient.gradle.annotation.PropertySources;
import gov.nyc.doitt.gis.geoclient.gradle.annotation.Source;
import gov.nyc.doitt.gis.geoclient.gradle.ctx.AbstractPluginExtension;

public class GeoclientExtension extends AbstractPluginExtension {

    private final DirectoryProperty nativeTempDir;

    public GeoclientExtension(Project project) {
        super(project);
        this.nativeTempDir = directoryProperty();
    }

    @PropertySources(value = { @PropertySource(Source.EXTENSION_PROPERTY), @PropertySource(Source.EXTENSION_DEFAULT) })
    public DirectoryProperty getNativeTempDir() {
        return this.nativeTempDir;
    }

    @Override
    public String getNamespace() {
        return "gc";
    }

    @Override
    protected void initialize(List<Property> properties) {
        properties.add(withIdFromSource("NativeTempDir", Source.SYSTEM_PROPERTY));
    }

}
