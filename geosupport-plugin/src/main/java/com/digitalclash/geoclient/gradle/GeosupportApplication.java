package com.digitalclash.geoclient.gradle;

import javax.inject.Inject;
import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Nested;

import com.digitalclash.geoclient.gradle.GeosupportExtension;
import com.digitalclash.geoclient.gradle.GeosupportIntegrationTestOptions;

/**
 * Top-level extension container for nesting other Geosupport extensions.
 */
abstract public class GeosupportApplication {

    @Nested
    abstract public GeosupportExtension getGeosupport();

    @Nested
    abstract public GeosupportIntegrationTestOptions getIntegrationTestOptions();

    public void geosupport(Action<? super GeosupportExtension> action) {
        action.execute(getGeosupport());
    }

    public void integrationTestOptions(Action<? super GeosupportIntegrationTestOptions> action) {
        action.execute(getIntegrationTestOptions());
    }
}
