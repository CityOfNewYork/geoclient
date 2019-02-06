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

import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientExtension.GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_CONTAINER_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_REPORT_FILE_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOCLIENT_REPORT_TASK_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOSUPPORT_CONTAINER_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOSUPPORT_REPORT_FILE_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.GEOSUPPORT_REPORT_TASK_NAME
import static gov.nyc.doitt.gis.geoclient.gradle.GeosupportExtension.GEOSUPPORT_DEFAULT_GEOFILES
import static gov.nyc.doitt.gis.geoclient.gradle.GeosupportExtension.GEOSUPPORT_DEFAULT_HOME
import static gov.nyc.doitt.gis.geoclient.gradle.GeosupportExtension.GEOSUPPORT_DEFAULT_LIBRARY_PATH
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
        def reportFile = new File(testBuildDir, reportFileName)
        def result = runner()
                .withArguments(taskName)
                .build()

        then:
        //println(result.output)
        result.output.contains(String.format(RuntimePropertyReport.OUT_REPORT_TITLE_FORMAT, containerName));
        result.output.contains(String.format(RuntimePropertyReport.OUT_REPORT_FILE_FORMAT, reportFile.canonicalPath));
        result.task(':' + taskName).outcome == SUCCESS
        strings.each { substring ->
            result.output.contains(substring)
        }

        where:
        // @formatter:off
        taskName                    | reportFileName              | containerName             | strings
        GEOCLIENT_REPORT_TASK_NAME  | GEOCLIENT_REPORT_FILE_NAME  | GEOCLIENT_CONTAINER_NAME  | [
            GEOCLIENT_DEFAULT_SUBDIR_NATIVE_TEMP_DIR
        ]
        GEOSUPPORT_REPORT_TASK_NAME | GEOSUPPORT_REPORT_FILE_NAME | GEOSUPPORT_CONTAINER_NAME | [
            GEOSUPPORT_DEFAULT_GEOFILES,
            GEOSUPPORT_DEFAULT_HOME,
            GEOSUPPORT_DEFAULT_LIBRARY_PATH
        ]
        // @formatter:on
    }

    @Unroll
    def "#taskName uses dsl config to override default settings"() {
        given:
        settingsFile << "rootProject.name = '${PROJECT_NAME}'"
        buildFile << """
            plugins {
                id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
            }
        """

        when:
        def result = runner()
                .withArguments(taskName)
                .build()
        then:
        println(result.output)
        result.task(':' + taskName).outcome == SUCCESS
        strings.each { substring ->
            result.output.contains(substring)
        }

        where:
        taskName                    | reportFileName              | containerName             | strings
        GEOCLIENT_REPORT_TASK_NAME  | GEOCLIENT_REPORT_FILE_NAME  | GEOCLIENT_CONTAINER_NAME  | ["/foo"]}

    def runner() {
        return GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
    }
}
