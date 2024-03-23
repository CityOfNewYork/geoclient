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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    private final File outputDir;

    public ResponseWriter(File outputDir) {
        this.outputDir = outputDir;
    }

    public void write(Sample sample, Response response) {
        Path outputFile = outputFile(sample);
        logger.info("Sample {} - output file: {}", sample, outputFile);
        try {
            Files.write(outputFile, response.getBody().getBytes());
        } catch (IOException e) {
            logger.error("Could not write output file for sample " + sample.toString(), e);
            throw new DocumentationException("Exception creating sample " + sample.toString(), e);
        }
    }

    Path outputFile(Sample sample) {
        return Paths.get(this.outputDir.getAbsolutePath(), sample.getPathVariable() + "-" + sample.getId() + ".json");
    }
}
