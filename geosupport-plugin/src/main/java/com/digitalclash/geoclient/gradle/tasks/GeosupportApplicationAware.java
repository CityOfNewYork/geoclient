package com.digitalclash.geoclient.gradle.tasks;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.tasks.Nested;

import com.digitalclash.geoclient.gradle.GeosupportApplication;

/*
 * Based on https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/tasks/RegistryCredentialsAware.java
 */
public interface GeosupportApplicationAware extends Task {
   /**
    * The target GeosupportApplication to be used by the task.
    */
    @Nested
    GeosupportApplication getGeosupportApplication();

    /**
     * Configures the target geosupportApplication that the task will use.
     *
     * @param action The action to run on the geosupportApplication instance.
     */
    void geosupportApplication(Action<? super GeosupportApplication> action);

}
