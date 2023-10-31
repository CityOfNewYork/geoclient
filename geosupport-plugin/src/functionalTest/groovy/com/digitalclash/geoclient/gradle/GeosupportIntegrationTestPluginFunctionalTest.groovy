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
    String defaultGeosupportHome

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
        defaultGeosupportHome = DEFAULT_HOME_LINUX
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            defaultGeosupportHome = DEFAULT_HOME_WINDOWS
        }
    }

    def "default geosupportIntegrationTest configures a passing JUnit test"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'java-library'
                id 'com.digitalclash.geoclient.gradle.geosupport-integration-test'
            }

            geosupportApplication {
                geosupport {
                    geofiles = '/opt/geosupport/current/fls/'
                }
                integrationTestOptions {
                    testName = 'geosupportIntegrationTest'
                }
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
        println(buildFile)
        File iTestJavaSrcDir = new File(testProjectDir, "src/geosupportIntegrationTest/java/com/example")
        iTestJavaSrcDir.mkdirs()
        File iTestResourcesDir = new File(testProjectDir, "src/geosupportIntegrationTest/resources")
        iTestResourcesDir.mkdirs()
        File junitSrcFile = new File(iTestJavaSrcDir, 'GoatTest.java')
        junitSrcFile << """
        package com.example;

        import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.assertEquals;

        public class GoatTest {
            @Test
            public void testEnvironment() {
                System.out.println(String.format("GEOFILES: %s", System.getenv("GEOFILES")));
                assertEquals("/opt/geosupport/current/fls/", System.getenv("GEOFILES"));
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

    //def "custom geosupportIntegrationTest configures a passing JUnit test"() {
    //    given:
    //    String customSourceSet = 'integrationTest'
    //    createIntegrationTestSources(customSourceSet, "sourceSetName = '${customSourceSet}'", String.format("%s/fls/", defaultGeosupportHome))

    //    when:
    //    def result = GradleRunner.create()
    //        .withProjectDir(testProjectDir)
    //        .withArguments('geosupportIntegrationTest')
    //        .withPluginClasspath()
    //        .forwardOutput()
    //        .build()

    //    then:
    //    result.task(":geosupportIntegrationTest").outcome == SUCCESS
    //}
}
