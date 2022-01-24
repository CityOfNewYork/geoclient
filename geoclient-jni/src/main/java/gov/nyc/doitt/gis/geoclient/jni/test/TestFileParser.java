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
package gov.nyc.doitt.gis.geoclient.jni.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileParser {
    final Logger logger = LoggerFactory.getLogger(TestFileParser.class);

    public static final int INPUT_LENGTH = 1200;
    private static final String CONF_START = "function=";
    private static final String CONF_PATTERN = "function=(\\w+);length=(\\d+);";
    private static final String COMMENT_PATTERN = "^\\s*#.*";
    private static final int MAX_LOG_LINE_SIZE = 100;
    private InputStream inputStream;

    public TestFileParser() {
        super();
    }

    public TestFileParser(InputStream inputStream) {
        super();
        this.inputStream = inputStream;
    }

    public List<TestConfig> parse(File file) throws FileNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            this.inputStream = fis;
            return parse();
        }
    }

    public List<TestConfig> parse(URL url) throws FileNotFoundException, IOException {
        return parse(url.getFile());
    }

    public List<TestConfig> parse(String fileName) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return parse(reader);
        }
    }

    private List<TestConfig> parse(BufferedReader reader) throws FileNotFoundException, IOException {
        log("Parser debug output is truncated to a max of " + MAX_LOG_LINE_SIZE + " characters per line.");
        List<TestConfig> result = new ArrayList<TestConfig>();
        String line = reader.readLine();
        String functionName = null;
        int length = -1;
        while (line != null) {
            if (isComment(line)) {
                // Do nothing
                log("comment: " + line);
            } else if (isConfigLine(line)) {
                try (Scanner scanner = new Scanner(line);) {
                    log("config line: " + line);
                    scanner.findInLine(CONF_PATTERN);
                    MatchResult match = scanner.match();
                    functionName = match.group(1);
                    log("parsed config functionName: " + functionName);
                    length = Integer.parseInt(match.group(2));
                    log("parsed config length: " + length);
                }
            } else {
                // Input (workAreaOne) line
                String input = parseInputLine(line, INPUT_LENGTH);
                log("parsed input line: " + input);
                TestConfig conf = new TestConfig(functionName, input, length);
                log("adding " + conf);
                result.add(conf);
            }
            line = reader.readLine();
        }
        return result;
    }

    public List<TestConfig> parse() throws FileNotFoundException, IOException {

        if (inputStream == null) {
            throw new IllegalStateException("InputStream field cannot be null");
        }
        try (InputStreamReader isReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isReader);) {
            logger.warn("Test input stream encoding: {}", isReader.getEncoding());
            return parse(reader);
        }
    }

    public boolean isComment(String line) {
        return line != null && line.matches(COMMENT_PATTERN);
    }

    public boolean isConfigLine(String line) {
        return line != null && line.startsWith(CONF_START);
    }

    public String parseInputLine(String line, int length) {
        if (line != null) {
            // Make sure it's padded to the required length
            return String.format("%1$-" + length + "s", line);
        }
        return null;
    }

    private void log(String message) {
        int maxToUse = MAX_LOG_LINE_SIZE;
        if (message.length() < MAX_LOG_LINE_SIZE) {
            maxToUse = message.length();
        }
        logger.debug(message.substring(0, maxToUse));
    }
}