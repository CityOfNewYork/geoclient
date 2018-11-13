/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gov.nyc.doitt.gis.geoclient.jni.util;

import java.util.Objects;

/**
 * @author mlipper
 */
public class Platform {

	public static final String LINUX_SHARED_LIB_PREFIX = "lib";

	public static final String LINUX_SHARED_LIB_FILE_EXTENSION = "so";

	public static final String WINDOWS_SHARED_LIB_PREFIX = "";

	public static final String WINDOWS_SHARED_LIB_FILE_EXTENSION = "dll";

	private String name;

	private String operatingSystem;

	private String architecture;

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
				os = "linux";
			}
			else if (isWindows()) {
				os = "windows";
			}

			String arch = null;
			if (is64Bit()) {
				arch = "x64";
			}

			if (os == null || arch == null) {
				throw new UnsupportedPlatformException(getOperatingSystem(),
						getArchitecture());
			}
			this.name = String.format("%s-%s", os, arch);
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
		return this.operatingSystem.toLowerCase().contains("windows");
	}

	public boolean isLinux() {
		return this.operatingSystem.toLowerCase().contains("linux");
	}

	public boolean is64Bit() {
		return this.architecture.indexOf("64") > 0;
	}

	public String getSharedLibraryPrefix() {
		if (isWindows()) {
			return WINDOWS_SHARED_LIB_PREFIX;
		}
		return LINUX_SHARED_LIB_PREFIX;
	}

	public String getSharedLibraryFileExtension() {
		if (isWindows()) {
			return WINDOWS_SHARED_LIB_FILE_EXTENSION;
		}
		return LINUX_SHARED_LIB_FILE_EXTENSION;
	}

	public String getSharedLibraryName(String baseName) {
		return String.format("%s%s.%s", getSharedLibraryPrefix(), baseName,
				getSharedLibraryFileExtension());
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
