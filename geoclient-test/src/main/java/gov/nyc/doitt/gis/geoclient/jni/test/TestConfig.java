package gov.nyc.doitt.gis.geoclient.jni.test;

public class TestConfig
  {
    private final String functionName;
    private final String input;
    private final int lengthOfWorkAreaTwo;
    private final String expectedOutput;

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo, String expectedOutput)
    {
      super();
      this.functionName = functionName;
      this.input = input;
      this.lengthOfWorkAreaTwo = lengthOfWorkAreaTwo;
      this.expectedOutput = expectedOutput;
    }

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo)
    {
      this(functionName, input, lengthOfWorkAreaTwo, null);
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

    public String getExpectedOutput() {
    	return expectedOutput;
    }

    @Override
    public String toString()
    {
      return "TestConfig [functionName=" + functionName + ", actualSize=" + input.length() + ", input=" + input
          + ", lengthOfWorkAreaTwo=" + lengthOfWorkAreaTwo + "]";
    }
  }