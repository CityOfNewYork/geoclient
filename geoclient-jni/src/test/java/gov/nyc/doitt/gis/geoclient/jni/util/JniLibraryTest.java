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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        this.name = "snafubar";
        this.platform = new Platform("linux", "x64");
        this.version = "2.0.0-RELEASE";

        this.builder = JniLibrary.builder();
        this.jniLibrary = this.builder.platform(this.platform)
                            .name(this.name)
                            .version(this.version)
                            .build();
    }

    @Test
    void testBuild() {
        assertAll("JniLibrary.builder",
                ()-> assertSame(this.platform, this.jniLibrary.getPlatform()),
                ()-> assertEquals(this.name, this.jniLibrary.getName()),
                ()-> assertEquals(this.version, this.jniLibrary.getVersion()));
    }

    @Test
    void testBuildRequiresPlatformSet() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            this.builder.platform(null).build();
        });
        assertEquals("Platform cannot be null", exception.getMessage());
    }

    @Test
    void testBuildRequiresNameSet() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            this.builder.name(null).build();
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void testBuildDefaultsVersion() {
        this.jniLibrary = this.builder.version(null).build();
        assertAll("JniLibrary.builder",
                ()-> assertSame(this.platform, this.jniLibrary.getPlatform()),
                ()-> assertEquals(this.name, this.jniLibrary.getName()),
                ()-> assertEquals("UNKNOWN", this.jniLibrary.getVersion()));
    }

    @Test
    void testGetPlatformDirName() {
        assertEquals(this.platform.getName().replace("_", "-"), this.jniLibrary.getPlatformDirName());
    }

    @Test
    void testGetLibraryFileName() {
        assertEquals(this.platform.getSharedLibraryFileName(this.name), this.jniLibrary.getLibraryFileName());
    }

    @Test
    void testGetResourceName() {
        String expectedResourceName = String.format("%s/%s", this.jniLibrary.getPlatformDirName(), this.jniLibrary.getLibraryFileName());
        assertEquals(expectedResourceName, this.jniLibrary.getResourceName());
    }
}
