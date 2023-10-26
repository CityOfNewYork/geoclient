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
package com.digitalclash.geoclient.gradle;

import javax.inject.Inject;

import com.digitalclash.geoclient.gradle.GeosupportExtension;
import com.digitalclash.geoclient.gradle.GeosupportIntegrationTestOptions;

import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Nested;

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
