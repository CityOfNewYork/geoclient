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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JniContext {

    static final Logger logger = LoggerFactory.getLogger(JniContext.class);

    private static final String GC_SHAREDLIB_BASENAME = "geoclientjni";
    private static final String GC_PACKAGE_PATH = JniContext.class.getPackage().getName().replaceAll("\\.", "\\/");

    private enum SystemProperty {

        GC_JNI_VERSION("gc.jni.version"), JAVA_IO_TMPDIR("java.io.tmpdir"), JAVA_LIBRARY_PATH("java.library.path");

        private final String key;

        private SystemProperty(String name) {
            this.key = name;
        }

        public String key() {
            return key;
        }

        @Override
        public String toString() {
            return key();
        }

    }

    private static String getSystemProperty(SystemProperty sysProp) {
        logger.info("Retrieving System property using {}", sysProp);
        return System.getProperty(sysProp.key());
    }

    public static String getGeoclientJniVersion() {
        return JniContext.getSystemProperty(SystemProperty.GC_JNI_VERSION);
    }

    public static String getJvmTempDir() {
        return JniContext.getSystemProperty(SystemProperty.JAVA_IO_TMPDIR);
    }

    public static String getJvmLibraryPath() {
        return JniContext.getSystemProperty(SystemProperty.JAVA_LIBRARY_PATH);
    }

    public static String getSharedLibraryBaseName() {
        return GC_SHAREDLIB_BASENAME;
    }

    public static String getJavaPackagePath() {
        return GC_PACKAGE_PATH;
    }
}
