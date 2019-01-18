package gov.nyc.doitt.gis.geoclient.gradle;

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
import org.gradle.api.logging.Logging;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

public class RuntimePropertyReport extends DefaultTask {

    public static final String DEFAULT_REPORT_FILE_NAME = "runtime-properties.txt";
    private static final Logger logger = Logging.getLogger(RuntimePropertyReport.class);
    private final NamedDomainObjectContainer<RuntimeProperty> properties;
    private final Property<String> fileName;
    private final DirectoryProperty outputDir;

    @javax.inject.Inject
    public RuntimePropertyReport(String containerName, NamedDomainObjectContainer<RuntimeProperty> properties,
            Project project) {
        super();
        Objects.requireNonNull(containerName, "Container name argument cannot be null");
        Objects.requireNonNull(properties, "NamedDomainObjectContainer<RuntimeProperty> argument cannot be null");
        ObjectFactory objectFactory = project.getObjects();
        this.properties = properties;
        this.fileName = objectFactory.property(String.class);
        this.fileName.convention(containerName + "-" + DEFAULT_REPORT_FILE_NAME);
        this.outputDir = project.getLayout().getBuildDirectory();
    }

    public DirectoryProperty getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String path) {
        this.outputDir.dir(path);
    }

    public Property<String> getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName.set(name);
    }

    protected String buildContent() {
        StringBuffer buffer = new StringBuffer();
        properties.forEach((p) -> {
            buffer.append(p.format());
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
