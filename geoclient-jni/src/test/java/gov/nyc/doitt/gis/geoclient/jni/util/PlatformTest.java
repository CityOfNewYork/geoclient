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

import static gov.nyc.doitt.gis.geoclient.jni.util.Platform.LINUX_OS_FAMILY;
import static gov.nyc.doitt.gis.geoclient.jni.util.Platform.SUPPORTED_LINUX_PLATFORM;
import static gov.nyc.doitt.gis.geoclient.jni.util.Platform.SUPPORTED_WINDOWS_PLATFORM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlatformTest {

    static Stream<Arguments> expectedPlatformAndVaildConstructorArgsProvider() {
        return Stream.of(
                Arguments.of(SUPPORTED_LINUX_PLATFORM, "LINUX dammit", "X64"),
                Arguments.of(SUPPORTED_LINUX_PLATFORM, "lInux", "64"),
                Arguments.of(SUPPORTED_WINDOWS_PLATFORM, "closeallwindows", "x86_64"),
                Arguments.of(SUPPORTED_WINDOWS_PLATFORM, "  windows 56 ", "x86_64   ")
        );
    }

    @Test
    @DisplayName("Constructor recognizes supported Linux platform given funky args")
    void testConstructorRecognizesSupportedLinuxPlatform() {
        Platform p = new Platform("Linux","12323646");
        assertEquals(SUPPORTED_LINUX_PLATFORM.getName(), p.getName());
        assertNotEquals(SUPPORTED_LINUX_PLATFORM.getName(), p.getOperatingSystem());
        assertNotEquals(SUPPORTED_LINUX_PLATFORM.getArchitecture(), p.getOperatingSystem());
    }

    @Test
    @DisplayName("Constructor recognizes supported Windows platform given funky args")
    void testConstructorRecognizesSupportedWindowsPlatform() {
        Platform p = new Platform("WINDOWS 46","amd64");
        assertEquals(SUPPORTED_WINDOWS_PLATFORM.getName(), p.getName());
        assertNotEquals(SUPPORTED_WINDOWS_PLATFORM.getOperatingSystem(), p.getOperatingSystem());
        assertNotEquals(SUPPORTED_WINDOWS_PLATFORM.getArchitecture(), p.getOperatingSystem());
    }

    @Test
    @DisplayName("Constructor throws UnsupportedPlatformException for invalid arguments")
    void testConstructorThrowsExceptionOnInvalidArguments() {

        Throwable exception = assertThrows(UnsupportedPlatformException.class, () -> { new Platform("Windows 7","amd32"); });
        assertEquals("Unsupported JNI platform: OS='Windows 7' ARCH='amd32'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> { new Platform(null,"amd32"); });
        assertEquals("Argument 'operatingSystem' cannot be null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> { new Platform("Windows 10",null); });
        assertEquals("Argument 'architecture' cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor called with valid args creates Platform in expected state")
    void testConstructorWithValidArgsCreatesPlatformInExpectedState() {
        assertTrue(SUPPORTED_LINUX_PLATFORM.isLinux());
        assertFalse(SUPPORTED_LINUX_PLATFORM.isWindows());
        assertTrue(SUPPORTED_WINDOWS_PLATFORM.isWindows());
        assertFalse(SUPPORTED_WINDOWS_PLATFORM.isLinux());
    }

    @Test
    @DisplayName("Default constructor creates Platform in expected state")
    @EnabledOnOs({LINUX, WINDOWS})
    @EnabledIfSystemProperty(named="os.arch", matches = ".*64.*")
    void testDefaultConstructorCreatesPlatformInExpectedState() {
        Platform p =new Platform();
        assertTrue(new Platform().is64Bit());
        if(System.getProperty("os.name").toLowerCase().contains(LINUX_OS_FAMILY)) {
            assertTrue(p.isLinux());
            assertFalse(p.isWindows());
        } else {
            assertTrue(p.isWindows());
            assertFalse(p.isLinux());
        }
    }

    @Test
    @DisplayName("Default constructor recognizes current platform")
    @EnabledIfSystemProperty(named="os.arch", matches = ".*64.*")
    @EnabledOnOs({LINUX, WINDOWS})
    void testDefaultConstructorRecognizesCurrentPlatform() {
        String currentOs = System.getProperty("os.name");
        String currentArch = System.getProperty("os.arch");
        // Default constructor derives OS and arch from Java system properties
        Platform actualCurrentPlatform = new Platform();

        Platform p = new Platform(currentOs,currentArch);
        assertEquals(currentOs, p.getOperatingSystem());
        assertEquals(currentArch, p.getArchitecture());
        assertEquals(actualCurrentPlatform, p);
    }

    // TODO Implement with mock
    @Disabled
    @Test
    @DisplayName("Default constructor throws UnsupportedPlatformException if System.getProperty for 'os.name' or 'os.arch' returns null")
    @EnabledIfSystemProperty(named="os.arch", matches = ".*64.*")
    @EnabledOnOs({LINUX, WINDOWS})
    void testDefaultConstructorThrowsExceptionOnMissingSystemProperties() {
        //String currentOs = mock(System.getProperty("os.name")).returns("Windows 7");
        //String currentArch = mock(System.getProperty("os.arch")).returns("amd32");
        // Default constructor derives OS and arch from Java system properties
        Throwable exception = assertThrows(UnsupportedPlatformException.class, () -> { new Platform(); });
        assertEquals("Unsupported JNI platform: OS='Windows 7' ARCH='amd32'", exception.getMessage());
    }

    @Test
    void testGetSharedLibraryFileName() {
        assertEquals("libwoof.so",SUPPORTED_LINUX_PLATFORM.getSharedLibraryFileName("woof"));
        assertEquals("Meow.dll",SUPPORTED_WINDOWS_PLATFORM.getSharedLibraryFileName("Meow"));
    }

    @ParameterizedTest
    @DisplayName("Platform name corresponds to equality")
    @MethodSource("expectedPlatformAndVaildConstructorArgsProvider")
    void testPlatformNameCorrespondsToEquality(Platform platform, String operatingSystem, String architecture) {
        assertEquals(platform, new Platform(operatingSystem, architecture));
        assertEquals(platform.getName(), new Platform(operatingSystem, architecture).getName());
        assertNotEquals(platform.getOperatingSystem(), new Platform(operatingSystem, architecture).getOperatingSystem());
        assertNotEquals(platform.getArchitecture(), new Platform(operatingSystem, architecture).getArchitecture());
    }

    @Test
    @DisplayName("Valid operatingSystem and architecture are recognized, preserved but normalized for platform name")
    void testValidOsAndArchIsRecognized() {
        String expectedOs = "windows 10K";
        String expectedArch = "1264xx";
        Platform p = new Platform(expectedOs, expectedArch);
        assertEquals(SUPPORTED_WINDOWS_PLATFORM.getName(), p.getName());
        assertEquals(expectedOs, p.getOperatingSystem());
        assertEquals(expectedArch, p.getArchitecture());
        expectedOs = "GNU Linux";
        expectedArch = "64 chocolate pies";
        p = new Platform(expectedOs, expectedArch);
        assertEquals(SUPPORTED_LINUX_PLATFORM.getName(), p.getName());
        assertEquals(expectedOs, p.getOperatingSystem());
        assertEquals(expectedArch, p.getArchitecture());
    }
}
