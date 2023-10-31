/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.digitalclash.geoclient.gradle.tasks;

import com.digitalclash.geoclient.gradle.IntegrationTestOptions;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.tasks.Nested;

/*
 * Based on https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/tasks/RegistryCredentialsAware.java
 */
public interface IntegrationTestOptionsAware extends Task {
   /**
    * The target IntegrationTestOptions to be used by the task.
    */
    @Nested
    IntegrationTestOptions getIntegrationTestOptions();

    /**
     * Configures the target IntegrationTestOptions that the task will use.
     *
     * @param action The action to run on the IntegrationTestOptions instance.
     */
    void integrationTestOptions(Action<? super IntegrationTestOptions> action);
}
