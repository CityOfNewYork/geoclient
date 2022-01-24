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
package gov.nyc.doitt.gis.geoclient.jni;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.test.ByteBufferUtils;
import gov.nyc.doitt.gis.geoclient.jni.test.TestConfig;
import gov.nyc.doitt.gis.geoclient.jni.test.TestFileParser;

class GeoclientJniTest {
    final static Logger logger = LoggerFactory.getLogger(GeoclientJniTest.class);

    private GeoclientJni geoclientJni = new GeoclientJni();

    static Stream<TestConfig> getFixtures() throws FileNotFoundException, IOException {
        TestFileParser parser = new TestFileParser();
        URL url = GeoclientJniTest.class.getResource("/jni-test.conf");
        List<TestConfig> configs = parser.parse(url);
        return configs.stream();
    }

    @ParameterizedTest
    @MethodSource("getFixtures")
    void testCallgeoWithByteBuffers(TestConfig conf) throws CharacterCodingException {
        logFunctionCall(conf, "ByteBuffer");
        ByteBuffer wa1 = conf.getWorkAreaOne();
        logByteBuffer("1", wa1);
        ByteBuffer wa2 = ByteBuffer.allocate(conf.getLengthOfWorkAreaTwo());
        // conf.getWorkAreaTwo();
        logByteBuffer("2", wa2);
        geoclientJni.callgeo(wa1, wa2);
        String actualW1 = ByteBufferUtils.decode(wa1);
        logWorkArea("1", actualW1);
        String actualW2 = ByteBufferUtils.decode(wa2);
        logWorkArea("2", actualW2);
        String returnCode = ByteBufferUtils.getReturnCode(actualW1);
        logReturnCode(conf, "ByteBuffer", returnCode);
        logByteBuffer("1", wa1);
        logByteBuffer("2", wa2);
        // wa1.clear();
        // wa2.clear();
        assertNotNull(actualW2);
        assertFalse(actualW2.isEmpty());
        assertTrue(ByteBufferUtils.isSuccess(returnCode),
                String.format("Return code from function {} should indicate success", conf.getFunctionName()));
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getFixtures")
    void testCallgeoWithByteArrays(TestConfig conf) {
        logFunctionCall(conf, "byte[]");
        byte[] wa1 = conf.getWorkAreaOneBytes();
        byte[] wa2 = conf.getWorkAreaTwoBytes();
        geoclientJni.callgeo(wa1, 0, wa2, 0);
        String actualW1 = ByteBufferUtils.decode(wa1);
        logWorkArea("1", actualW1);
        String actualW2 = ByteBufferUtils.decode(wa2);
        logWorkArea("2", actualW2);
        String returnCode = ByteBufferUtils.getReturnCode(actualW1);
        logReturnCode(conf, "byte[]", returnCode);
        assertNotNull(actualW2);
        assertFalse(actualW2.isEmpty());
        assertTrue(ByteBufferUtils.isSuccess(returnCode),
                String.format("Return code from function {} should indicate success", conf.getFunctionName()));
    }

    private String lpad(String s) {
        return String.format("%-5s", String.format("[F%s]", s));
    }

    private void logFunctionCall(TestConfig conf, String message) {
        int i = 0;
        int len = message.length() + 5 + 7 + 2;
        StringBuffer sb = new StringBuffer();
        while (i < len) {
            sb.append("-");
            i++;
        }
        logger.debug(sb.toString());
        logger.debug("{} {} request", lpad(conf.getFunctionName()), message);
    }

    private void logReturnCode(TestConfig conf, String message, String returnCode) {
        logger.debug("{} resp:{} {}", lpad(conf.getFunctionName()), returnCode, message);
    }

    private void logWorkArea(String workAreaName, String workAreaData) {
        logger.debug("[WA{}]:<{}>", workAreaName, workAreaData);
    }

    private void logByteBuffer(String workAreaName, ByteBuffer buffer) {
        logger.trace("[WA{}]: ByteBuffer[capacity: {}, position: {}, limit: {}]", workAreaName, buffer.capacity(),
                buffer.position(), buffer.limit());
    }
}