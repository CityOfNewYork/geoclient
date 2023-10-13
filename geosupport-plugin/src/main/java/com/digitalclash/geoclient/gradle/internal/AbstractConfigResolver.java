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
