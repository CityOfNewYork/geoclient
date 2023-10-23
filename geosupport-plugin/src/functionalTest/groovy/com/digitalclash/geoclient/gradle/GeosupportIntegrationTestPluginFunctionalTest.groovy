package com.digitalclash.geoclient.gradle

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import spock.lang.TempDir
import spock.lang.Specification

import static com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver.DEFAULT_JNI_VERSION
import static com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver.DEFAULT_HOME_LINUX
import static com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver.DEFAULT_HOME_WINDOWS

class GeosupportIntegrationTestPluginFunctionalTest extends Specification {
    @TempDir File testProjectDir
    File settingsFile
    File buildFile
    //File javaSrcDir
    File integrationTestSrcDir
    File goatTestFile
    String defaultGeosupportHome

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
        integrationTestSrcDir = new File(testProjectDir, 'src/geosupportIntegrationTest/java/com/example')
        integrationTestSrcDir.mkdirs()
        //javaSrcDir = new File(testProjectDir, 'main/java/com/example')
        //javaSrcDir.mkdirs()
        goatTestFile = new File(integrationTestSrcDir, 'GoatTest.java')
        defaultGeosupportHome = DEFAULT_HOME_LINUX
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            defaultGeosupportHome = DEFAULT_HOME_WINDOWS
        }
    }
    def "geosupport test environment is configured"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'java-library'
                id 'com.digitalclash.geoclient.gradle.geosupport-integration-test'
            }

            geosupport {
                geofiles = "/usr/local/fls/"
            }

            geosupportIntegrationTest {
                useJUnitPlatform()
                testLogging {
                    info {
                        events "failed", "skipped", "passed"
                        showStandardStreams = true
                    }
                }
            }

            repositories {
                mavenCentral()
            }

            dependencies {
                geosupportIntegrationTestImplementation 'org.junit.jupiter:junit-jupiter:5.7.1'
                geosupportIntegrationTestRuntimeOnly 'org.junit.platform:junit-platform-launcher'

            }
        """
        goatTestFile << """
        package com.example;

        import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.assertEquals;

        public class GoatTest {
            @Test
            public void testEnvironment() {
                System.out.println("GEOFILES: " + System.getenv("GEOFILES"));
                assertEquals("/usr/local/fls/", System.getenv("GEOFILES"));
            }
        }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('geosupportIntegrationTest')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.task(":geosupportIntegrationTest").outcome == SUCCESS
    }
}
