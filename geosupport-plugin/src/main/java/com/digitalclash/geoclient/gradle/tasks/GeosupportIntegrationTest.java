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

import com.digitalclash.geoclient.gradle.GeosupportExtension;
import com.digitalclash.geoclient.gradle.GeosupportIntegrationTestOptions;

import org.gradle.api.Action;
import org.gradle.api.tasks.testing.Test;

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
