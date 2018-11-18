/*
 * Copyright 2013-2016 the original author or authors.
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

import gov.nyc.doitt.gis.geoclient.jni.Geoclient;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

// TODO When rewriting this class see the following for some ideas:
// https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/java-library/multiproject
public class JniTest
{
  private static final Charset CHARSET = Charset.forName("UTF-8");
  private static final CharsetDecoder DECODER = CHARSET.newDecoder();
  private static final String DEFAULT_CLASSPATH_ROOT = "./";
  private static final String DEFAULT_CLASSPATH_ROOT_FROM_JAR = "/";
  private static final String DEFAULT_FILE = "jni-test.conf";
  private static final String CLASSPATH_FLAG = "classpath:";
  private static final int F1A_WA2_LENGTH = 2800;
  private static final int FHR_WA2_LENGTH = 2800;
  private static final int GEOSUPPORT_RC_LENGTH = 2;
  private static final int GEOSUPPORT_RC_START = 716;
  private static final String GEOSUPPORT_RC_SUCCESS = "00";
  private static final String GEOSUPPORT_RC_SUCCESS_WITH_WARN = "01";
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
  private static Logger logger;
  private final Geoclient geoclient;
  private List<TestConfig> testConfigs;

  public JniTest(Geoclient geoclient, boolean debug, List<TestConfig> testConfigs)
  {
    super();
    this.geoclient = geoclient;
    this.testConfigs = testConfigs;
  }

  /**
   * @param args command line arguments
   * @throws Exception if anything goes wrong
   */
  public static void main(String[] args) throws Exception
  {
    boolean debugOption = false;
    String fileOption = null;
    if (args != null && args.length > 0)
    {
      for (int i = 0; i < args.length; i++)
      {
        String string = args[i];
        if (OPTION_DEBUG_LONG.equalsIgnoreCase(string) || OPTION_DEBUG_SHORT.equalsIgnoreCase(string))
        {
          debugOption = true;
        } else if (OPTION_HELP_LONG.equalsIgnoreCase(string) || OPTION_HELP_SHORT.equalsIgnoreCase(string))
        {
          String myName = JniTest.class.getCanonicalName();
          System.out.println();
          System.out.println();
          System.out.println("Usage:");
          System.out.println();
          System.out.println("  java -Djava.library.path=\"<path to Geosupport and Geoclient .so's or .dll's>\" \\");
          System.out.println("           -cp=\"<path to Geoclient jars>\" " + myName + " [OPTIONS]");
          System.out.println();
          System.out.println("  -d,--debug            Enables debug logging");
          System.out.println();
          System.out.println("  --file=<path to file> Specifies the file containing test data and config to use for running tests.");
          System.out.println("                        The file path can be either full, relative or, if prefixed with '" + CLASSPATH_FLAG + "',");
          System.out.println("                        relative to the classpath.");
          System.out.println();
          System.out.println("                        If this argument is not given, the program will attempt to find a file named '" + DEFAULT_FILE + "'");
          System.out.println("                        at the root of the CLASSPATH (by default, the geoclient-jni jar includes such a file).");
          System.out.println("                        If the default test case file cannot be found, the built-in test cases are used.");
          System.out.println();
          System.out.println("  -h,--help             Show this message");
          System.out.println();
          System.out.println("  NOTE: In addition to the above, the Geosupport and Geoclient libraries must also be on the LD_LIBRARY_PATH ");
          System.out.println("        (for Linux) or the PATH (for Windows)");
          System.out.println();
          return;
        } else if (string.startsWith(OPTION_FILE))
        {
          fileOption = string.substring((OPTION_FILE + "=").length());
        }
      }

    }

    logger = new Logger(debugOption ? Logger.LEVEL_DEBUG : Logger.LEVEL_INFO);

    logger.debug("PATH=" + System.getenv("PATH"));
    logger.debug("LD_LIBRARY_PATH=" + System.getenv("LD_LIBRARY_PATH"));
    logger.debug("Using OPTIONS: ");
    logger.debug("    --debug=" + (debugOption ? "true" : "false"));
    logger.debug("    --file=" + (fileOption != null ? fileOption : "<not given>"));

    JniTest jniTest = new JniTest(new GeoclientJni(), debugOption, getTestCaseData(fileOption));
    System.exit(jniTest.runTest());
  }

  private static List<TestConfig> getTestCaseData(String fileOption) throws IOException
  {
    InputStream fileStream = null;
    if (fileOption == null)
    {
      // Try to find default file on the CLASSPATH
      logger.debug("--file option not given; checking for default file '" + DEFAULT_FILE + "'");

      String defaultFile = (iAmRunningFromAJar() ? DEFAULT_CLASSPATH_ROOT_FROM_JAR : DEFAULT_CLASSPATH_ROOT)
          + DEFAULT_FILE;
      fileStream = getClasspathResource(defaultFile);

      if (fileStream == null)
      {
        logger.debug("Tests will be run with built-in test case data");
        return DEFAULT_TEST_CONFIGS;
      }
      logger.debug("Tests will be run with default test case file '" + defaultFile
          + "' found on the CLASSPATH");

    } else
    {
      // User supplied a file
      if (fileOption.startsWith(CLASSPATH_FLAG))
      {
        // User specified to use CLASSPATH resolution
        fileStream = getClasspathResource(fileOption.substring(CLASSPATH_FLAG.length()));
      } else
      {
        // Regular File resource
        fileStream = getFilePathResource(fileOption);
      }

      // User supplied file not found
      if (fileStream == null)
      {
        throw new IOException(
            "Supplied file argument '"
                + fileOption
                + "' could not be resolved to an existing file. Run again with --debug to see what file path is being checked.");
      }

      logger.debug("Tests will be run with data from supplied file argument '" + fileOption + "'");
    }

    return new TestFileParser(fileStream, logger).parse();
  }

  private static boolean iAmRunningFromAJar()
  {
    String simpleName = JniTest.class.getSimpleName() + ".class";
    URL url = JniTest.class.getResource(simpleName);
    return url != null && url.toString().startsWith("jar:");
  }

  private static InputStream getClasspathResource(String classpathFile) throws IOException
  {
    logger.debug("Checking for CLASSPATH file: " + classpathFile);
    String msg = "JniTest.class.getResource(" + classpathFile + ") works? ";
    URL url = JniTest.class.getResource(classpathFile);
    if (url == null)
    {
      logger.debug(msg + "false");
    } else
    {
      logger.debug(msg + "true");
      return url.openStream();
    }

    msg = "JniTest.class.getClassLoader().getResource(" + classpathFile + ") works? ";
    url = JniTest.class.getClassLoader().getResource(classpathFile);
    if (url == null)
    {
      logger.debug(msg + "false");
      return null;
    }
    logger.debug(msg + "true");

    return url.openStream();
  }

  private static InputStream getFilePathResource(String fileOption) throws IOException
  {
    File file = new File(fileOption);

    if (file.isAbsolute())
    {
      logger.debug("Checking for file using absolute file path: " + fileOption);
    } else
    {
      logger.debug("Checking for file " + fileOption + " as relative path to user.dir=" + System.getProperty("user.dir"));
    }
    if (!file.exists())
    {
      return null;
    }
    return new FileInputStream(file);
  }

  public int runTest() throws Exception
  {
    int failures = 0;
    int errors = 0;
    for (TestConfig conf : testConfigs)
    {
      try
      {
        failures = failures + (callgeo(conf) ? 0 : 1);
      } catch (Throwable t)
      {
        t.printStackTrace();
        errors++;
      }
    }
    int exitCode = (errors + failures);
    String msg = String.format("Tests run: %d, Failures: %d, Errors: %d", testConfigs.size(), failures, errors);
    if(exitCode > 0)
    {
      logger.error(msg);
    } else
    {
      logger.info(msg);
    }
    return exitCode;
  }

  private boolean callgeo(TestConfig conf) throws Exception
  {
    //logger.debug(conf.toString());
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
    if(isSuccess)
    {
      logger.debug(message);
      logger.debug(wa1Msg);
      logger.raw(Logger.LEVEL_DEBUG, wa1Result);
      logger.debug(wa2Msg);
      logger.raw(Logger.LEVEL_DEBUG, wa2Result);
    } else
    {
      logger.error(message);
      logger.error(wa1Msg);
      logger.raw(Logger.LEVEL_ERROR, wa1Result);
      logger.error(wa2Msg);
      logger.raw(Logger.LEVEL_ERROR, wa2Result);
    }
    return isSuccess;
  }

  private boolean isSuccess(String returnCode)
  {
    if (isNotNullOrEmpty(returnCode))
    {
      return GEOSUPPORT_RC_SUCCESS.equals(returnCode) || GEOSUPPORT_RC_SUCCESS_WITH_WARN.equals(returnCode);
    }
    return false;
  }

  private String getReturnCode(String workAreaOneResult)
  {
    return workAreaOneResult.substring(GEOSUPPORT_RC_START, GEOSUPPORT_RC_START + GEOSUPPORT_RC_LENGTH);
  }

  private String decode(ByteBuffer buffer) throws Exception
  {
    return DECODER.decode(buffer).toString();
  }

  private boolean isNotNullOrEmpty(String s)
  {
    return s != null && s.length() > 0;
  }

  private static class Logger
  {
    private static final int LEVEL_DEBUG = 2;
    private static final int LEVEL_INFO = 1;
    private static final int LEVEL_ERROR = 0;
    private static final String PREFIX_ERROR = "[ERROR] ";
    private static final String PREFIX = "[JNI_TEST] ";
    private int level;

    public Logger(int level)
    {
      super();
      this.level = level;
    }

    public void debug(String message)
    {
      if (level >= LEVEL_DEBUG)
      {
        doLog(PREFIX, message);
      }
    }

    public void info(String message)
    {
      if (level >= LEVEL_INFO)
      {
        doLog(PREFIX, message);
      }
    }

    public void error(String message)
    {
      if (level >= LEVEL_ERROR)
      {
        doLog(PREFIX_ERROR, message);
      }
    }

    public void raw(int withLevel, String message)
    {
      if (withLevel <= level)
      {
        doLog(null, message);
      }
    }

    private void doLog(String prefix, String message)
    {
      StringBuffer buffer = new StringBuffer();
      if (prefix != null)
      {
        buffer.append(prefix);
      }
      if (message != null)
      {
        buffer.append(message);
      }
      System.out.println(buffer);
    }
  }

  private static class TestConfig
  {
    private final String functionName;
    private final String input;
    private final int lengthOfWorkAreaTwo;

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo)
    {
      super();
      this.functionName = functionName;
      this.input = input;
      this.lengthOfWorkAreaTwo = lengthOfWorkAreaTwo;
    }

    public String getFunctionName()
    {
      return functionName;
    }

    public String getInput()
    {
      return input;
    }

    public int getLengthOfWorkAreaTwo()
    {
      return lengthOfWorkAreaTwo;
    }

    @Override
    public String toString()
    {
      return "TestConfig [functionName=" + functionName + ", actualSize=" + input.length() + ", input=" + input
          + ", lengthOfWorkAreaTwo=" + lengthOfWorkAreaTwo + "]";
    }
  }

  private static class TestFileParser
  {
    private static final String CONF_START = "function=";
    private static final String CONF_PATTERN = "function=(\\w+);length=(\\d+);";
    private static final String COMMENT_PATTERN = "^\\s*#.*";
    private static final int MAX_LOG_LINE_SIZE = 100;
    private InputStream inputStream;
    private Logger logger;

    public TestFileParser(InputStream inputStream, Logger logger)
    {
      super();
      this.inputStream = inputStream;
      this.logger = logger;
    }

    public List<TestConfig> parse() throws FileNotFoundException, IOException
    {
      log("Parser debug output is truncated to a max of " + MAX_LOG_LINE_SIZE + " characters per line.");
      List<TestConfig> result = new ArrayList<TestConfig>();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      try
      {
        String line = reader.readLine();
        String functionName = null;
        int length = -1;
        while (line != null)
        {
          if (isComment(line))
          {
            // Do nothing
            log("comment: " + line);
          } else if (isConfigLine(line))
          {
            Scanner scanner = new Scanner(line);
            try
            {
              log("config line: " + line);
              scanner.findInLine(CONF_PATTERN);
              MatchResult match = scanner.match();
              functionName = match.group(1);
              log("parsed config functionName: " + functionName);
              length = Integer.parseInt(match.group(2));
              log("parsed config length: " + length);
            } finally
            {
              scanner.close();
            }
          } else
          {
            String input = parseInputLine(line, length);
            log("parsed input line: " + input);
            TestConfig conf = new TestConfig(functionName, input, length);
            log("adding " + conf);
            result.add(conf);
          }
          line = reader.readLine();
        }

      } finally
      {
        reader.close();
      }
      return result;
    }

    public boolean isComment(String line)
    {
      return line != null && line.matches(COMMENT_PATTERN);
    }

    public boolean isConfigLine(String line)
    {
      return line != null && line.startsWith(CONF_START);
    }

    public String parseInputLine(String line, int length)
    {
      if (line != null)
      {
        // Make sure it's padded to the required length
        return String.format("%1$-" + length + "s", line);
      }
      return null;
    }

    private void log(String message)
    {
        int maxToUse = MAX_LOG_LINE_SIZE;
        if (message.length() < MAX_LOG_LINE_SIZE)
        {
          maxToUse = message.length();
        }
        logger.debug(message.substring(0, maxToUse));
    }
  }
}
