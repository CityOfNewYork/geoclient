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

    def "geosupport extension defaults are set"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport-integration-test'
            }

            task showGeosupportExtensionDefaults {
                doLast {
                    println("geosupport_home: \${geosupport.home.get()}")
                    println("geosupport_include_path: \${geosupport.includePath.get()}")
                    println("geosupport_library_path: \${geosupport.libraryPath.get()}")
                    println("geosupport_geofiles: \${geosupport.geofiles.get()}")
                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showGeosupportExtensionDefaults')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains("geosupport_home: ${defaultGeosupportHome}")
        result.output.contains("geosupport_include_path: ${defaultGeosupportHome}/include")
        result.output.contains("geosupport_library_path: ${defaultGeosupportHome}/lib")
        result.output.contains("geosupport_geofiles: ${defaultGeosupportHome}/fls/")
        result.task(":showGeosupportExtensionDefaults").outcome == SUCCESS
    }
}
