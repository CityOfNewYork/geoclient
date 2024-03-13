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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

/**
 * The service that calls {@code geoclient-service} and writes the requests and
 * responses to files.
 *
 * @author mlipper
 */
public class GeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(SampleGeneratorApplication.class);
    private File outputDir;
    private RestClient restClient;

    /**
     * The generation service that is autowired by Spring.
     *
     * @param restClient the RestClient to use
     * @param outputDir the directory to write output files
     */
    public GeneratorService(RestClient restClient, File outputDir) {
        this.restClient = restClient;
        this.outputDir = outputDir;
    }

    public void write(Sample sample) {
        logger.info("Writing {} to {}", sample, this.outputDir.getAbsolutePath());
    }

}
