package gov.nyc.doitt.gis.geoclient.gradle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.testing.Test;

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
    
    public static final String INTEGRATION_TEST_TASK_NAME = "GeosupportIntegrationTest";
    // @formatter:on

    public void apply(Project project) {
        GeoclientExtension geoclient = createGeoclientExtension(project);
        createRuntimeReportTask(project, GEOCLIENT_REPORT_TASK_NAME, geoclient);

        GeosupportExtension geosupport = createGeosupportExtension(project);
        createRuntimeReportTask(project, GEOSUPPORT_REPORT_TASK_NAME, geosupport);

        createGeosupportIntegrationTestTask(project, Arrays.asList(geoclient, geosupport));
    }

    GeoclientExtension createGeoclientExtension(Project project) {
        GeoclientExtension geoclient = new GeoclientExtension(GEOCLIENT_CONTAINER_NAME,
                new NonDefaultSourcesResolver(project), project);
        project.getExtensions().add(GEOCLIENT_CONTAINER_NAME, geoclient);
        logger.info("GeoclientExtension container CONFIGURED successfully");
        return geoclient;
    }

    GeosupportExtension createGeosupportExtension(Project project) {
        GeosupportExtension geosupport = new GeosupportExtension(GEOSUPPORT_CONTAINER_NAME,
                new NonDefaultSourcesResolver(project), project);
        project.getExtensions().add(GEOSUPPORT_CONTAINER_NAME, geosupport);
        logger.info("GeosupportExtension CONFIGURED successfully");
        return geosupport;
    }

    void createRuntimeReportTask(Project project, String task, RuntimePropertyExtension extension) {
        RuntimePropertyReport report = project.getTasks().create(task, RuntimePropertyReport.class, extension, project);
        report.setGroup("help");
        logger.info("{} task CONFIGURED successfully", task);
    }

    void createGeosupportIntegrationTestTask(Project project, List<RuntimePropertyExtension> extensions) {
        List<RuntimeProperty> allRuntimeProperties = collectRuntimeProperties(extensions);
        TaskProvider<Test> testProvider = project.getTasks().register(INTEGRATION_TEST_TASK_NAME, Test.class);
        getTestExportActions(allRuntimeProperties).forEach(a -> testProvider.configure(a));
    }

    // @formatter:off
    List<RuntimeProperty> collectRuntimeProperties(List<RuntimePropertyExtension> extensions) {
        List<RuntimeProperty> result = null;
        
       result = extensions.stream()
        .map(new Function<RuntimePropertyExtension, List<RuntimeProperty>>() {
            @Override
            public List<RuntimeProperty> apply(RuntimePropertyExtension extension) {
                return extension.getRuntimeProperties().stream().collect(Collectors.toList());
            }
        }).reduce(result, (r, l) -> { 
            r.addAll(l);
            return r;
        });
        return result;
    }
    
    List<ExportAction<Test>> getTestExportActions(List<RuntimeProperty> props) {
        return props.stream()
                    .filter(RuntimeProperty::isResolved)
                    .map(new Function<RuntimeProperty, ExportAction<Test>>() {
                        @Override
                        public ExportAction<Test> apply(RuntimeProperty rp) { return new ExportAction<Test>(rp); }
                    })
                    .collect(Collectors.toList());
    }
    // @formatter:on
}