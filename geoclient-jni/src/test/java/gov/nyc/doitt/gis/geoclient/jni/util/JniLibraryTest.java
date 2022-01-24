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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JniLibraryTest {

    private JniLibrary.Builder builder;
    private JniLibrary jniLibrary;
    private String name;
    private Platform platform;
    private String version;

    @BeforeEach
    void setUp() {

        name = "snafubar";
        platform = new Platform("linux", "x64");
        version = "2.0.0-RELEASE";

        builder = JniLibrary.builder();
        jniLibrary = builder.platform(platform)
                            .name(name)
                            .version(version)
                            .build();
    }

    @Test
    void testBuild() {
        assertAll("JniLibrary.builder",
                ()-> assertSame(platform, jniLibrary.getPlatform()),
                ()-> assertEquals(name, jniLibrary.getName()),
                ()-> assertEquals(version, jniLibrary.getVersion()));
    }

    @Test
    void testBuildRequiresPlatformSet() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            builder.platform(null).build();
        });
        assertEquals("Platform cannot be null", exception.getMessage());
    }

    @Test
    void testBuildRequiresNameSet() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            builder.name(null).build();
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void testBuildDefaultsVersion() {
        jniLibrary = builder.version(null).build();
        assertAll("JniLibrary.builder",
                ()-> assertSame(platform, jniLibrary.getPlatform()),
                ()-> assertEquals(name, jniLibrary.getName()),
                ()-> assertEquals("UNKNOWN", jniLibrary.getVersion()));
    }

    @Test
    void testGetPlatformDirName() {
        assertEquals(platform.getName().replace("_", "-"), jniLibrary.getPlatformDirName());
    }

    @Test
    void testGetLibraryFileName() {
        assertEquals(platform.getSharedLibraryFileName(name), jniLibrary.getLibraryFileName());
    }

    @Test
    void testGetResourceName() {
        String expectedResourceName = String.format("%s/%s", jniLibrary.getPlatformDirName(), jniLibrary.getLibraryFileName());
        assertEquals(expectedResourceName, jniLibrary.getResourceName());
    }
}
