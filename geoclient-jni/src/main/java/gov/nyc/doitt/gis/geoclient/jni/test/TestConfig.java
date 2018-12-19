package gov.nyc.doitt.gis.geoclient.jni.test;

import java.nio.ByteBuffer;

public class TestConfig {
    private final String functionName;
    private final String input;
    private final int lengthOfWorkAreaTwo;
    private final String expectedOutput;

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo, String expectedOutput) {
        super();
        this.functionName = functionName;
        this.input = input;
        this.lengthOfWorkAreaTwo = lengthOfWorkAreaTwo;
        this.expectedOutput = expectedOutput;
    }

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo) {
        this(functionName, input, lengthOfWorkAreaTwo, null);
    }

    public ByteBuffer getWorkAreaOne() {
        return ByteBuffer.wrap(getInput().getBytes());
    }

    public ByteBuffer getWorkAreaTwo() {
        return ByteBuffer.allocate(getLengthOfWorkAreaTwo());
    }

    public byte[] getWorkAreaOneBytes() {
        return getInput().getBytes();
    }

    public byte[] getWorkAreaTwoBytes() {
        return new byte[getLengthOfWorkAreaTwo()];
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getInput() {
        return input;
    }

    public int getLengthOfWorkAreaTwo() {
        return lengthOfWorkAreaTwo;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public boolean hasExpectedOutput() {
        return this.expectedOutput != null;
    }

    @Override
    public String toString() {
        return "TestConfig [functionName=" + functionName + ", actualSize=" + input.length() + ", input=" + input
                + ", lengthOfWorkAreaTwo=" + lengthOfWorkAreaTwo + "]";
    }
}