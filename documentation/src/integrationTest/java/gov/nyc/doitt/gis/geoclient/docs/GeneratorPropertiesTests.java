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
package gov.nyc.doitt.gis.geoclient.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorPropertiesTests {

    private static final Logger logger = LoggerFactory.getLogger(SampleGeneratorApplication.class);

    // TODO Temporarily set in build.gradle
    public static String BASEURL_ENV_VARIABLE =   "GENERATOR_BASEURL";
    public static String OUTPUTDIR_ENV_VARIABLE = "GENERATOR_OUTPUTDIR";

    @Autowired
    private GeneratorProperties props;

    @BeforeAll
    public static void setUpAll() {
        assertNotNull(System.getenv(BASEURL_ENV_VARIABLE));
        logger.info("{}={}", BASEURL_ENV_VARIABLE, System.getenv(BASEURL_ENV_VARIABLE));
        assertNotNull(System.getenv(OUTPUTDIR_ENV_VARIABLE));
        logger.info("{}={}", OUTPUTDIR_ENV_VARIABLE, System.getenv(OUTPUTDIR_ENV_VARIABLE));
    }

    @BeforeEach
    public void setUp() {
        assertNotNull(this.props);
    }

    @Test
    public void testConfiguredFromEnvironmentVariables() {
        assertNotNull(this.props.getBaseUrl());
        assertEquals(System.getenv(BASEURL_ENV_VARIABLE), this.props.getBaseUrl());
        assertNotNull(this.props.getOutputDir());
        assertEquals(System.getenv(OUTPUTDIR_ENV_VARIABLE), this.props.getOutputDir().getAbsolutePath());
    }
}
