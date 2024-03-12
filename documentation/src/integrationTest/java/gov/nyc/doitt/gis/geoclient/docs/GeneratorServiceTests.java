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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorServiceTests {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorServiceTests.class);

    @ParameterizedTest
    @CsvFileSource(resources = "/address.csv", useHeadersInDisplayName = true)
    void addressExamples(String id, String houseNumber, String street, String borough, String zip, String description) {
        if (borough != null) {
            logger.info("{}. {}: {} {}, {}",id, houseNumber, street, borough, description);
        } else {
            logger.info("{}. {}: {} {}, {}",id, houseNumber, street, zip, description);
            assertNotNull(zip);
        }
        assertNotNull(id);
        assertNotNull(description);
        assertNotNull(houseNumber);
        assertNotNull(street);
    }
}
