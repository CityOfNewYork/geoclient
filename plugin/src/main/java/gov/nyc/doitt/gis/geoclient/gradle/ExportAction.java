package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.process.JavaForkOptions;

public class ExportAction<T extends Task & JavaForkOptions> implements Action<T> {

    private final RuntimeProperty runtimeProperty;

    public ExportAction(RuntimeProperty runtimeProperty) {
        super();
        this.runtimeProperty = runtimeProperty;
    }

    @Override
    public void execute(T execTask) {
        PropertySource propertySource = runtimeProperty.currentValue();
        ExportType exportType = getExportType(runtimeProperty);
        switch (exportType) {
        case SYSTEM:
            execTask.systemProperty(propertySource.getName(), propertySource.getValue());
            break;
        case ENVIRONMENT:
            execTask.environment(propertySource.getName(), propertySource.getValue());
            break;
        case GRADLE:
            // Fall through
        default:
            ExtraPropertiesExtension ext = execTask.getExtensions().getExtraProperties();
            ext.set(propertySource.getName(), propertySource.getValue());
            break;
        }
    }

    private ExportType getExportType(RuntimeProperty runtimeProperty) {
        if (!runtimeProperty.isExportTypeSpecified()) {
            return runtimeProperty.currentValue().getType().defaultExportType();
        }
        return runtimeProperty.getExportType().get();
    }

}
