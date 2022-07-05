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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TestConfig {
    private final String functionName;
    private final String input;
    private final int lengthOfWorkAreaOne;
    private final int lengthOfWorkAreaTwo;
    private final String expectedOutput;

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo, String expectedOutput) {
        super();
        this.functionName = functionName;
        this.input = input;
        this.lengthOfWorkAreaOne = TestFileParser.INPUT_LENGTH;
        this.lengthOfWorkAreaTwo = lengthOfWorkAreaTwo;
        this.expectedOutput = expectedOutput;
        validateInput(input);
    }

    public TestConfig(String functionName, String input, int lengthOfWorkAreaTwo) {
        this(functionName, input, lengthOfWorkAreaTwo, null);
    }

    public ByteBuffer getWorkAreaOne() {

        // ByteBuffer buffer = ByteBuffer.wrap(getInput().getBytes());
        ByteBuffer buffer = ByteBuffer.allocate(getLengthOfWorkAreaOne());
        buffer.put(getWorkAreaOneBytes());
        buffer.flip();
        return buffer;
    }

    public ByteBuffer getWorkAreaTwo() {
        ByteBuffer buffer = ByteBuffer.allocate(getLengthOfWorkAreaTwo());
        buffer.put(getWorkAreaTwoBytes());
        buffer.flip();
        return buffer;
    }

    public byte[] getWorkAreaOneBytes() {
        return getInput().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getWorkAreaTwoBytes() {
        // Copied from gov.nyc.doitt.gis.geoclient.function.Field
        // Allocate result array
        byte[] bytes = new byte[getLengthOfWorkAreaTwo()];
        // Fill with blanks
        Arrays.fill(bytes, (byte) ' ');
        return bytes;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getInput() {
        return input;
    }

    public int getLengthOfWorkAreaOne() {
        return lengthOfWorkAreaOne;
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

    private void validateInput(String input) {
        if (input == null) {
            throw new TestConfigurationException("Function input argument cannot be null");
        }
        if (input.isEmpty()) {
            throw new TestConfigurationException("Function input argument cannot be empty");
        }
        if (getLengthOfWorkAreaOne() != input.length()) {
            throw new TestConfigurationException(
                    String.format("Expected length of function input to be %d but instead was %d",
                            getLengthOfWorkAreaOne(), input.length()));
        }
    }

    @Override
    public String toString() {
        return "TestConfig [functionName=" + functionName + ", actualSize=" + input.length() + ", input=" + input
                + ", lengthOfWorkAreaTwo=" + lengthOfWorkAreaTwo + "]";
    }
}