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

import org.gradle.api.Transformer;
import org.gradle.api.file.Directory;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;

public class IntegrationTestOptions {

    static final String DEFAULT_INTEGRATION_TEST_NAME = "geosupportIntegrationTest";
    static final String DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME = "geosupportIntegrationTest";

    private final Property<String> testName;
    private final Property<String> sourceSetName;
    private final Provider<Directory> javaSourceDir;
    private final Provider<Directory> resourcesSourceDir;
    private final Property<Boolean> validate;
    private final Property<Boolean> useJavaLibraryPath;
    private final Property<Boolean> exportLdLibraryPath;

    @Input
    public final Property<String> getTestName() {
        return testName;
    }

    @Input
    public final Property<String> getSourceSetName(){
        return sourceSetName;
    }

    @InputDirectory
    public final Provider<Directory> getJavaSourceDir() {
        return javaSourceDir;
    }

    @InputDirectory
    public final Provider<Directory> getResourcesSourceDir(){
        return resourcesSourceDir;
    }

    @Input
    public final Property<Boolean> getValidate() {
        return validate;
    }

    @Input
    public final Property<Boolean> getUseJavaLibraryPath(){
        return useJavaLibraryPath;
    }

    @Input
    public final Property<Boolean> getExportLdLibraryPath() {
        return exportLdLibraryPath;
    }

    @Inject
    public IntegrationTestOptions(ObjectFactory objectFactory) {
        testName = objectFactory.property(String.class);
        testName.convention(DEFAULT_INTEGRATION_TEST_NAME);
        sourceSetName = objectFactory.property(String.class);
        sourceSetName.convention(DEFAULT_INTEGRATION_TEST_SOURCE_SET_NAME);
        javaSourceDir = sourceSetName.flatMap(new Transformer<Provider<Directory>, String>() {
            @Override
            public Provider<Directory> transform(String name){
                String path = String.format("src/%s/java", name);
                return objectFactory.directoryProperty().fileValue(new File(path));
            }
        });
        resourcesSourceDir = sourceSetName.flatMap(new Transformer<Provider<Directory>, String>() {
            @Override
            public Provider<Directory> transform(String name){
                String path = String.format("src/%s/resources", name);
                return objectFactory.directoryProperty().fileValue(new File(path));
            }
        });
        validate = objectFactory.property(Boolean.class);
        validate.convention(false);
        useJavaLibraryPath = objectFactory.property(Boolean.class);
        useJavaLibraryPath.convention(false);
        exportLdLibraryPath = objectFactory.property(Boolean.class);
        exportLdLibraryPath.convention(false);
    }

    @Override
    public String toString() {
        StringBuffer sb =new StringBuffer();
        sb.append("GeosupportIntegrationTestOptions [ ");
        sb.append("testName: ");
        sb.append(this.getTestName().getOrNull());
        sb.append(", sourceSetName: ");
        sb.append(this.getSourceSetName().getOrNull());
        sb.append(", javaSourceDir: ");
        sb.append(this.getJavaSourceDir().getOrNull());
        sb.append(", resourcesSourceDir: ");
        sb.append(this.getResourcesSourceDir().getOrNull());
        sb.append(", validate:");
        sb.append(this.getValidate().getOrNull());
        sb.append(", useJavaLibraryPath: ");
        sb.append(this.getUseJavaLibraryPath().getOrNull());
        sb.append(", exportLdLibraryPath: ");
        sb.append(this.getExportLdLibraryPath().getOrNull());
        sb.append(" ]");
        return sb.toString();
    }


}
