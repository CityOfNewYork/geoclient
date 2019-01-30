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

    protected String buildContent() {
        StringBuffer buffer = new StringBuffer();
        runtimeProperties.forEach((runtimeProperty) -> {
            buffer.append(String.format("%-16s: %s\n", "RuntimeReport:", runtimeProperty.getName()));
            buffer.append(String.format("%-16s: %s\n", "     fileName: '{}'", fileName.getOrNull()));
            buffer.append(String.format("%-16s: %s\n", "    outputDir: '{}'", outputDir.getOrNull()));
            runtimeProperty.getSources().get()
                    .forEach(e -> buffer.append(RuntimePropertyReport.format(e)).append('\n'));

        });
        return buffer.toString();
    }

    @TaskAction
    public void generateReport() throws IOException {
        String content = buildContent();
        logger.quiet(content);
        File destination = getReportFile(this.outputDir.get().getAsFile(), this.fileName.get());
        logger.debug("Writing report to file {}", destination.getCanonicalFile());
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination));) {
            output.write(content);
        }
        logger.lifecycle("Runtime property report written to '" + destination + "'");
    }

    public static String formatHeader(String containerName, File report) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(containerName).append('\n');
        buffer.append("----------------").append('\n');
        buffer.append(String.format("%-16s: %56s\n", "report", report));
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

    public static String format(RuntimeProperty runtimeProperty) {
        StringBuffer buffer = new StringBuffer();
        String.format("%16s %-16s: %-20s %10s", "Source Type", "Name", "Value", "Resolution");
        return buffer.toString();
    }

    public static String format(PropertySource s) {
        String type = s.getType() != null ? s.getType().toString() : "";
        String resolution = s.getResolution() != null ? s.getResolution().toString() : "";
        return format("%16s %-16s: %-20s %10s", "[" + type.toUpperCase() + "]", s.getName(), s.getValue(),
                "<" + resolution.toUpperCase() + ">");
    }

    private static String format(String template, Object... args) {
        return String.format(template, args);
    }
}