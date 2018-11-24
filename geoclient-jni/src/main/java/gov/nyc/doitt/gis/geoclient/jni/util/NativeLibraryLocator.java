/**
 *
 * Based on the class net.rubygrapefruit.platform.internal.NativeLibraryLocator from the native-platform project:
 *
 * 		https://github.com/adammurdoch/native-platform.git
 *
 * Original copyright:
 *
 * Copyright 2012 Adam Murdoch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.jni.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NativeLibraryLocator {

	final Logger logger = LoggerFactory.getLogger(NativeLibraryLocator.class);

	private final String extractDir;

	public NativeLibraryLocator(String extractDir) {
		this.extractDir = extractDir;
	}

	public File find(JniLibrary jniLibrary) throws IOException {
		String resourceName = "gov/nyc/doitt/gis/geoclient/jni/" + jniLibrary.getResourceName();
		logger.debug("jniLibrary.resourceName={}", resourceName);
		if (this.extractDir != null) {
			File libFile = new File(this.extractDir,
					String.format("%s/%s", jniLibrary.getVersion(), resourceName));
			logger.debug("libFile={}", libFile);
			File lockFile = new File(libFile.getParentFile(),
					libFile.getName() + ".lock");
			lockFile.getParentFile().mkdirs();
			lockFile.createNewFile();
			RandomAccessFile lockFileAccess = new RandomAccessFile(lockFile, "rw");
			try {
				// Take exclusive lock on lock file
				FileLock lock = lockFileAccess.getChannel().lock();
				assert lock.isValid();
				if (lockFile.length() > 0 && lockFileAccess.readBoolean()) {
					// Library has been extracted
					return libFile;
				}
				URL resource = getClass().getClassLoader().getResource(resourceName);
				logger.debug("URL resource={}", resource);
				if (resource != null) {
					// Extract library and write marker to lock file
					libFile.getParentFile().mkdirs();
					copy(resource, libFile);
					lockFileAccess.seek(0);
					lockFileAccess.writeBoolean(true);
					return libFile;
				}
			}
			finally {
				// Also releases lock
				lockFileAccess.close();
			}
		}
		else {
			// Default to using JVM temp-file directory whose value is derived at runtime
			// from Java system property 'java.io.tmpdir'
			URL resource = getClass().getClassLoader().getResource(resourceName);
			if (resource != null) {
				File libFile;
				File libDir = File.createTempFile(jniLibrary.getName(), "dir");
				libDir.delete();
				libDir.mkdirs();
				libFile = new File(libDir, jniLibrary.getName());
				libFile.deleteOnExit();
				copy(resource, libFile);
				return libFile;
			}
		}
		// Panic and resort to depraved hackery
		return loadFromBuildDirectory(jniLibrary);
	}

	private File loadFromBuildDirectory(JniLibrary jniLibrary) {
		File libFile = new File(String.format("build/libs/%s/shared/%s",
				jniLibrary.getName(), jniLibrary.getPlatform().getName()));
		if (libFile.isFile()) {
			return libFile;
		}

		return null;
	}

	private static void copy(URL source, File dest) {
		try {
			InputStream inputStream = source.openStream();
			try {
				OutputStream outputStream = new FileOutputStream(dest);
				try {
					byte[] buffer = new byte[4096];
					while (true) {
						int nread = inputStream.read(buffer);
						if (nread < 0) {
							break;
						}
						outputStream.write(buffer, 0, nread);
					}
				}
				finally {
					outputStream.close();
				}
			}
			finally {
				inputStream.close();
			}
		}
		catch (IOException e) {
			throw new JniLibraryException("Could not extract native JNI library.", e);
		}
	}

}
