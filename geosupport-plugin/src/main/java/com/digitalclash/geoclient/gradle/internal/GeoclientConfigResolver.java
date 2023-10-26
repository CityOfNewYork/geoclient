/*
 * Copyright 2013-2023 the original author or authors.
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
package com.digitalclash.geoclient.gradle.internal;

public class GeoclientConfigResolver extends AbstractConfigResolver {

    public static final String DEFAULT_JNI_VERSION = "geoclient-jni-2";
    public static final String GC_JNI_VERSION_ENVVAR = "GC_JNI_VERSION";
    public static final String GC_JNI_VERSION_SYSTEM = "gc.jni.version";

    public String getJniVersion() {
        String jniVersion = getSystemProperty(GeoclientConfigResolver.GC_JNI_VERSION_SYSTEM);
        if (jniVersion != null) {
            return jniVersion;
        }
        jniVersion = getEnv(GeoclientConfigResolver.GC_JNI_VERSION_SYSTEM);
        if (jniVersion != null) {
            return jniVersion;
        }
        return GeoclientConfigResolver.DEFAULT_JNI_VERSION;
    }
}
