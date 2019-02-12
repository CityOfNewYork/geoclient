package gov.nyc.doitt.gis.geoclient.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.testing.Test

// Based on com.bmuschko.gradle.docker.IntegrationTestPlugin
// See https://github.com/bmuschko/gradle-docker-plugin/blob/master/buildSrc/src/main/kotlin/com/bmuschko/gradle/docker/IntegrationTestPlugin.kt
class GeosupportIntegrationTestPlugin<Project> implements Plugin<Project> {

    static final String TEST_NAME = "integrationTest"
    static final String SOURCE_SET_NAME = "integrationTest"
    static final String JAVA_SOURCE_SET_DIR = String.format("src/%s/java", SOURCE_SET_NAME)
    static final String JAVA_RESOURCES_SOURCE_SET_DIR = String.format("src/%s/resources", SOURCE_SET_NAME)

    void apply(Project project) {
        final GeoclientExtension geoclient = project.extensions.create("geoclient", GeoclientExtension, project)
        final GeosupportExtension geosupport = project.extensions.create("geosupport", GeosupportExtension, project)
        println project.name + ".hasPlugin(JavaPlugin) == " + project.getPlugins().hasPlugin(JavaPlugin)
        if(project.getPlugins().hasPlugin(JavaPlugin)) {
            def sourceSets = project.convention.getPlugin(JavaPluginConvention).getSourceSets()
            def testRuntimeClasspath = project.configurations.testRuntimeClasspath
            def compileClasspath = project.configurations.compileClasspath
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
            project.logger.lifecycle("Configured {} task with the following:", TEST_NAME)
            project.logger.lifecycle("geoclient.systemProperties={}", geoclient.systemProperties)
            project.logger.lifecycle("geosupport.environment={}", geosupport.environment)

            project.tasks.check.dependsOn(integrationTest)
        }
    }
}
