package gov.nyc.doitt.gis.geoclient.gradle.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

public class GeosupportInfo extends DefaultTask {

    @Internal
    private final GeoclientExtension geoclient;

    @Internal
    private final GeosupportExtension geosupport;

    private String fileName;

    private File outputDir;

    @Inject
    public GeosupportInfo(Project project) {
        this.geoclient = project.getExtensions().getByType(GeoclientExtension.class);
        this.geosupport = project.getExtensions().getByType(GeosupportExtension.class);
        this.fileName = String.format("%s.txt", project.getName());
        this.outputDir = project.getBuildDir();
    }

    @Input
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @OutputDirectory
    public File getOutputDir() {
        return this.outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    @TaskAction
    void info() throws IOException {
        StringBuffer sb = new StringBuffer();
        // GeoclientExtension info
        sb.append("geoclient.nativeTempDir");
        sb.append("=");
        sb.append(format(this.geoclient.getNativeTempDir()) + "\n");
        // GeosupportExtension info
        sb.append("geosupport.geofiles");
        sb.append("=");
        sb.append(format(this.geosupport.getGeofiles()) + "\n");
        sb.append("geosupport.home");
        sb.append("=");
        sb.append(format(this.geosupport.getHome()) + "\n");
        sb.append("geosupport.libraryPath");
        sb.append("=");
        sb.append(format(this.geosupport.getLibraryPath()) + "\n");
        writeFile(new File(this.outputDir, this.fileName), sb.toString());
        System.out.println(sb.toString());
    }

    private String format(DirectoryProperty dirProperty) throws IOException {
        Provider<File> dirProvider = dirProperty.getAsFile();
        if (dirProvider.isPresent()) {
            File dir = dirProvider.get();
            if (dir.exists()) {
                return "'" + dir.getCanonicalPath() + "'";
            }
            return "'" + dir.getAbsolutePath() + "'";
        }
        return "''";
    }

    private String format(Property<String> stringProperty) {
        if (stringProperty.isPresent()) {
            return "'" + stringProperty.get() + "'";
        }
        return "''";
    }

    private void writeFile(File destination, String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination));) {
            output.write(content);
        }
        System.out.println(getClass().getSimpleName() + " report written to '" + destination + "'");
    }

}
