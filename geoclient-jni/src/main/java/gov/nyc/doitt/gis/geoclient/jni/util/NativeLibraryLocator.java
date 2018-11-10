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

import java.io.*;
import java.net.URL;
import java.nio.channels.FileLock;

public class NativeLibraryLocator {
	
    final static String PATH_TEMPLATE = "%s/%s-%s";
    
    private final String parentPath;
    private final File extractDir;

    public NativeLibraryLocator(String parentPath, File extractDir) {
        this.parentPath = parentPath;
    	this.extractDir = extractDir;
    }

    public File find(JniLibrary jniLibrary) throws IOException {
        String resourceName = String.format(PATH_TEMPLATE, parentPath, jniLibrary.getOperatingSystem(), jniLibrary.getArchitecture());
        if (extractDir != null) {
            File libFile = new File(extractDir, String.format(PATH_TEMPLATE, jniLibrary.getVersion(), jniLibrary.getOperatingSystem(), jniLibrary.getArchitecture()));
            File lockFile = new File(libFile.getParentFile(), libFile.getName() + ".lock");
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
                if (resource != null) {
                    // Extract library and write marker to lock file
                    libFile.getParentFile().mkdirs();
                    copy(resource, libFile);
                    lockFileAccess.seek(0);
                    lockFileAccess.writeBoolean(true);
                    return libFile;
                }
            } finally {            	
                // Also releases lock
                lockFileAccess.close();
            }
        } else {
            URL resource = getClass().getClassLoader().getResource(resourceName);
            if (resource != null) {
                File libFile;
                File libDir = File.createTempFile("geoclient-jni", "dir");
                libDir.delete();
                libDir.mkdirs();
                libFile = new File(libDir, jniLibrary.getName());
                libFile.deleteOnExit();
                copy(resource, libFile);
                return libFile;
            }
        }
        return loadFromBuildDirectory(jniLibrary);
    }
    
    private File loadFromBuildDirectory(JniLibrary jniLibrary) {
        String componentName = jniLibrary.getName().replaceFirst("^lib", "").replaceFirst("\\.\\w+$", "");
        int pos = componentName.indexOf("-");
        while (pos >= 0) {
            componentName = componentName.substring(0, pos) + Character.toUpperCase(componentName.charAt(pos + 1)) + componentName.substring(pos + 2);
            pos = componentName.indexOf("-", pos);
        }
        File libFile = new File(String.format("build/libs/%s/shared/%s/%s", jniLibrary.getComponentName(), jniLibrary.getPlatformName(), jniLibrary.getName()));
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
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new JniLibraryException("Could not extract native JNI library.", e);
        }
    }
}
