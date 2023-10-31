package com.digitalclash.geoclient.gradle.tasks;

import org.gradle.api.Action;
import org.gradle.api.tasks.testing.Test;

import com.digitalclash.geoclient.gradle.GeosupportExtension;
import com.digitalclash.geoclient.gradle.GeosupportIntegrationTestOptions;

public abstract class GeosupportIntegrationTest extends Test implements GeosupportExtensionAware, IntegrationTestOptionsAware {

    private final GeosupportExtension geosupport;
    private final GeosupportIntegrationTestOptions integrationTestOptions;

    public GeosupportIntegrationTest() {
        super();
        geosupport = getProject().getObjects().newInstance(GeosupportExtension.class, getProject().getObjects());
        integrationTestOptions = getProject().getObjects().newInstance(GeosupportIntegrationTestOptions.class, getProject().getObjects());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void geosupport(Action<? super GeosupportExtension> action) {
        action.execute(geosupport);
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    public final GeosupportExtension getGeosupport() {
        return geosupport;
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    public final GeosupportIntegrationTestOptions getIntegrationTestOptions() {
        return integrationTestOptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void integrationTestOptions(Action<? super GeosupportIntegrationTestOptions> action) {
        action.execute(integrationTestOptions);
    }

}
