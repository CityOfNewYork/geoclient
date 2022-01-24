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
package gov.nyc.doitt.gis.geoclient.jni.util;

public class JniLibrary {

    private final String name;

    private final Platform platform;

    private final String version;

    public static class Builder {

        private String name;

        private Platform platform;

        private String version;

        public Builder platform(Platform platform) {
            this.platform = platform;
            return this;
        }

        public Builder platform(String operatingSystem, String architecture) {
            this.platform = new Platform(operatingSystem, architecture);
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public JniLibrary build() {
            if (this.name == null) {
                throw new IllegalStateException("Name cannot be null");
            }

            if (this.platform == null) {
                throw new IllegalStateException("Platform cannot be null");
            }

            if (this.version == null) {
                this.version = "UNKNOWN";
            }

            return new JniLibrary(this.name, this.platform, this.version);
        }

    }

    private JniLibrary(String name, Platform platform, String version) {
        super();
        this.name = name;
        this.platform = platform;
        this.version = version;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public String getPlatformDirName() {
        return this.platform.getName().replace("_", "-");
    }

    public String getLibraryFileName() {
        return this.platform.getSharedLibraryFileName(this.name);
    }

    public String getResourceName() {
        return String.format("%s/%s", getPlatformDirName(), getLibraryFileName());
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

}
