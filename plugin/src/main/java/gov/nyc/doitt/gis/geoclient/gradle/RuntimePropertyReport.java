package gov.nyc.doitt.gis.geoclient.gradle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

public class RuntimePropertyReport extends DefaultTask {

    private final Property<String> containerName;
    private final Property<RuntimeProperty> runtimeProperty;
    private final DirectoryProperty outputDir;

    public RuntimePropertyReport(String containerName, ObjectFactory objectFactory) {
        super();
        this.containerName = objectFactory.property(String.class);
        this.containerName.set(containerName);
        this.runtimeProperty = objectFactory.property(RuntimeProperty.class);
        this.outputDir = objectFactory.directoryProperty();
    }

    public Property<String> getContainerName() {
        return containerName;
    }

    public void setContainerName(String name) {
        this.containerName.set(name);
    }

    public Property<RuntimeProperty> getRuntimeProperty() {
        return runtimeProperty;
    }

    public void setRuntimeProperty(RuntimeProperty runtimeProperty) {
        this.runtimeProperty.set(runtimeProperty);
    }

    public DirectoryProperty getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String path) {
        this.outputDir.dir(path);
    }

    public String getFileName() {
        String fileName = null;
        if (this.outputDir.isPresent()) {
            fileName = this.outputDir.get().getAsFile().getName();
        }
        return fileName;
    }

    public void setFileName(String fileName) {
        this.outputDir.file(fileName);
    }

    @TaskAction
    public void generateReport() throws IOException {
        Objects.requireNonNull(this.containerName.getOrNull(), "Task input 'containerName' cannot be null");
        String name = this.containerName.get();
        Objects.requireNonNull(this.outputDir.getOrNull(), "Task input 'outputDir' must not be null");
        File destination = this.outputDir.get().getAsFile();
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination));) {
            output.write(name);
            Objects.requireNonNull(runtimeProperty.getOrNull(), "Task input of type RuntimeProperty cannot be null");
            RuntimeProperty prop = runtimeProperty.get();
            output.write(prop.getName() + " = '");
        }
        System.out.println(name + " report written to '" + destination + "'");
    }

}
