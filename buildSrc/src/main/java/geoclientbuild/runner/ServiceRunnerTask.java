package geoclientbuild.runner;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkerExecutor;
import javax.inject.Inject;

public abstract class ServiceRunnerTask extends DefaultTask {
    @Input
    abstract ListProperty<String> getActiveProfiles();
    @InputFile
    abstract RegularFileProperty getJarFile();
    @Input
    abstract ListProperty<String> getJvmArguments();
    @Inject
    abstract public WorkerExecutor getWorkerExecutor();

    @TaskAction
    public void execute() {

    }
}
