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
