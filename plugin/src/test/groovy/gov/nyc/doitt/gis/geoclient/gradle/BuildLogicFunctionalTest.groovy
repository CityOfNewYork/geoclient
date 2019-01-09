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

import static org.gradle.testkit.runner.TaskOutcome.*

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Specification

class BuildLogicFunctionalTest extends Specification {

    //static final String GEOSUPPORT_HOME='/opt/geosupport'
    //static final String GEOFILES="${GEOSUPPORT_HOME}/fls/"
    //static final String GS_LIBRARY_PATH="${GEOSUPPORT_HOME}/lib"

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile
    File settingsFile
    String sysPropGcNativeTempDir = BuildConfigurationResolver.SYSPROP_GC_NATIVE_TEMP_DIR
    String geosupportHome = BuildConfigurationResolver.ENV_VAR_GEOSUPPORT_HOME
    String geosupportLibraryPath = BuildConfigurationResolver.ENV_VAR_GS_LIBRARY_PATH
    String geofiles = BuildConfigurationResolver.ENV_VAR_GEOFILES

    def setup() {
        //geosupportHome = testProjectDir.newFolder('geosupport')
        //gsLibDir = new File(geosupportHome, 'lib')
        //geofilesDir = new File(geosupportHome, 'fls')
        //testProjectDir.newFile('gradle.properties') << """
        //systemProp.java.library.path=${gsLibDir.toURI()}
        //"""
        settingsFile = testProjectDir.newFile('settings.gradle')
        buildFile = testProjectDir.newFile('build.gradle')

    }

    def "applying plugin without configuration succeeds"() {
        given:
        settingsFile << "rootProject.name = 'plugin-funtest'"
        buildFile << """
            plugins {
                id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
            }
        """

        when:
        def result = runner()
                .withArguments('geosupportInfo')
                .build()

        then:
        println result.output
        result.output.contains('geoclient.nativeTempDir=')
        result.output.contains('geosupport.geofiles=')
        result.output.contains('geosupport.home=')
        result.output.contains('geosupport.libraryPath=')
        result.task(':geosupportInfo').outcome == SUCCESS
    }

    def runner() {
        return GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
    }
}
