package gov.nyc.doitt.gis.gradle.gluegen

import org.gradle.model.Managed
import org.gradle.language.c.CSourceSet
import org.gradle.language.java.JavaSourceSet

@Managed interface GluegenArgs {
  File getConfigurationFile(); void setConfigurationFile(File configurationFile)
  File getJavaOutputDir(); void setJavaOutputDir(File javaOutputDir)
  File getNativeHeader(); void setNativeHeader(File nativeHeader)
  File getNativeOutputDir(); void setNativeOutputDir(File nativeOutputDir);
  String getIncludePath(); void setIncludePath(String includePath) 
  String getJavaExe(); void setJavaExe(String javaExe) 
}

@Managed interface GeneratedJavaSource extends JavaSourceSet {}
@Managed interface GeneratedCSource extends CSourceSet {}


