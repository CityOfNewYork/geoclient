package gov.nyc.doitt.gis.geoclient.gradle

import org.gradle.api.Action
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test

// Based on com.bmuschko.gradle.docker.IntegrationTestPlugin
// See https://github.com/bmuschko/gradle-docker-plugin/blob/master/buildSrc/src/main/kotlin/com/bmuschko/gradle/docker/IntegrationTestPlugin.kt
class IntegrationTestPlugin<Project> implements Plugin<Project> {

    static final String TEST_NAME = "integrationTest"
    static final String SOURCE_SET_NAME = "integrationTest"
    static final String JAVA_SOURCE_SET_DIR = String.format("src/%s/java", SOURCE_SET_NAME)
    static final String JAVA_RESOURCES_SOURCE_SET_DIR = String.format("src/%s/resources", SOURCE_SET_NAME)

	private final Logger logger = Logging.getLogger(getClass())

    void apply(Project project) {
        GeoclientExtension geoclient = project.extensions.create("geoclient", GeoclientExtension, project)
        GeosupportExtension geosupport = project.extensions.create("geosupport", GeosupportExtension, project)
        project.getPlugins().withType(JavaPlugin) { javaPlugin ->
            def sourceSets = project.getExtensions().getByType(SourceSetContainer)
            def testRuntimeClasspath = project.getConfigurations().testRuntimeClasspath
            def compileClasspath = project.getConfigurations().compileClasspath
            def integrationTestSourceSet = sourceSets.create(SOURCE_SET_NAME, new Action<SourceSet>(){
                void execute(SourceSet sourceSet) {
                    sourceSet.java.srcDir(JAVA_SOURCE_SET_DIR)
                    sourceSet.resources.srcDir(JAVA_RESOURCES_SOURCE_SET_DIR)
                    sourceSet.compileClasspath += sourceSets.main.output + testRuntimeClasspath
                    sourceSet.runtimeClasspath += sourceSet.output + sourceSet.compileClasspath
                }
            });
            def integrationTest = project.tasks.create(TEST_NAME, Test, new Action<Test>() {
                void execute(Test test) {
                   test.description = 'Runs tests which call Geosupport native code using JNI.'
                   test.group = 'verification'
                   test.testClassesDirs = integrationTestSourceSet.output.classesDirs
                   test.classpath = integrationTestSourceSet.runtimeClasspath
                   test.mustRunAfter('test')
                   geoclient.getSystemProperties().each { k,v ->
                     test.systemProperty(k,v)
                   }
                   geosupport.getEnvironment().each { k,v ->
                     test.environment(k,v)
                   }
                }
            });
            logger.quiet(":{}:{} task configured", project.name, integrationTest.name)
            logger.info("geoclient.systemProperties={}", geoclient.systemProperties)
            logger.info("geosupport.environment={}", geosupport.environment)

            project.tasks.check.dependsOn(integrationTest)
        }
    }
}
