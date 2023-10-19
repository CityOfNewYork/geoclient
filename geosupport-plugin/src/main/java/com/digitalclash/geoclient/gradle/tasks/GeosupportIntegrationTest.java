package com.digitalclash.geoclient.gradle.tasks;

import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.testing.Test;

public abstract class GeosupportIntegrationTest extends Test {

    public GeosupportIntegrationTest() {
        super();
    }

    @Override
    @TaskAction
    public void executeTests() {
        super.executeTests();
    }
}
