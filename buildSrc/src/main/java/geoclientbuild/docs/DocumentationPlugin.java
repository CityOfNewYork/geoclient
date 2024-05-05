package geoclientbuild.docs;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentationPlugin implements Plugin<Project> {

    private Logger logger = LoggerFactory.getLogger(DocumentationPlugin.class);

    public void apply(Project project) {
        logger.info("Applying DocumentationPlugin...");
    }
}
