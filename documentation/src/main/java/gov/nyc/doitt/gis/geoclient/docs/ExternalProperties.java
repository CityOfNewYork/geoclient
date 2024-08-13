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
package gov.nyc.doitt.gis.geoclient.docs;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bean to hold configuration properties autowired by Spring.
 * Typically configured using environment variables prefixed with <pre>DOCGEN_</pre>.
 *
 * @author mlipper
 */
@ConfigurationProperties("generator")
public class ExternalProperties {

    private String baseUrl;
    private File outputDir;

    /**
     * Returns the base URL for the {@code geoclient-service}.
     *
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return this.baseUrl;
    }

    /**
     * Sets the base URL for the {@code geoclient-service}.
     * Can be set with environment variable <pre>DOCGEN_BASEURL</pre>.
     *
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Returns output directory where requests and responses will be written.
     *
     * @return the outputDir
     */
    public File getOutputDir() {
        return outputDir;
    }

    /**
     * Sets the output directory where requests and responses will be written.
     * Can be set with environment variable <pre>DOCGEN_OUTPUTDIRECTORY</pre>.
     *
     * @param outputDir the outputDir to set
     */
    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

}
