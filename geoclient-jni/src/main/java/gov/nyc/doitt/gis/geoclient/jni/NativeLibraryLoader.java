package gov.nyc.doitt.gis.geoclient.jni;

//
// This class is copied from Facebook's RocksDB v5.9.2
// See https://github.com/facebook/rocksdb
//

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import gov.nyc.doitt.gis.geoclient.jni.util.Environment;

/**
 * This class is used to load the Geoclient shared library from within the jar.
 * The shared library is extracted to a temp folder and loaded from there.
 */
public class NativeLibraryLoader {
  //singleton
  private static final NativeLibraryLoader instance = new NativeLibraryLoader();
  private static boolean initialized = false;

//  private static final String sharedLibraryName = Environment.getSharedLibraryName("geoclient");
//  private static final String jniLibraryName = Environment.getJniLibraryName("geoclient");
//  private static final String jniLibraryFileName = Environment.getJniLibraryFileName("geoclient");
//  private static final String tempFilePrefix = "libgeoclientjni";
//  private static final String tempFileSuffix = Environment.getJniLibraryExtension();

  /**
   * Get a reference to the NativeLibraryLoader
   *
   * @return The NativeLibraryLoader
   */
  public static NativeLibraryLoader getInstance() {
    return instance;
  }

  /**
   * Firstly attempts to load the library from <i>java.library.path</i>,
   * if that fails then it falls back to extracting
   * the library from the classpath
   * {@link gov.nyc.doitt.gis.geoclient.jni.NativeLibraryLoader#loadLibraryFromJar(java.lang.String)}
   *
   * @param tmpDir A temporary directory to use
   *   to copy the native library to when loading from the classpath.
   *   If null, or the empty string, we rely on Java's
   *   {@link java.io.File#createTempFile(String, String)}
   *   function to provide a temporary location.
   *   The temporary file will be registered for deletion
   *   on exit.
   *
   * @throws java.io.IOException if a filesystem operation fails.
   */
  public synchronized void loadLibrary(final String tmpDir) throws IOException {
//    try {
//    	
//    	System.out.println(String.format("System.loadLibrary(%s);", sharedLibraryName));
//        System.loadLibrary(sharedLibraryName);
//    } catch(final UnsatisfiedLinkError ule1) {
//      try {
//      	System.out.println(String.format("System.loadLibrary(%s);", jniLibraryName));
//        System.loadLibrary(jniLibraryName);
//      } catch(final UnsatisfiedLinkError ule2) {
//       	System.out.println(String.format("loadLibraryFromJar(%s);", tmpDir));
//        loadLibraryFromJar(tmpDir);
//      }
//    }
  }

  /**
   * Attempts to extract the native Geoclient library
   * from the classpath and load it
   *
   * @param tmpDir A temporary directory to use
   *   to copy the native library to. If null,
   *   or the empty string, we rely on Java's
   *   {@link java.io.File#createTempFile(String, String)}
   *   function to provide a temporary location.
   *   The temporary file will be registered for deletion
   *   on exit.
   *
   * @throws java.io.IOException if a filesystem operation fails.
   */
  void loadLibraryFromJar(final String tmpDir)
      throws IOException {
    if (!initialized) {
      System.out.println(String.format("File file = loadLibraryFromJarToTemp(%s).getAbsolutePath();", tmpDir));
      File file = loadLibraryFromJarToTemp(tmpDir);
      System.out.println(String.format("System.load(%s);", file.getAbsolutePath()));
      System.load(file.getAbsolutePath());
      //System.load(loadLibraryFromJarToTemp(tmpDir).getAbsolutePath());
      initialized = true;
    }
  }

  File loadLibraryFromJarToTemp(final String tmpDir)
          throws IOException {
//    final File temp;
//    if (tmpDir == null || tmpDir.isEmpty()) {
//      temp = File.createTempFile(tempFilePrefix, tempFileSuffix);
//    } else {
//      temp = new File(tmpDir, jniLibraryFileName);
//      if (temp.exists() && !temp.delete()) {
//        throw new RuntimeException("File: " + temp.getAbsolutePath()
//            + " already exists and cannot be removed.");
//      }
//      if (!temp.createNewFile()) {
//        throw new RuntimeException("File: " + temp.getAbsolutePath()
//            + " could not be created.");
//      }
//    }
//
//    if (!temp.exists()) {
//      throw new RuntimeException("File " + temp.getAbsolutePath() + " does not exist.");
//    } else {
//      temp.deleteOnExit();
//    }
//
//    // attempt to copy the library from the Jar file to the temp destination
//    try (final InputStream is = getClass().getClassLoader().
//      getResourceAsStream(jniLibraryFileName)) {
//      if (is == null) {
//        throw new RuntimeException(jniLibraryFileName + " was not found inside JAR.");
//      } else {
//        Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
//      }
//    }
//
//    return temp;
      return null;
  }

  /**
   * Private constructor to disallow instantiation
   */
  private NativeLibraryLoader() {
  }
}
