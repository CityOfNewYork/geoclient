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

import java.io.File;

import javax.annotation.Nullable;

// Based on com.bmuschko.gradle.docker.internal.DefaultDockerConfigResolver
// See https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/internal/DefaultDockerConfigResolver.java
public abstract class AbstractConfigResolver {

    @Nullable
    String getEnv(String name) {
        return System.getenv(name);
    }

    @Nullable
    String getSystemProperty(String name) {
        return System.getProperty(name);
    }

    boolean isFileExists(String path) {
        return new File(path).exists();
    }

    boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

}
