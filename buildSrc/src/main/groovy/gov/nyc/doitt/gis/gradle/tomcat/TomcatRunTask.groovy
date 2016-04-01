package gov.nyc.doitt.gis.gradle.tomcat

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.TaskAction

class TomcatRunTask extends DefaultTask {

  def contextPath() {
    def contextPath = project.tomcat.contextPath ?: project.war.baseName
    if (contextPath.charAt(0) != '/') {
      contextPath = '/' + contextPath
    }
    contextPath
  }

  def makeClasspath() {
    def buildSrcJar = project.files( new File(project.rootProject.projectDir, 'buildSrc/build/libs/buildSrc.jar') )

    buildSrcJar +
    project.buildscript.configurations.classpath +
    project.files(project.buildscript.dependencies.localGroovy().resolve()) +
    project.configurations.getByName('tomcat')
  }

  @TaskAction
  def run() {

    def work = new File(project.buildDir, 'tomcat')
    work.mkdirs()

    project.javaexec {
      main = 'gov.nyc.doitt.gis.gradle.tomcat.MainExec'
      classpath = makeClasspath()

      args = [ project.war.archivePath, contextPath(), project.tomcat.port ]

      systemProperties = project.tomcat.systemProperties
      workingDir = work

      maxHeapSize = '1024m'
      minHeapSize = '512m'
      jvmArgs '-XX:MaxPermSize=256m'

    }
  }
}