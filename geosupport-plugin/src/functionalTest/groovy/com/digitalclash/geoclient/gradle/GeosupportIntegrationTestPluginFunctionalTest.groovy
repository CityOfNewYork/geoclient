package com.digitalclash.geoclient.gradle

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import spock.lang.TempDir
import spock.lang.Specification

import static com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver.DEFAULT_JNI_VERSION

class GeosupportIntegrationTestPluginFunctionalTest extends Specification {
    @TempDir File testProjectDir
    File settingsFile
    File buildFile

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
    }

    def "geoclient extension defaults are set"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport-integration-test'
            }

            task showGeoclientExtensionDefaults {
                doLast {
                    println(geoclient.jniVersion.get())
                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showGeoclientExtensionDefaults')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains(DEFAULT_JNI_VERSION)
        result.task(":showGeoclientExtensionDefaults").outcome == SUCCESS
    }

    def "geoclient extension uses jniVersion"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport-integration-test'
            }
            geoclient {
                jniVersion = 'geoclient-jni-2.1'
            }
            task showGeoclientExtensionSettings {
                doLast {
                    println(geoclient.jniVersion.get())
                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showGeoclientExtensionSettings')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains('geoclient-jni-2.1')
        result.task(":showGeoclientExtensionSettings").outcome == SUCCESS
    }
}
