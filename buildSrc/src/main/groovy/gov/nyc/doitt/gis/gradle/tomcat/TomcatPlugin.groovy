package gov.nyc.doitt.gis.gradle.tomcat

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.TaskAction

/**
 * Copied from <a href="https://gist.github.com/matthauck/6338633">this Gist</a>.
 */

class TomcatExtension {
  Map systemProperties = [:]
  String contextPath = null
  int port = 8080
  int stopPort = 8081
  String stopKey = 'stopKey'
}

class TomcatPlugin implements Plugin<Project> {

  private static final TOMCAT_VERSION = '7.0.68'

  void apply(Project project) {
    project.extensions.create('tomcat', TomcatExtension)
    project.plugins.apply(WarPlugin)
    project.configurations.create('tomcat').setVisible(false).setTransitive(true)
    project.dependencies {
      tomcat(
        "org.apache.tomcat.embed:tomcat-embed-core:${TOMCAT_VERSION}",
        "org.apache.tomcat.embed:tomcat-embed-logging-juli:${TOMCAT_VERSION}",
        "org.apache.tomcat.embed:tomcat-embed-jasper:${TOMCAT_VERSION}"
      )
    }

    project.task('tomcatRunWar',
      type: TomcatRunTask,
      dependsOn: project.war,
      group: 'Tomcat',
      description: "Launch embedded tomcat on project's war file"
    )

    project.task('tomcatStop',
      type: TomcatStopTask,
      group: 'Tomcat',
      description: 'Stop embedded tomcat'
    )
    project.tasks.tomcatStop.stopPort = project.tomcat.stopPort
    project.tasks.tomcatStop.stopKey = project.tomcat.stopKey
  }
}