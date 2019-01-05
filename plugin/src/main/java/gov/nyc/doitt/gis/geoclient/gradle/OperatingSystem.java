/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.gradle;
import java.util.HashMap;
import java.util.Map;
/**
 * The code and insspiration for this class has been copied from
 * <code>org.gradle.internal.nativeintegration.jansi.JansiOperatingSystemSupport.java</code>,
 * a class available in the Gradle project licensed with Apache License version 2.0.
 *
 * @see <a href="https://github.com/gradle/gradle">Gradle</a>
 */
public enum OperatingSystem {
    LINUX("linux"),
    WINDOWS("windows");
    private final static Map<String, OperatingSystem> MAPPING = new HashMap<String, OperatingSystem>();
    private final String identifier;
    static {
        for(OperatingSystem osSupport : values()) {
            MAPPING.put(osSupport.identifier, osSupport);
        }
    }
    OperatingSystem(String identifier) {
        this.identifier = identifier;
    }
    public String getIdentifier() {
        return identifier;
    }
    public static OperatingSystem forIdentifier(String identifier) {
        return MAPPING.get(identifier);
    }
}