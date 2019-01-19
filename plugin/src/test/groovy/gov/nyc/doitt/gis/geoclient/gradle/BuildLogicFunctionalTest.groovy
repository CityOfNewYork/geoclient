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

import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_REPORT_FILE_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_REPORT_TASK_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOSUPPORT_REPORT_FILE_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOSUPPORT_REPORT_TASK_NAME
import static org.gradle.testkit.runner.TaskOutcome.*

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Specification
import spock.lang.Unroll

class BuildLogicFunctionalTest extends Specification {

    final static String PROJECT_NAME = 'plugin-funtest'

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()

    File buildFile
    File settingsFile
    File testBuildDir

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        settingsFile = testProjectDir.newFile('settings.gradle')
        testBuildDir = new File(testProjectDir.root, "build")
    }

    @Unroll
    def "#taskName succeeds using plugin default settings"() {

        given:
        settingsFile << "rootProject.name = '${PROJECT_NAME}'"
        buildFile << """
            plugins {
                id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
            }
        """

        when:
        def reportFilePath = new File(testBuildDir, reportFileName).absolutePath
        def result = runner()
                .withArguments(taskName)
                .build()

        then:
        //println(result.output);
        result.output.contains("Runtime property report written to '${reportFilePath}'")
        result.task(':' + taskName).outcome == SUCCESS

        where:
        taskName << [GEOCLIENT_REPORT_TASK_NAME, GEOSUPPORT_REPORT_TASK_NAME]
        reportFileName << [GEOCLIENT_REPORT_FILE_NAME, GEOSUPPORT_REPORT_FILE_NAME]
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
