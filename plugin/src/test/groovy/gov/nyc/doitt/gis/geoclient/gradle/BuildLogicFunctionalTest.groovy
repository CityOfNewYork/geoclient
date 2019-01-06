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

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.*

class BuildLogicFunctionalTest extends Specification {

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile
    //File geosupportHome
    //File gsLibDir
    //File geofilesDir

    def setup() {
        //geosupportHome = testProjectDir.newFolder('geosupport')
        //gsLibDir = new File(geosupportHome, 'lib')
        //geofilesDir = new File(geosupportHome, 'fls')
        //testProjectDir.newFile('gradle.properties') << """
        //systemProp.java.library.path=${gsLibDir.toURI()}
        //"""
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "applying plugin without configuration succeeds"() {
        given:
        buildFile << """
            plugins {
                id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
            }
        """

        when:
        def result = runner()
            .withArguments('tasks')
            .build()

        then:
        result.task(':tasks').outcome == SUCCESS
    }

    def runner() {
        return GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withPluginClasspath()
    }
}
