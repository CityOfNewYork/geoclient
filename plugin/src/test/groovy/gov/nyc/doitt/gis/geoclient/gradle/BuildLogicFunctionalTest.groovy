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

/*
 * IMPORTANT: The name of this class and it's methods must match the 'test { filters {...} }' block in build.gradle
 *            to insure that the forked JVM used to run this suite has it's environment variables and Java system 
 *            properties set as expected!
 */
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
        //println System.getenv().sort().each { k, v -> println "$k=$v"; }
        settingsFile = testProjectDir.newFile('settings.gradle')
        buildFile = testProjectDir.newFile('build.gradle')

    }

    def "apply plugin without any configuration succeeds"() {

        given:
        System.getProperty(sysPropGcNativeTempDir) == false
        System.getenv().containsKey(geosupportHome) == false
        System.getenv().containsKey(geosupportLibraryPath) == false
        System.getenv().containsKey(geofiles) == false

        and:
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
        //println result.output
        result.output.contains("geoclient.nativeTempDir=''")
        result.output.contains("geosupport.geofiles=''")
        result.output.contains("geosupport.home=''")
        result.output.contains("geosupport.libraryPath=''")
        result.output.contains("GeosupportInfo report written to '${testProjectDir.root}/build'")
        result.task(':geosupportInfo').outcome == SUCCESS
    }

    def "applied plugin is completely configured from system and environment"() {

        given:
        settingsFile << "rootProject.name = 'plugin-funtest'"
        buildFile << """
            plugins {
                id 'groovy'
                id 'gov.nyc.doitt.gis.geoclient.gradle.geoclient-plugin'
            }
            test {
                systemProperty 'gc.jni.tempdir',  '/tmp'
                   environment 'GEOSUPPORT_HOME', '/opt/geosupport'
                   environment 'GS_LIBRARY_PATH', '/opt/geosupport/lib'
                   environment 'GEOFILES',        '/opt/geosupport/fls/'
            }
        """
        when:
        def result = runner()
                .withArguments('geosupportInfo')
                .build()

        then:
        result.output.contains("geoclient.nativeTempDir='/tmp'")
        result.output.contains("geosupport.geofiles='/opt/geosupportfls/'")
        result.output.contains("geosupport.home='/opt/geosupport'")
        result.output.contains("geosupport.libraryPath='/opt/geosupport/lib'")
        result.output.contains("GeosupportInfo report written to '${testProjectDir.root}/build'")
        result.task(':geosupportInfo').outcome == SUCCESS
    }

    def runner() {
        return GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
    }
}
