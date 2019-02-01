package gov.nyc.doitt.gis.geoclient.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class GeoclientPlugin implements Plugin<Project> {

    private static final Logger logger = Logging.getLogger(GeoclientPlugin.class);

    // @formatter:off
    public static final String DEFAULT_REPORT_FILE_NAME_FORMAT = "%s-runtime-properties.txt";
    public static final String RUNTIME_REPORT_TASK_NAME_FORMAT = "%sRuntimeReport";

    public static final String SYSPROP_JAVA_IO_TEMPDIR = "java.io.tempdir";
    public static final String SYSPROP_JAVA_LIBRARY_PATH = "java.library.path";

    public static final String GEOCLIENT_CONTAINER_NAME = "geoclient";

    public static final String GEOCLIENT_REPORT_FILE_NAME = String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, GEOCLIENT_CONTAINER_NAME);
    public static final String GEOCLIENT_REPORT_TASK_NAME = String.format(RUNTIME_REPORT_TASK_NAME_FORMAT, GEOCLIENT_CONTAINER_NAME);

    public static final String GEOSUPPORT_CONTAINER_NAME = "geosupport";

    public static final String GEOSUPPORT_REPORT_FILE_NAME = String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, GEOSUPPORT_CONTAINER_NAME);
    public static final String GEOSUPPORT_REPORT_TASK_NAME = String.format(RUNTIME_REPORT_TASK_NAME_FORMAT, GEOSUPPORT_CONTAINER_NAME);
    // @formatter:on

    public void apply(Project project) {
        GeoclientExtension geoclient = createGeoclientExtension(project);
        createRuntimeReportTask(project, GEOCLIENT_REPORT_TASK_NAME, geoclient);
        GeosupportExtension geosupport = createGeosupportExtension(project);
        createRuntimeReportTask(project, GEOSUPPORT_REPORT_TASK_NAME, geosupport);
    }

    GeoclientExtension createGeoclientExtension(Project project) {
        GeoclientExtension geoclient = new GeoclientExtension(GEOCLIENT_CONTAINER_NAME, project);
        project.getExtensions().add(GEOCLIENT_CONTAINER_NAME, geoclient);
        logger.info("GeoclientExtension container configured successfully");
        return geoclient;
    }

    GeosupportExtension createGeosupportExtension(Project project) {
        GeosupportExtension geosupport = new GeosupportExtension(GEOSUPPORT_CONTAINER_NAME, project);
        project.getExtensions().add(GEOSUPPORT_CONTAINER_NAME, geosupport);
        logger.info("GeosupportExtension configured successfully");
        return geosupport;
    }

    void createRuntimeReportTask(Project project, String task, RuntimePropertyExtension extension) {
        RuntimePropertyReport report = project.getTasks().create(task, RuntimePropertyReport.class, extension, project);
        report.setGroup("verification");
        logger.info("{} task configured successfully", task);
    }
}