/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.jni.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author mlipper
 */
//@Brittle // TODO Create this Annotation
public class Platform {

    public static final String ARCH_X64 = "x64";

    public static final String LINUX_OS_FAMILY = "linux";

    public static final String LINUX_SHARED_LIB_PREFIX = "lib";

    public static final String LINUX_SHARED_LIB_PATH_VAR = "LD_LIBRARY_PATH";

    public static final String LINUX_SHARED_LIB_FILE_EXTENSION = "so";

    public static final String WINDOWS_OS_FAMILY = "windows";

    public static final String WINDOWS_SHARED_LIB_PREFIX = "";

    public static final String WINDOWS_SHARED_LIB_PATH_VAR = "PATH";

    public static final String WINDOWS_SHARED_LIB_FILE_EXTENSION = "dll";

    public static final BiFunction<String, String, String> PLATFORM_NAMER = (String s1, String s2) -> { return String.format("%s_%s", s1, s2); };

    public static final Platform SUPPORTED_LINUX_PLATFORM = new Platform(LINUX_OS_FAMILY, ARCH_X64);

    public static final Platform SUPPORTED_WINDOWS_PLATFORM = new Platform(WINDOWS_OS_FAMILY, ARCH_X64);

    public static final List<Platform> SUPPORTED_PLATFORMS = Arrays.asList(SUPPORTED_LINUX_PLATFORM, SUPPORTED_WINDOWS_PLATFORM);

    private final String name;

    private final String operatingSystem;

    private final String architecture;

    public Platform(String operatingSystem, String architecture) {
        super();
        if (operatingSystem == null) {
          throw new IllegalArgumentException(
              "Argument 'operatingSystem' cannot be null");
        }
        this.operatingSystem = operatingSystem;
        if (architecture == null) {
          throw new IllegalArgumentException("Argument 'architecture' cannot be null");
        }
        this.architecture = architecture;
        this.name = getName();
    }

  public Platform() {
    this(System.getProperty("os.name"), System.getProperty("os.arch"));
  }

  public String getName() {
    if (this.name == null) {
      String os = null;
      if (isLinux()) {
        os = LINUX_OS_FAMILY;
      }
      else if (isWindows()) {
        os = WINDOWS_OS_FAMILY;
      }

      String arch = null;
      if (is64Bit()) {
        arch = ARCH_X64;
      }

      if (os == null || arch == null) {
        throw new UnsupportedPlatformException(getOperatingSystem(),
            getArchitecture());
      }
      return PLATFORM_NAMER.apply(os, arch);
    }

    return this.name;
  }

  public String getOperatingSystem() {
    return this.operatingSystem;
  }

  public String getArchitecture() {
    return this.architecture;
  }

  public boolean isWindows() {
    return this.operatingSystem.toLowerCase().contains(WINDOWS_OS_FAMILY);
  }

  public boolean isLinux() {
    return this.operatingSystem.toLowerCase().contains(LINUX_OS_FAMILY);
  }

  public boolean is64Bit() {
    return this.architecture.indexOf(ARCH_X64.substring(1)) >= 0;
  }

  private String getSharedLibraryPrefix() {
    if (isWindows()) {
      return WINDOWS_SHARED_LIB_PREFIX;
    }
    return LINUX_SHARED_LIB_PREFIX;
  }

  private String getSharedLibraryFileExtension() {
    if (isWindows()) {
      return WINDOWS_SHARED_LIB_FILE_EXTENSION;
    }
    return LINUX_SHARED_LIB_FILE_EXTENSION;
  }

  public String getSharedLibraryFileName(String baseName) {
    return String.format("%s%s.%s", getSharedLibraryPrefix(), baseName,
        getSharedLibraryFileExtension());
  }

  public String getSharedLibraryPathEnvironmentVarName() {
      if (isWindows()) {
          return WINDOWS_SHARED_LIB_PATH_VAR;
      }
      return LINUX_SHARED_LIB_PATH_VAR;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Platform other = (Platform) obj;
    return Objects.equals(this.name, other.name);
  }

  @Override
  public String toString() {
    return "Platform [name=" + this.name + ", operatingSystem=" + this.operatingSystem
        + ", architecture=" + this.architecture + "]";
  }

}
