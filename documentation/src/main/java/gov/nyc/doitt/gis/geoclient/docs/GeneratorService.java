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

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * The service that calls {@code geoclient-service} and writes the requests and
 * responses to files.
 *
 * @author mlipper
 */
@Service
public class GeneratorService {
    private File outputDirectory;
    private RestClient restClient;

    /**
     * The generation service that is autowired by Spring.
     *
     * @param properties GeneratorProperties
     */
    public GeneratorService(GeneratorProperties properties) {
        this.outputDirectory = properties.getOutputDir();
        String baseUrl = properties.getBaseUrl();
        baseUrl = baseUrl != null ? baseUrl : "http://localhost:8080/geoclient/v2";
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public void write() {
        System.out.println("Calling " + this.restClient.get().uri("version").retrieve().toEntity(String.class));
        System.out.println("Writing to " + this.outputDirectory.getAbsolutePath());
    }

}
