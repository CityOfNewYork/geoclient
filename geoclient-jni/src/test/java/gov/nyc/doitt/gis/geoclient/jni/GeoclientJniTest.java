package gov.nyc.doitt.gis.geoclient.jni;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.stream.Stream;

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

    static Stream<TestConfig> getFixtures() throws IOException {
        InputStream inputStream = GeoclientJni.class.getClassLoader().getResourceAsStream("test.conf");
        TestFileParser parser = new TestFileParser(inputStream, logger);
        return parser.parse().stream();
    }

    @ParameterizedTest
    @MethodSource("getFixtures")
    void testCallgeoWithByteBuffers(TestConfig conf) throws CharacterCodingException {
        ByteBuffer wa1 = conf.getWorkAreaOne();
        ByteBuffer wa2 = conf.getWorkAreaTwo();
        logger.info("Calling function {}", conf.getFunctionName());
        geoclientJni.callgeo(wa1, wa2);
        String actualW1 = ByteBufferUtils.decode(wa1);
        String returnCode = ByteBufferUtils.getReturnCode(actualW1);
        logger.info(
                String.format("Result of %s call:  geosupportReturnCode = \"%s\"", conf.getFunctionName(), returnCode));
        assertTrue(ByteBufferUtils.isSuccess(returnCode),
                String.format("Return code from function {} should indicate success", conf.getFunctionName()));

        if (wa2 != null) {
            String actualW2 = ByteBufferUtils.decode(wa2);
            assertNotNull(actualW2);
            assertFalse(actualW2.isEmpty());
        }
    }

    @ParameterizedTest
    @MethodSource("getFixtures")
    void testCallgeoByteArrayIntByteArrayInt(TestConfig conf) {
        byte[] wa1 = conf.getWorkAreaOneBytes();
        byte[] wa2 = conf.getWorkAreaTwoBytes();
        logger.info("Calling function {}", conf.getFunctionName());
        geoclientJni.callgeo(wa1, wa1.length, wa2, wa2.length);
        String actualW1 = ByteBufferUtils.decode(wa1);
        String returnCode = ByteBufferUtils.getReturnCode(actualW1);
        logger.info(
                String.format("Result of %s call:  geosupportReturnCode = \"%s\"", conf.getFunctionName(), returnCode));
        assertTrue(ByteBufferUtils.isSuccess(returnCode),
                String.format("Return code from function {} should indicate success", conf.getFunctionName()));
        if (wa2 != null) {
            String actualW2 = ByteBufferUtils.decode(wa2);
            assertNotNull(actualW2);
            assertFalse(actualW2.isEmpty());
        }
    }

}
