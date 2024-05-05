/*
 * Copyright 2013-2024 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom method implementation for use as a custom condition with
 * {@code org.junit.jupiter.api.condition.EnabledIf}.
 *
 * This class is used instead of a composed annotation because <em>either</em> a
 * matching environment variable <em>or</em> system property will allow the test
 * to run: if both are used to compose an annotation, then <em>both</em> are required.
 *
 * The method is static because it is not part of the test class being annotated.
 *
 * @author mlipper
 * @since 2.0
 * @see <a href="https://junit.org/junit5/docs/5.7.0/user-guide/#writing-tests-conditional-execution-custom">JUnit 5 documentation</a>
 */
public class RequiresRestServiceCustomCondtion {

    public static final String RUNNING_STATUS = "running";
    public static final String ENVIRONMENT_VARIABLE_NAME = "GEOCLIENT_SERVICE_STATUS";
    public static final String SYSTEM_PROPERTY_NAME = "geoclient.service.status";

    static Logger logger = LoggerFactory.getLogger(RequiresRestServiceCustomCondtion.class);

    public static boolean restServiceRunning() {
        String envVar = System.getenv(ENVIRONMENT_VARIABLE_NAME);
        logger.debug("{}={}", ENVIRONMENT_VARIABLE_NAME, envVar);
        if (envVar != null && envVar.equalsIgnoreCase(RUNNING_STATUS)) {
            return true;
        }
        String sysProp = System.getProperty(SYSTEM_PROPERTY_NAME, null);
        logger.debug("{}={}", SYSTEM_PROPERTY_NAME, sysProp);
        if (sysProp != null && sysProp.equalsIgnoreCase(RUNNING_STATUS)) {
            return true;
        }
        return false;
    }
}
