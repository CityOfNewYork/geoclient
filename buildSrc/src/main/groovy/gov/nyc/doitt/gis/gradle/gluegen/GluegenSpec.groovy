package gov.nyc.doitt.gis.gradle.gluegen

import org.gradle.model.Managed

@Managed interface GluegenSpec {
  File getConfigurationFile(); void setConfigurationFile(File configurationFile)
  File getJavaOutputDir(); void setJavaOutputDir(File javaOutputDir)
  File getNativeHeader(); void setNativeHeader(File nativeHeader)
  File getNativeOutputDir(); void setNativeOutputDir(File nativeOutputDir);
  String getIncludePath(); void setIncludePath(String includePath) 
  String getJavaExe(); void setJavaExe(String javaExe) 
}

