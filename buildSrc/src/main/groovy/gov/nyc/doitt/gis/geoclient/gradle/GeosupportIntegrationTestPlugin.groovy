package gov.nyc.doitt.gis.geoclient.gradle.GeosupportIntegrationTestPlugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.GroovySourceSet
import org.gradle.api.tasks.testing.Test

class GeosupportIntegrationTestPlugin<Project> implements Plugin<Project> {
    void apply(Project project) {
        project.task('geosupportIntegrationTest') {
            doLast {
                println 'Configuring Geosupport integration tests'
            }
        }
    }
}