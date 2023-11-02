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
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * Gradle plugin extension for setting or getting Geosupport installation properties.
 * All properties not set by the build use conventional defaults.
 * <p>
 * The following example shows how to use this extension in a build script with the Groovy DSL:
 * <pre>
 * geosupport {
 *     home = '/opt/geosupport/current'
 *     geofiles = '/opt/geosupport/current/fls/'
 *     libraryPath = '/opt/geosupport/current/lib'
 *     includePath = fileTree(dir: 'src/main/headers')
 * }
 * </pre>
 */
public class GeosupportExtension {

    private final Property<String> geofiles;
    private final Property<String> home;
    private final DirectoryProperty includePath;
    private final Property<String> libraryPath;

    /**
     * Value to use for required Geosupport runtime enviroment
     * variable, <code>GEOFILES</code>. This should be the absolute path
     * to the Geosupport data files directory, typically:
     * <code>$GEOSUPPORT_HOME/fls/</code>.
     * <p>
     * <strong>IMPORTANT</strong>: Geosupport <em>requires</em> that this
     * path end with a trailing slash (<code>java.file.separator</code>).
     * <p>
     * Defaults to:
     * <ul>
     * <li><code>/opt/geosupport/current/fls/</code> on Linux/Unix</li>
     * <li><code>c:/opt/geosupport/current/fls/</code> on Windows</li>
     * </ul>
     */
    @Input
    public final Property<String> getGeofiles() {
        return geofiles;
    }

    /**
     * Value to use for the optional <code>GEOSUPPORT_HOME</code> enviroment
     * variable. This variable is not used directly by Geosupport but is
     * often useful for configuring client application runtimes and deployments.
     * <p>
     * Defaults to:
     * <ul>
     * <li><code>/opt/geosupport/current</code> on Linux/Unix</li>
     * <li><code>c:/opt/geosupport/current</code> on Windows</li>
     * </ul>
     */
    @Input
    @Optional
    public final Property<String> getHome() {
        return home;
    }

    /**
     * The path to the directory containing Geosupport's C header files.
     * This property is not used directly by Geosupport but is
     * often useful for compiling C/C++ or JNI code that uses the Geosupport
     * C API.
     * <p>
     * NOTE: Unlike the other extension
     * <code>org.gradle.api.provider.Property<String></code> properties, this
     * property is a <code>org.gradle.api.file.DirectoryProperty<String></code>
     * to make it easier to integrate in a Gradle build.
     * <p>
     * Defaults to:
     * <ul>
     * <li><code>/opt/geosupport/current/include</code> on Linux/Unix</li>
     * <li><code>c:/opt/geosupport/current/include</code> on Windows</li>
     * </ul>
     */
    @InputDirectory
    @Optional
    @PathSensitive(PathSensitivity.RELATIVE)
    public final DirectoryProperty getIncludePath() {
        return includePath;
    }

    /**
     * The path to the directory containing Geosupport's C shared libraries.
     * This property is not used directly by Geosupport but is
     * often useful for configuring client application runtimes for things
     * like <code>LD_LIBRARY_PATH</code> and <code>java.library.path</code>.
     * <p>
     * Defaults to:
     * <ul>
     * <li><code>/opt/geosupport/current/lib</code> on Linux/Unix</li>
     * <li><code>c:/opt/geosupport/current/lib</code> on Windows</li>
     * </ul>
     */
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
