package geoclientbuild.docs;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentationPlugin implements Plugin<Project> {

    public static final String GENERATE_SAMPLES_TASK_NAME = "generateSamples";
    private Logger logger = LoggerFactory.getLogger(DocumentationPlugin.class);

    public void apply(Project project) {
        logger.info("Applying DocumentationPlugin...");
        project.getTasks().register(GENERATE_SAMPLES_TASK_NAME, GenerateSamplesTask.class);
    }
}
