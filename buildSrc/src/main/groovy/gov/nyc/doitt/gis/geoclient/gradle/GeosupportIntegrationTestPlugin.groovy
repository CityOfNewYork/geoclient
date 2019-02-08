package gov.nyc.doitt.gis.geoclient.gradle.GeosupportIntegrationTestPlugin

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.testing.Test

// Based on com.bmuschko.gradle.docker.IntegrationTestPlugin
// See https://github.com/bmuschko/gradle-docker-plugin/blob/master/buildSrc/src/main/kotlin/com/bmuschko/gradle/docker/IntegrationTestPlugin.kt
class GeosupportIntegrationTestPlugin<Project> implements Plugin<Project> {
    void apply(Project project) {
        def sourceSets = project.convention.getPlugin(JavaPluginConvention).getSourceSets()
        def testRuntimeClasspath = project.configurations.testRuntimeClasspath
        // Gradle reference from src/plugins/org/gradle/api/tasks/plugins/JavaBasePlugin.java 
        def integrationTestSourceSet = sourceSets.create("integrationTest", new Action<SourceSet>(){            
            void execute(SourceSet sourceSet) {
                with(sourceSet) {
                    java.srcDir('src/integrationTest/java')
                    resources.srcDir('src/integrationTest/resources')
                    compileClasspath += main.output + testRuntimeClasspath
                    runtimeClasspath += output + compileClasspath
                }
            }
        });
        def integrationTest = project.tasks.create('integrationTest', Test, new Action<Test>() {
            void execute(Test test) {
                with(test) {
                    description = 'Runs tests which call Geosupport native code using JNI.'
                    group = 'verification'
                    testClassesDir = integrationTestSourceSet.output.classesDirs
                    classpath = integrationTestSourceSet.runtimeClasspath
                    mustRunAfter('test')
                }
            }
        });

        project.tasks.check.dependsOn(integrationTest)
    }
}