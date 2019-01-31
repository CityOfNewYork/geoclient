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
    private final String containerName;
    private final NamedDomainObjectContainer<RuntimeProperty> runtimeProperties;

    private final Property<String> fileName;
    private final DirectoryProperty outputDir;

    @javax.inject.Inject
    public RuntimePropertyReport(String containerName, NamedDomainObjectContainer<RuntimeProperty> container,
            Project project) {
        super();
        Objects.requireNonNull(containerName, "Container name argument cannot be null");
        Objects.requireNonNull(container, "NamedDomainObjectContainer<RuntimeProperty> argument cannot be null");
        this.logger = project.getLogger();
        this.containerName = containerName;
        ObjectFactory objectFactory = project.getObjects();
        this.runtimeProperties = container;
        this.fileName = objectFactory.property(String.class);
        this.fileName.convention(String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, containerName));
        this.outputDir = project.getLayout().getBuildDirectory();
    }

    public String getContainerName() {
        return containerName;
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

    @TaskAction
    public void generateReport() throws IOException {
        File destination = getReportFile(this.outputDir.get().getAsFile(), this.fileName.get());
        String content = buildContent(destination);
        logger.quiet(content);
        logger.debug("Writing report to file {}", destination.getCanonicalFile());
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination));) {
            output.write(content);
        }
        logger.lifecycle("Runtime property report written to '" + destination + "'");
    }

    protected String buildContent(File destination) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(formatHeader(this.containerName, destination));
        runtimeProperties.forEach((runtimeProperty) -> {
            buffer.append(String.format("%-16s|%-12s|%-16s|%-32s|%-10s\n", "Property", "Type", "Name", "Value",
                    "resolution"));
            buffer.append(String.format("%s\n",
                    "----------------|------------|----------------|--------------------------------|----------"));

            runtimeProperty.getSources().get().forEach(propertySource -> buffer
                    .append(RuntimePropertyReport.format(runtimeProperty.getName(), propertySource)));

        });
        return buffer.toString();
    }

    public static String format(String name, PropertySource propertySource) {
        String type = propertySource.getType() != null ? propertySource.getType().toString() : "";
        String resolution = propertySource.getResolution() != null ? propertySource.getResolution().toString() : "";
        return String.format("%-16s|%-12s|%-16s|%-32s|%-10s\n", name, type.toUpperCase(), propertySource.getName(),
                propertySource.getValue(), resolution.toUpperCase());
    }

    public static String formatHeader(String containerName, File report) {
        StringBuffer buffer = new StringBuffer();
        // 78 (77 + LF) chars per line
        buffer.append(String.format("\n%s runtime\n", "'" + containerName + "'"));
        buffer.append(String.format("%s\n", "------------------------------------------"));
        buffer.append(String.format("%s: %s\n\n", "report file", report));
        return buffer.toString();
    }

    public static File getReportFile(File dir, String fileName) throws IOException {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destination = new File(dir, fileName);
        if (!destination.exists()) {
            destination.createNewFile();
        }
        return destination;
    }
}