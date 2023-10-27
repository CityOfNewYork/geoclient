package com.digitalclash.geoclient.gradle

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import spock.lang.TempDir
import spock.lang.Specification

import static com.digitalclash.geoclient.gradle.GeosupportPlugin.DEFAULT_INTEGRATION_TEST_JAVA_SOURCE_DIR
import static com.digitalclash.geoclient.gradle.GeosupportPlugin.DEFAULT_INTEGRATION_TEST_NAME
import static com.digitalclash.geoclient.gradle.GeosupportPlugin.DEFAULT_INTEGRATION_TEST_RESOURCES_SOURCE_DIR
import static com.digitalclash.geoclient.gradle.GeosupportPlugin.DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME
import static com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver.DEFAULT_JNI_VERSION
import static com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver.DEFAULT_HOME_LINUX
import static com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver.DEFAULT_HOME_WINDOWS

class GeosupportPluginFunctionalTest extends Specification {
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

    def "geosupportApplication defaults are set"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport'
            }

            task showGeosupportApplicationDefaults {
                doLast {
                    def geosupportExtension = geosupportApplication.geosupport
                    println("geosupport.geofiles=\${geosupportExtension.geofiles.get()}")
                    println("geosupport.home=\${geosupportExtension.home.get()}")
                    println("geosupport.includePath=\${geosupportExtension.includePath.get()}")
                    println("geosupport.libraryPath=\${geosupportExtension.libraryPath.get()}")
                    def integrationTestOpts = geosupportApplication.integrationTestOptions
                    println("integrationTestOptions.testName=\${integrationTestOpts.testName.get()}")
                    println("integrationTestOptions.sourceSetName=\${integrationTestOpts.sourceSetName.get()}")
                    println("integrationTestOptions.javaSourceDir=\${integrationTestOpts.javaSourceDir.get()}")
                    println("integrationTestOptions.resourcesSourceDir=\${integrationTestOpts.resourcesSourceDir.get()}")
                    println("integrationTestOptions.validate=\${integrationTestOpts.validate.get()}")
                    println("integrationTestOptions.useJavaLibraryPath=\${integrationTestOpts.useJavaLibraryPath.get()}")
                    println("integrationTestOptions.exportLdLibraryPath=\${integrationTestOpts.exportLdLibraryPath.get()}")

                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showGeosupportApplicationDefaults')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains("geosupport.geofiles=${defaultGeosupportHome}/fls/")
        result.output.contains("geosupport.home=${defaultGeosupportHome}")
        result.output.contains("geosupport.includePath=${defaultGeosupportHome}/include")
        result.output.contains("geosupport.libraryPath=${defaultGeosupportHome}/lib")
        result.output.contains("integrationTestOptions.testName=${DEFAULT_INTEGRATION_TEST_NAME}")
        result.output.contains("integrationTestOptions.sourceSetName=${DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME}")
        result.output.contains("integrationTestOptions.javaSourceDir=${testProjectDir}/${DEFAULT_INTEGRATION_TEST_JAVA_SOURCE_DIR}")
        result.output.contains("integrationTestOptions.resourcesSourceDir=${testProjectDir}/${DEFAULT_INTEGRATION_TEST_RESOURCES_SOURCE_DIR}")
        result.output.contains("integrationTestOptions.validate=false")
        result.output.contains("integrationTestOptions.useJavaLibraryPath=false")
        result.output.contains("integrationTestOptions.exportLdLibraryPath=false")

        result.task(":showGeosupportApplicationDefaults").outcome == SUCCESS
    }

    def "geosupportExtension can be customized"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport'
            }

            geosupportApplication {
                geosupport {
                    geofiles = '/usr/local/fls/'
                    home = '/geosupport/current'
                    includePath = file('/lib/include')
                    libraryPath = '/usr/local/lib'
                }
            }

            task showCustomGeosupportExtension {
                doLast {
                    def geosupportExt = geosupportApplication.geosupport
                    println("geosupport.geofiles: \${geosupportExt.geofiles.get()}")
                    println("geosupport.home: \${geosupportExt.home.get()}")
                    println("geosupport.includePath: \${geosupportExt.includePath.get()}")
                    println("geosupport.libraryPath: \${geosupportExt.libraryPath.get()}")
                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showCustomGeosupportExtension')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains('geosupport.geofiles: /usr/local/fls/')
        result.output.contains('geosupport.home: /geosupport/current')
        result.output.contains('geosupport.includePath: /lib/include')
        result.output.contains('geosupport.libraryPath: /usr/local/lib')
        result.task(":showCustomGeosupportExtension").outcome == SUCCESS
    }

    def "integrationTestOptions can be customized"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport'
            }

            geosupportApplication {
                integrationTestOptions {
                    testName = 'goatTest'
                    sourceSetName = 'goatee'
                    javaSourceDir = layout.projectDirectory.dir('src/iTest/java')
                    resourcesSourceDir = layout.projectDirectory.dir('src/intTest/resources')
                    validate = true
                    useJavaLibraryPath = true
                    exportLdLibraryPath = true
                }
            }

            task showCustomIntegrationTestOptions {
                doLast {
                    def integrationTestOpts = geosupportApplication.integrationTestOptions
                    println("integrationTestOptions.testName=\${integrationTestOpts.testName.get()}")
                    println("integrationTestOptions.sourceSetName=\${integrationTestOpts.sourceSetName.get()}")
                    println("integrationTestOptions.javaSourceDir=\${integrationTestOpts.javaSourceDir.get()}")
                    println("integrationTestOptions.resourcesSourceDir=\${integrationTestOpts.resourcesSourceDir.get()}")
                    println("integrationTestOptions.validate=\${integrationTestOpts.validate.get()}")
                    println("integrationTestOptions.useJavaLibraryPath=\${integrationTestOpts.useJavaLibraryPath.get()}")
                    println("integrationTestOptions.exportLdLibraryPath=\${integrationTestOpts.exportLdLibraryPath.get()}")

                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showCustomIntegrationTestOptions')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains("integrationTestOptions.testName=goatTest")
        result.output.contains("integrationTestOptions.sourceSetName=goatee")
        result.output.contains("integrationTestOptions.javaSourceDir=${testProjectDir}/src/iTest/java")
        result.output.contains("integrationTestOptions.resourcesSourceDir=${testProjectDir}/src/intTest/resources")
        result.output.contains("integrationTestOptions.validate=true")
        result.output.contains("integrationTestOptions.useJavaLibraryPath=true")
        result.output.contains("integrationTestOptions.exportLdLibraryPath=true")

        result.task(":showCustomIntegrationTestOptions").outcome == SUCCESS
    }

    def "geoclientExtension defaults are set"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport'
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

    def "geoclientExtension can be customized"() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
            plugins {
                id 'com.digitalclash.geoclient.gradle.geosupport'
            }
            geoclient {
                jniVersion = 'geoclient-jni-2.1'
            }
            task showCustomGeoclientExtension {
                doLast {
                    println(geoclient.jniVersion.get())
                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('showCustomGeoclientExtension')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.output.contains('geoclient-jni-2.1')
        result.task(":showCustomGeoclientExtension").outcome == SUCCESS
    }
}
