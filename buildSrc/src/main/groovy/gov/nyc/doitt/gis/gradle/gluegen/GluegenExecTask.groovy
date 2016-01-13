package gov.nyc.doitt.gis.gradle.gluegen

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.OutputDirectory

class GluegenExecTask extends JavaExec {

  String classpathConfigurationName
  // Include path
  @InputFiles FileCollection includePath
  // Header file which Gluegen should generate JNI glue code
  @InputFile File nativeHeader
  // Location of Gluegen *.cfg configuration file
  File configurationFile
  // Substitution value for the '${javaOutputDir}' token, if present in the configurationFile
  @OutputDirectory File javaOutputDir
  // Substitution value for the '${nativeOutputDir}' token, if present in the configurationFile
  @OutputDirectory File nativeOutputDir
  // Needed for use of 'generatedBy' in the C component model
  @OutputDirectory File headerDir
  // ignored since Gluegen does not produce header file
  // Note that this refers to the generated source and not the 'header'
  // property of the GluegenSpec
  @OutputDirectory File sourceDir

  /**
   * {@inheritDoc}
   */
  @Override
  String getMain() {
      return "com.jogamp.gluegen.GlueGen"
  }

  /**
   * {@inheritDoc}
   */
  @Override
  List<String> getArgs() {
    List<String> args = super.getArgs() ?: new ArrayList<String>()
    // IMPORTANT: Gluegen's PCPP does not evaluate macros and geoclient.h
    // uses macros to include either NYCgeo.h or geo.h. If this task complains
    // complains about missing header files, make sure all buildable platform
    // include directories contain both
    args << "-I${includePath.asPath}"
    args << "-Ecom.jogamp.gluegen.JavaEmitter"
    args << "-C${configurationFile.canonicalPath}"
    args << "${nativeHeader.canonicalPath}"
  }

  /**
   * {@inheritDoc}
   */
  @Override
  String getDescription() {
    return "Generates *.java, *.c JNI files using Gluegen"
  }

  /**
   * {@inheritDoc}
   */
  @Override
  String getGroup() {
    return "Build"
  }
}
