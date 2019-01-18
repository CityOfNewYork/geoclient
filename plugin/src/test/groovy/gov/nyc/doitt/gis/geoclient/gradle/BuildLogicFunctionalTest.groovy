/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gov.nyc.doitt.gis.geoclient.gradle

import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_CONTAINER_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOSUPPORT_CONTAINER_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.RuntimePropertyReport.DEFAULT_REPORT_FILE_NAME
import static org.gradle.testkit.runner.TaskOutcome.*

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Specification

class BuildLogicFunctionalTest extends Specification {

    final static String PROJECT_NAME = 'plugin-funtest'
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile
    File settingsFile
    String geoclientReport
    String geosupportReport

    def setup() {
        settingsFile = testProjectDir.newFile('settings.gradle')
        buildFile = testProjectDir.newFile('build.gradle')
        geoclientReport = new File("${testProjectDir.root}/build/${GEOCLIENT_CONTAINER_NAME}-${DEFAULT_REPORT_FILE_NAME}").absolutePath
        geosupportReport = new File("${testProjectDir.root}/build/${GEOSUPPORT_CONTAINER_NAME}-${DEFAULT_REPORT_FILE_NAME}").absolutePath
    }

    def "plugin without any configuration succeeds"() {

        given:
        settingsFile << "rootProject.name = '${PROJECT_NAME}'"
        buildFile << """
            plugins {
                id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
            }
        """
        when:
        def result = runner()
                //.withArguments('geoclientRuntimeReport', 'geosupportRuntimeReport')
                .withArguments('geoclientRuntimeReport')
                .build()

        then:
        println(result.output);
        result.output.contains("GeosupportInfo_Decorated report written to '${geoclientReport}'")
        result.task(':geosupportInfo').outcome == SUCCESS
    }
    /*
     def "plugin is configured from extension properties"() {
     given:
     settingsFile << "rootProject.name = '${PROJECT_NAME}'"
     buildFile << """
     plugins {
     id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
     }
     geoclient {
     nativeTempDir = new File(System.getProperty('java.io.tmpdir'))
     }
     geosupport {
     geofiles = System.getProperty('geosupport.geofiles')
     home = '/foo'
     libraryPath = System.getProperty('geosupport.libraryPath')
     }
     """
     and:
     def expectedNativeTempDir = new File(System.getProperty("java.io.tmpdir"))
     when:
     def result = runner()
     .withArguments('geosupportInfo',
     '-Dgeosupport.geofiles=/foo/fls/',
     '-Dgeosupport.libraryPath=/foo/lib')
     .build()
     then:
     result.output.contains("geoclient.nativeTempDir='${expectedNativeTempDir}'")
     result.output.contains("geosupport.geofiles='/foo/fls/'")
     result.output.contains("geosupport.home='/foo'")
     result.output.contains("geosupport.libraryPath='/foo/lib'")
     result.output.contains("GeosupportInfo_Decorated report written to '${geoclientReport}'")
     result.task(':geosupportInfo').outcome == SUCCESS
     }
     */
    def runner() {
        return GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
    }
}
