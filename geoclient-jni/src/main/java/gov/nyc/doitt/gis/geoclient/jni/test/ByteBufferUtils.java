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
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class ByteBufferUtils {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final CharsetEncoder ENCODER = CHARSET.newEncoder();
    private static final CharsetDecoder DECODER = CHARSET.newDecoder();
    private static final String GEOSUPPORT_RC_SUCCESS = "00";
    private static final String GEOSUPPORT_RC_SUCCESS_WITH_WARN = "01";
    private static final int GEOSUPPORT_RC_LENGTH = 2;
    private static final int GEOSUPPORT_RC_START = 716;

    public static String decode(ByteBuffer buffer) throws CharacterCodingException {
        return DECODER.decode(buffer).toString();
    }

    public static String decode(byte[] buffer) {
        return new String(buffer);
    }

    public void encode(CharBuffer charBuffer, ByteBuffer buffer) throws CharacterCodingException {
        CoderResult result = ENCODER.encode(charBuffer, buffer, true);
        if (result.isError()) {
            result.throwException();
        }
    }

    public static String getReturnCode(String workAreaOneResult) {
        return workAreaOneResult.substring(GEOSUPPORT_RC_START, GEOSUPPORT_RC_START + GEOSUPPORT_RC_LENGTH);
    }

    public static boolean isSuccess(String returnCode) {
        if (isNotNullOrEmpty(returnCode)) {
            return GEOSUPPORT_RC_SUCCESS.equals(returnCode) || GEOSUPPORT_RC_SUCCESS_WITH_WARN.equals(returnCode);
        }
        return false;
    }

    private static boolean isNotNullOrEmpty(String s) {
        return s != null && s.length() > 0;
    }

}