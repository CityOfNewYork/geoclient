/*
 * Copyright 2013-2022 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

// TODO Capture stdout or mock
class IntegrationTestSupportTest {

    private static class IntegrationTest implements IntegrationTestSupport {

        final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

        void debugLogEnvironment() {
            logEnvironment(Level.DEBUG, logger);
        }

        void debugLogSystemProperties() {
            logSystemProperties(Level.DEBUG, logger);
        }

        List<File> getJavaLibraryPathFiles() {
            String javaLibraryPath = getJavaLibraryPath();
            if (javaLibraryPath == null) {
                return Collections.emptyList();
            }
            return filesFromPathString(getJavaLibraryPath());
        }
    }

    private IntegrationTest itestInstance;
    private String existingJavaLibraryPath;

    @BeforeEach
    void setUp() {
        this.itestInstance = new IntegrationTest();
        this.existingJavaLibraryPath = System.getProperty("java.library.path");
    }

    @AfterEach
    void tearDown() {
        System.setProperty("java.library.path", existingJavaLibraryPath);
    }

    @Test
    void testGetSystemPropertyAsFile() {
        File javaHome = new File(System.getProperty("java.home"));
        File javaTmpDir = new File(System.getProperty("java.io.tmpdir"));
        String expectedPath = javaHome + File.pathSeparator + javaTmpDir;
        System.setProperty("java.library.path", expectedPath);
        List<File> actual = itestInstance.getJavaLibraryPathFiles();
        assertFalse(actual.isEmpty());
        assertEquals(2, actual.size());
        assertEquals(actual.get(0), javaHome);
        assertEquals(actual.get(1), javaTmpDir);
    }

    @Test
    void testLogEnvironment() {
        itestInstance.debugLogEnvironment();
    }

    @Test
    void testLogSystemProperties() {
        itestInstance.debugLogSystemProperties();
    }

}
