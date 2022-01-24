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
 *
 * NOTE:
 * Based on the class net.rubygrapefruit.platform.internal.NativeLibraryLocator from the native-platform project:
 *
 *      https://github.com/adammurdoch/native-platform.git
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
/*
 * IMPORTANT:
 *
 *   The entire file is excluded from spotless check. Significant source
 *   changes should be manually checked by temporarily commenting out the
 *   'targetExcludes ...' line in the geoclient-jni/build.gradle file and
 *   runnning the gradle geoclient-jni:spotlessJavaCheck task.
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

import gov.nyc.doitt.gis.geoclient.jni.JniContext;

public class NativeLibraryLocator {

    final static Logger logger = LoggerFactory.getLogger(NativeLibraryLocator.class);

    private final String extractDir;

    public NativeLibraryLocator(String extractDir) {
        this.extractDir = extractDir;
    }

    public NativeLibraryLocator() {
        this(null);
    }

    public File find(JniLibrary jniLibrary) throws IOException {

        if (this.extractDir != null) {
            return extractToDirectory(jniLibrary, this.extractDir);
        }

        File libFile = extractToDefaultTempDirectory(jniLibrary);

        if (libFile != null) {
            return libFile;
        }

        File buildFile = getBuildDirectory(jniLibrary);
        logger.warn(
                "Having panic attack and resorting to depraved hackery by searching build directory for shared library file:");
        logger.warn(buildFile.getCanonicalPath());
        return loadFromDirectory(jniLibrary, buildFile);
    }

    protected File extractToDirectory(JniLibrary jniLibrary, String destinationDir) throws IOException {
        String resourceName = getResourceName(jniLibrary);
        logger.debug("Target shared library file parent directory is {}", destinationDir);
        File libFile = new File(destinationDir, String.format("%s/%s", jniLibrary.getVersion(), resourceName));
        logger.debug("Attempting to extract shared library resource {} to file {}", resourceName, libFile);
        File lockFile = new File(libFile.getParentFile(), libFile.getName() + ".lock");
        lockFile.getParentFile().mkdirs();
        boolean isNew = lockFile.createNewFile();
        if (isNew) {
            logger.debug("Successfully created new lock file {}", lockFile.getCanonicalPath());
        } else {
            logger.debug("Using existing lock file {}", lockFile.getCanonicalPath());
        }
        RandomAccessFile lockFileAccess = new RandomAccessFile(lockFile, "rw");
        logger.debug("Using RandomAccessFile {}", lockFileAccess);
        try {
            // Take exclusive lock on lock file
            FileLock lock = lockFileAccess.getChannel().lock();
            assert lock.isValid();
            if (lockFile.length() > 0 && lockFileAccess.readBoolean()) {
                // Library has already been extracted
                logger.debug("Located existing library File {} using FileLock {}", libFile, lock.toString());
                return libFile;
            }
            URL resource = resolveClassLoaderUrlResource(resourceName);
            if (resource != null) {
                logger.debug("Using resolved ClassLoader URL resource {}", resource.toString());
                // Extract library and write marker to lock file
                libFile.getParentFile().mkdirs();
                copy(resource, libFile);
                lockFileAccess.seek(0);
                lockFileAccess.writeBoolean(true);
                logger.info("Successfully extracted shared library file {} to parent directory {}.",
                        libFile.getCanonicalPath(), libFile.getParentFile());
                return libFile;
            }
        } finally {
            // Also releases lock
            lockFileAccess.close();
            logger.debug("Closed RandomAccessFile {}", lockFileAccess);
        }
        return libFile;
    }

    protected File extractToDefaultTempDirectory(JniLibrary jniLibrary) throws IOException {
        String resourceName = getResourceName(jniLibrary);
        // Default to using runtime JVM temporary file directory
        URL resource = resolveClassLoaderUrlResource(resourceName);
        if (resource != null) {
            File libFile;
            File libDir = File.createTempFile(jniLibrary.getName(), "dir");
            libDir.delete();
            libDir.mkdirs();
            libFile = new File(libDir, jniLibrary.getName());
            libFile.deleteOnExit();
            copy(resource, libFile);
            logger.debug("Successfully resolved {} to URL {} and copied it to temporary file {}.", resourceName,
                    resource.toString(), libFile.getCanonicalPath());
            return libFile;
        }
        logger.debug("Failed to resolve {} to a URL", resourceName);
        return null;
    }

    protected String getResourceName(JniLibrary jniLibrary) {
        return String.format("%s/%s", JniContext.getJavaPackagePath(), jniLibrary.getResourceName());
    }

    protected URL resolveClassLoaderUrlResource(String resourceName) {
        Class<?> clazz = this.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        logger.debug("Attempting to load resource '{}' from {} instance's ClassLoader {}", resourceName,
                clazz.getCanonicalName(), classLoader.toString());
        URL resource = classLoader.getResource(resourceName);
        if (resource != null) {
            logger.info("Resolved {} to URL {}", resourceName, resource.toString());
        } else {
            logger.warn("ClassLoader {} falied to resolve resource {}.", classLoader, resourceName);
        }
        return resource;
    }

    protected File loadFromDirectory(JniLibrary jniLibrary, File parentDir) {
        String libFileName = jniLibrary.getLibraryFileName();
        logger.debug("Attempting to load file {} from directory {}...", libFileName, parentDir);
        File libFile = new File(parentDir, jniLibrary.getLibraryFileName());
        if (libFile.isFile()) {
            logger.info("Resolved file {} to absolute path {}", libFileName, libFile.getAbsolutePath());
            return libFile;
        }
        logger.warn("Shared library file {} in directory {} does not exist.", libFileName, parentDir);
        return null;
    }

    private File getBuildDirectory(JniLibrary jniLibrary) {
        File libFile = new File(
                String.format("build/libs/%s/shared/%s", jniLibrary.getName(), jniLibrary.getPlatform().getName()));
        return libFile;
    }

    private static void copy(URL source, File dest) {
        if (source == null) {
            throw new NullPointerException("URL source argument cannot be null.");
        }
        if (dest == null) {
            throw new NullPointerException("File destination argument cannot be null.");
        }
        logger.debug("Copying URL resource {} to file {}", source, dest);
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
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            logger.error("Error copying from {} to {}", source, dest);
            throw new JniLibraryException(String.format("Exception while copying URL resource %s to file %s",
                    source.toString(), dest.getAbsolutePath()), e);
        }
    }

}
