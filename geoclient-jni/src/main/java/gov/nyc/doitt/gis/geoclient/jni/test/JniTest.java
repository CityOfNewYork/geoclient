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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.Geoclient;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;

// TODO When rewriting this class see the following for some ideas:
// https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/java-library/multiproject
public class JniTest {
    private static final String DEFAULT_CLASSPATH_ROOT = "./";
    private static final String DEFAULT_CLASSPATH_ROOT_FROM_JAR = "/";
    private static final String DEFAULT_FILE = "jni-test.conf";
    private static final String CLASSPATH_FLAG = "classpath:";
    private static final int F1A_WA2_LENGTH = 2800;
    private static final int FHR_WA2_LENGTH = 2800;
    private static final String INPUT_1A = "1A240                                                   1          rsd                                                                                                                                              C                                                                                                                    X                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ";
    private static final String INPUT_HR = "HR                                                                                                                                                                                                                  C                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ";
    private static final String OPTION_DEBUG_LONG = "--debug";
    private static final String OPTION_DEBUG_SHORT = "-d";
    private static final String OPTION_FILE = "--file";
    private static final String OPTION_HELP_LONG = "--help";
    private static final String OPTION_HELP_SHORT = "--h";

    private static final List<TestConfig> DEFAULT_TEST_CONFIGS = new ArrayList<TestConfig>();
    {
        DEFAULT_TEST_CONFIGS.add(new TestConfig("1A", INPUT_1A, F1A_WA2_LENGTH));
        DEFAULT_TEST_CONFIGS.add(new TestConfig("HR", INPUT_HR, FHR_WA2_LENGTH));
    }

    static final Logger logger = LoggerFactory.getLogger(JniTest.class);
    private final Geoclient geoclient;
    private List<TestConfig> testConfigs;

    public JniTest(Geoclient geoclient, boolean debug, List<TestConfig> testConfigs) {
        super();
        this.geoclient = geoclient;
        this.testConfigs = testConfigs;
    }

    /**
     * @param args command line arguments
     * @throws Exception if anything goes wrong
     */
    public static void main(String[] args) throws Exception {
        boolean debugOption = false;
        String fileOption = null;
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String string = args[i];
                if (OPTION_DEBUG_LONG.equalsIgnoreCase(string) || OPTION_DEBUG_SHORT.equalsIgnoreCase(string)) {
                    debugOption = true;
                } else if (OPTION_HELP_LONG.equalsIgnoreCase(string) || OPTION_HELP_SHORT.equalsIgnoreCase(string)) {
                    String myName = JniTest.class.getCanonicalName();
                    logger.info("");
                    logger.info("");
                    logger.info("Usage:");
                    logger.info("");
                    logger.info("  java -Djava.library.path=\"<path to Geosupport and Geoclient .so's or .dll's>\" \\");
                    logger.info("           -cp=\"<path to Geoclient jars>\" " + myName + " [OPTIONS]");
                    logger.info("");
                    logger.info("  -d,--debug            Enables debug logging");
                    logger.info("");
                    logger.info(
                            "  --file=<path to file> Specifies the file containing test data and config to use for running tests.");
                    logger.info(
                            "                        The file path can be either full, relative or, if prefixed with '"
                                    + CLASSPATH_FLAG + "',");
                    logger.info("                        relative to the classpath.");
                    logger.info("");
                    logger.info(
                            "                        If this argument is not given, the program will attempt to find a file named '"
                                    + DEFAULT_FILE + "'");
                    logger.info(
                            "                        at the root of the CLASSPATH (by default, the geoclient-jni jar includes such a file).");
                    logger.info(
                            "                        If the default test case file cannot be found, the built-in test cases are used.");
                    logger.info("");
                    logger.info("  -h,--help             Show this message");
                    logger.info("");
                    logger.info(
                            "  NOTE: In addition to the above, the Geosupport and Geoclient libraries must also be on the LD_LIBRARY_PATH ");
                    logger.info("        (for Linux) or the PATH (for Windows)");
                    logger.info("");
                    return;
                } else if (string.startsWith(OPTION_FILE)) {
                    fileOption = string.substring((OPTION_FILE + "=").length());
                }
            }

        }

        logger.debug("PATH=" + System.getenv("PATH"));
        logger.debug("LD_LIBRARY_PATH=" + System.getenv("LD_LIBRARY_PATH"));
        logger.debug("Using OPTIONS: ");
        logger.debug("    --debug=" + (debugOption ? "true" : "false"));
        logger.debug("    --file=" + (fileOption != null ? fileOption : "<not given>"));

        JniTest jniTest = new JniTest(new GeoclientJni(), debugOption, getTestCaseData(fileOption));
        System.exit(jniTest.runTest());
    }

    // TODO InputStream is not closed!!
    // FIXME
    public static List<TestConfig> getTestCaseData(String fileOption) throws IOException {
        InputStream fileStream = null;
        if (fileOption == null) {
            // Try to find default file on the CLASSPATH
            logger.debug("--file option not given; checking for default file '" + DEFAULT_FILE + "'");

            String defaultFile = (iAmRunningFromAJar() ? DEFAULT_CLASSPATH_ROOT_FROM_JAR : DEFAULT_CLASSPATH_ROOT)
                    + DEFAULT_FILE;
            fileStream = getClasspathResource(defaultFile);

            if (fileStream == null) {
                logger.debug("Tests will be run with built-in test case data");
                return DEFAULT_TEST_CONFIGS;
            }
            logger.debug("Tests will be run with default test case file '" + defaultFile + "' found on the CLASSPATH");

        } else {
            // User supplied a file
            if (fileOption.startsWith(CLASSPATH_FLAG)) {
                // User specified to use CLASSPATH resolution
                fileStream = getClasspathResource(fileOption.substring(CLASSPATH_FLAG.length()));
            } else {
                // Regular File resource
                fileStream = getFilePathResource(fileOption);
            }

            // User supplied file not found
            if (fileStream == null) {
                throw new IOException("Supplied file argument '" + fileOption
                        + "' could not be resolved to an existing file. Run again with --debug to see what file path is being checked.");
            }

            logger.debug("Tests will be run with data from supplied file argument '" + fileOption + "'");
        }

        return new TestFileParser(fileStream).parse();
    }

    private static boolean iAmRunningFromAJar() {
        String simpleName = JniTest.class.getSimpleName() + ".class";
        URL url = JniTest.class.getResource(simpleName);
        return url != null && url.toString().startsWith("jar:");
    }

    private static InputStream getClasspathResource(String classpathFile) throws IOException {
        logger.debug("Checking for CLASSPATH file: " + classpathFile);
        String msg = "JniTest.class.getResource(" + classpathFile + ") works? ";
        URL url = JniTest.class.getResource(classpathFile);
        if (url == null) {
            logger.debug(msg + "false");
        } else {
            logger.debug(msg + "true");
            return url.openStream();
        }

        msg = "JniTest.class.getClassLoader().getResource(" + classpathFile + ") works? ";
        url = JniTest.class.getClassLoader().getResource(classpathFile);
        if (url == null) {
            logger.debug(msg + "false");
            return null;
        }
        logger.debug(msg + "true");

        return url.openStream();
    }

    private static InputStream getFilePathResource(String fileOption) throws IOException {
        File file = new File(fileOption);

        if (file.isAbsolute()) {
            logger.debug("Checking for file using absolute file path: " + fileOption);
        } else {
            logger.debug("Checking for file " + fileOption + " as relative path to user.dir="
                    + System.getProperty("user.dir"));
        }
        if (!file.exists()) {
            return null;
        }
        return new FileInputStream(file);
    }

    public int runTest() throws Exception {
        int failures = 0;
        int errors = 0;
        for (TestConfig conf : testConfigs) {
            try {
                failures = failures + (callgeo(conf) ? 0 : 1);
            } catch (Throwable t) {
                t.printStackTrace();
                errors++;
            }
        }
        int exitCode = (errors + failures);
        String msg = String.format("Tests run: %d, Failures: %d, Errors: %d", testConfigs.size(), failures, errors);
        if (exitCode > 0) {
            logger.error(msg);
        } else {
            logger.info(msg);
        }
        return exitCode;
    }

    private boolean callgeo(TestConfig conf) throws Exception {
        // logger.debug(conf.toString());
        ByteBuffer wa1 = ByteBuffer.wrap(conf.getInput().getBytes());
        ByteBuffer wa2 = ByteBuffer.allocate(conf.getLengthOfWorkAreaTwo());
        geoclient.callgeo(wa1, wa2);
        String wa1Result = decode(wa1);
        String wa2Result = decode(wa2);
        String rc = getReturnCode(wa1Result);
        String message = String.format("Result of %s call:  geosupportReturnCode = \"%s\"", conf.getFunctionName(), rc);
        boolean isSuccess = isSuccess(rc);
        String wa1Msg = String.format("WA1 of %s:", conf.getFunctionName());
        String wa2Msg = String.format("WA2 of %s:", conf.getFunctionName());
        if (isSuccess) {
            logger.debug(message);
            logger.debug(wa1Msg);
            logger.debug(wa1Result);
            logger.debug(wa2Msg);
            logger.debug(wa2Result);
        } else {
            logger.error(message);
            logger.error(wa1Msg);
            logger.error(wa1Result);
            logger.error(wa2Msg);
            logger.error(wa2Result);
        }
        return isSuccess;
    }

    private boolean isSuccess(String returnCode) {
        return ByteBufferUtils.isSuccess(returnCode);
    }

    private String getReturnCode(String workAreaOneResult) {
        return ByteBufferUtils.getReturnCode(workAreaOneResult);
    }

    private String decode(ByteBuffer buffer) throws Exception {
        return ByteBufferUtils.decode(buffer);
    }
}
