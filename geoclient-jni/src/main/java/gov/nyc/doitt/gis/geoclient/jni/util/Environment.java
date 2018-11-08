package gov.nyc.doitt.gis.geoclient.jni.util;

//
// This class was originally copied from Facebook's RocksDB v5.9.2
// See https://github.com/facebook/rocksdb
//

public class Environment {

    public enum Platforms { linux_x64, windows_x64 };
    
  private static String JNI_LIBRARY_BASENAME = "geoclientjni";
  private static String OS = System.getProperty("os.name").toLowerCase();
  private static String ARCH = System.getProperty("os.arch").toLowerCase();

  public static boolean isWindows() {
      return (OS.contains("windows"));
  }
  
  public static boolean isLinux() {
      return OS.contains("linux");
  }

  public static boolean is64Bit() {
      return (ARCH.indexOf("64") > 0);
  }

  public static String getPlatformName() {
      if(isLinux()) {
          return "linux";
      }
      if(isWindows()) {
          return "windows";
      }
      
      throw new UnsupportedOperationException(getUnsupportedPlatformExceptionMessage());
  }
  
  public static String getArchitecture() {
      return "x64";
  }  
  
  public static String getJniLibraryName() {
      return JNI_LIBRARY_BASENAME;
  }

  public static String getJniLibraryFileName() {

      if (isLinux()) {
          return String.format("lib%s.so", getJniLibraryName());      
	  }
	  
	  if (isWindows()) {
          return String.format("%s.dll", getJniLibraryName());      
      }
      
	  throw new UnsupportedOperationException(getUnsupportedPlatformExceptionMessage());
  }
  
  public static String getJniLibrarySubdirectoryName() {

      if (isLinux()) {
          return "linux-64";      
      }
      
      if (isWindows()) {
          return "windows-64";      
      }
      
      throw new UnsupportedOperationException(getUnsupportedPlatformExceptionMessage());
  }
  
  private static String getUnsupportedPlatformExceptionMessage() {
      return String.format("Unsupported JNI platform: OS='%s' ARCH='%s'", OS, ARCH);
  }
}
