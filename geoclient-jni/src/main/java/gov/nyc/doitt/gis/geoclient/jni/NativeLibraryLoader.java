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
package gov.nyc.doitt.gis.geoclient.jni;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.util.JniLibrary;
import gov.nyc.doitt.gis.geoclient.jni.util.NativeLibraryLocator;
import gov.nyc.doitt.gis.geoclient.jni.util.Platform;

/**
 * Loads native shared libraries for use with the JNI API a using simple,
 * operating system-specific logic.
 */
public class NativeLibraryLoader {

    final Logger logger = LoggerFactory.getLogger(NativeLibraryLocator.class);

    private final String baseLibraryName;

    public NativeLibraryLoader(String baseLibraryName) {
        super();
        this.baseLibraryName = baseLibraryName;
    }

    public void loadLibrary(String extractDir) throws IOException {
        NativeLibraryLocator locator = new NativeLibraryLocator(extractDir);
        File libFile = locator.find(getJniLibrary());
        logger.info("Attempting to load {} from file {}", baseLibraryName, libFile.getCanonicalPath());
        System.load(libFile.getCanonicalPath());
        logger.info("Successfully loaded {} from file {}", baseLibraryName, libFile.getCanonicalPath());
    }

    protected JniLibrary getJniLibrary() {
        return JniLibrary.builder().name(this.baseLibraryName).platform(new Platform())
                .version(JniContext.getGeoclientJniVersion()).build();
    }

}
