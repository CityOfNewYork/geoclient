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
package com.digitalclash.geoclient.gradle;

import java.io.File;

import javax.inject.Inject;

import com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;

public class GeosupportExtension {

    private final Property<String> geofiles;
    private final Property<String> home;
    private final DirectoryProperty includePath;
    private final Property<String> libraryPath;

    /*
     * TODO Use of org.gradle.api.tasks.PathSensitive and org.gradle.api.tasks.PathSensitivity?
     */

    @Input
    public final Property<String> getGeofiles() {
        return geofiles;
    }

    @Input
    @Optional
    public final Property<String> getHome() {
        return home;
    }

    @InputDirectory
    @Optional
    public final DirectoryProperty getIncludePath() {
        return includePath;
    }

    @Input
    @Optional
    public final Property<String> getLibraryPath() {
        return libraryPath;
    }

    @Inject
    public GeosupportExtension(ObjectFactory objectFactory) {
        GeosupportConfigResolver config = new GeosupportConfigResolver();
        geofiles = objectFactory.property(String.class);
        geofiles.convention(config.getGeofiles());
        home = objectFactory.property(String.class);
        home.convention(config.getHome());
        includePath = objectFactory.directoryProperty();
        includePath.convention(objectFactory.directoryProperty().fileValue(new File(config.getIncludePath())));
        libraryPath = objectFactory.property(String.class);
        libraryPath.convention(config.getLibraryPath());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GeosupportExtension [ ");
        sb.append("home: ");
        sb.append(this.getHome().getOrNull());
        sb.append(", geofiles: ");
        sb.append(this.getGeofiles().getOrNull());
        sb.append(", includePath: ");
        sb.append(this.getIncludePath().getOrNull());
        sb.append(", libraryPath: ");
        sb.append(this.getLibraryPath().getOrNull());
        sb.append(" ]");
        return sb.toString();
    }
}
