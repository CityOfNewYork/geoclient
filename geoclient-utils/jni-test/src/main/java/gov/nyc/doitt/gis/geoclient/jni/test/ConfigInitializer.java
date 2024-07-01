/*
 * Copyright 2013-2024 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.jni.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * Factory for creating TestConfig instances.
 *
 * @author mlipper
 *
 * @since 2.0
 *
 */
public class ConfigInitializer {

    public static final String DEFAULT_FILE_NAME = "/jni-test.conf";

    public List<TestConfig> create(File file) throws FileNotFoundException, IOException {
        return new TestFileParser().parse(file);
    }

    public List<TestConfig> create(URL url) throws FileNotFoundException, IOException {
        return new TestFileParser().parse(url);
    }

    public List<TestConfig> create(String fileName) throws FileNotFoundException, IOException {
        return new TestFileParser().parse(fileName);
    }

    public List<TestConfig> create() throws FileNotFoundException, IOException {
        return new TestFileParser(this.getClass().getResourceAsStream(DEFAULT_FILE_NAME)).parse();
    }

    public static List<TestConfig> defaultTestConfigs() throws FileNotFoundException, IOException {
        ConfigInitializer configInitializer = new ConfigInitializer();
        return configInitializer.create();
    }
}
