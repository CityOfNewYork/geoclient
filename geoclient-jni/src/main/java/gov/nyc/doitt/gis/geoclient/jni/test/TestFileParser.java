package gov.nyc.doitt.gis.geoclient.jni.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

import org.slf4j.Logger;

public class TestFileParser {
    final Logger logger;

    private static final String CONF_START = "function=";
    private static final String CONF_PATTERN = "function=(\\w+);length=(\\d+);";
    private static final String COMMENT_PATTERN = "^\\s*#.*";
    private static final int MAX_LOG_LINE_SIZE = 100;
    private InputStream inputStream;

    public TestFileParser(InputStream inputStream, Logger logger) {
        super();
        this.inputStream = inputStream;
        this.logger = logger;
    }

    public List<TestConfig> parse() throws FileNotFoundException, IOException {
        log("Parser debug output is truncated to a max of " + MAX_LOG_LINE_SIZE + " characters per line.");
        List<TestConfig> result = new ArrayList<TestConfig>();
        InputStreamReader isReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        logger.warn("Test input stream encoding: {}", isReader.getEncoding());
        BufferedReader reader = new BufferedReader(isReader);
        try {
            String line = reader.readLine();
            String functionName = null;
            int length = -1;
            while (line != null) {
                if (isComment(line)) {
                    // Do nothing
                    log("comment: " + line);
                } else if (isConfigLine(line)) {
                    Scanner scanner = new Scanner(line);
                    try {
                        log("config line: " + line);
                        scanner.findInLine(CONF_PATTERN);
                        MatchResult match = scanner.match();
                        functionName = match.group(1);
                        log("parsed config functionName: " + functionName);
                        length = Integer.parseInt(match.group(2));
                        log("parsed config length: " + length);
                    } finally {
                        scanner.close();
                    }
                } else {
                    String input = parseInputLine(line, length);
                    log("parsed input line: " + input);
                    TestConfig conf = new TestConfig(functionName, input, length);
                    log("adding " + conf);
                    result.add(conf);
                }
                line = reader.readLine();
            }

        } finally {
            reader.close();
        }
        return result;
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