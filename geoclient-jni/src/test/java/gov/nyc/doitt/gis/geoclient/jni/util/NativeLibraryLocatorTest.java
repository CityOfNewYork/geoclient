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
package gov.nyc.doitt.gis.geoclient.jni.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NativeLibraryLocatorTest {

    final Logger logger = LoggerFactory.getLogger(NativeLibraryLocatorTest.class);

    private final static String tmpDirProperty = "java.io.tmpdir";
    private String backup;
    private File testTmpDir;

    @BeforeEach
    void beforeEach() throws IOException {

        // Backup actual java.io.tmpdir System property
        backup = System.getProperty(tmpDirProperty);
        logger.debug("Actual: {}={}", tmpDirProperty, backup);

        // Create java.io.tmpdir for these tests
        testTmpDir = getTempDir();
        System.setProperty(tmpDirProperty, testTmpDir.getCanonicalPath());
        logger.debug("testTmpDir={}", testTmpDir.getCanonicalPath());
    }

    @AfterEach
    void afterEach() {
        // Restore actual java.io.tmpdir System property
        System.setProperty(tmpDirProperty,backup);
    }

    @Test
    void testFind() throws IOException {
        NativeLibraryLocator locator = new NativeLibraryLocator(testTmpDir.getCanonicalPath());
        File result = locator.find(getJniLibrary());
        assertNotNull(result);
        assertTrue(result.exists());
    }

    private JniLibrary getJniLibrary() throws IOException {
        return JniLibrary.builder().name("geoclientjni").platform(new Platform()).version("X1200").build();
    }

    private File getTempDir() throws IOException {
        URL url = getClass().getClassLoader().getResource(".");
        File root = new File(String.format("%s/../tmplib",url.getPath()));
        return root;
    }
}
