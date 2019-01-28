package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.DEFAULT_REPORT_FILE_NAME_FORMAT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public class RuntimePropertyReport extends DefaultTask {

    private final Logger logger;
    private final NamedDomainObjectContainer<RuntimeProperty> properties;

    private final Property<String> fileName;
    private final DirectoryProperty outputDir;

    @javax.inject.Inject
    public RuntimePropertyReport(String containerName, NamedDomainObjectContainer<RuntimeProperty> properties,
            Project project) {
        super();
        Objects.requireNonNull(containerName, "Container name argument cannot be null");
        Objects.requireNonNull(properties, "NamedDomainObjectContainer<RuntimeProperty> argument cannot be null");
        this.logger = project.getLogger();
        ObjectFactory objectFactory = project.getObjects();
        this.properties = properties;
        this.fileName = objectFactory.property(String.class);
        this.fileName.convention(String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, containerName));
        this.outputDir = project.getLayout().getBuildDirectory();
    }

    @Input
    public DirectoryProperty getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String path) {
        this.outputDir.dir(path);
    }

    @Input
    public Property<String> getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName.set(name);
    }

    private void logState() {
        logger.quiet("RuntimeReport");
        logger.quiet("        fileName: '{}'", fileName.getOrNull());
        logger.quiet("       outputDir: '{}'", outputDir.getOrNull());
        logger.quiet("      properties:");
        this.properties.getAsMap().entrySet().forEach(e -> {
            logger.quiet("                  '{}' = '{}'");
        });
    }

    protected String buildContent() {
        logState();
        StringBuffer buffer = new StringBuffer();
        properties.forEach((p) -> {
            buffer.append(FormatUtils.format(p) + '\n');
        });
        return buffer.toString();
    }

    @TaskAction
    public void generateReport() throws IOException {
        String content = buildContent();
        logger.quiet(content);
        File dir = this.outputDir.get().getAsFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destination = new File(dir, this.fileName.get());
        if (!destination.exists()) {
            destination.createNewFile();
        }
        logger.quiet("destination: {}", destination.getCanonicalFile());
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination));) {
            output.write(content);
        }
        logger.lifecycle("Runtime property report written to '" + destination + "'");
    }

}
