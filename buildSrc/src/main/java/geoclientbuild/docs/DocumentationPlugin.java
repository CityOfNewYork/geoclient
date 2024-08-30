package geoclientbuild.docs;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.gradle.api.plugins.BasePlugin.ASSEMBLE_TASK_NAME;
//import static org.gradle.api.plugins.BasePlugin.BUILD_GROUP;
import static org.gradle.api.plugins.JavaBasePlugin.DOCUMENTATION_GROUP;

public class DocumentationPlugin implements Plugin<Project> {

    public static final String GENERATE_SAMPLES_TASK_NAME = "generateSamples";
    private Logger logger = LoggerFactory.getLogger(DocumentationPlugin.class);

    public void apply(Project project) {
        logger.info("Applying DocumentationPlugin...");
        project.getTasks().register(GENERATE_SAMPLES_TASK_NAME, GenerateSamplesTask.class, t -> {
            t.setGroup(DOCUMENTATION_GROUP);
            t.setDescription("Write JSON responses to geoclient REST calls files in an output folder.");
            t.getRequestsFile().convention(project.getLayout().getBuildDirectory().file("resources/main/requests.json"));
            t.getDestinationDirectory().convention(project.getLayout().getBuildDirectory().dir("generated/samples"));
            t.dependsOn(ASSEMBLE_TASK_NAME);
        });
    }
}
