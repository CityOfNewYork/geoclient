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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

public class GeoclientStub implements Geoclient
{
    final Logger logger = LoggerFactory.getLogger(GeoclientStub.class);
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final CharsetDecoder DECODER = CHARSET.newDecoder();

    private final ConcurrentMap<String, TestConfig> db = new ConcurrentHashMap<>();

    public GeoclientStub() {
        super();
    }

    @Override
    public void callgeo(ByteBuffer work_area1, ByteBuffer work_area2)
    {
        try
        {
            String functionId = extractFunctionId(work_area1);
            logger.debug(String.format("Calling function %s",functionId));
            display(work_area1);
            display(work_area2);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void callgeo(byte[] work_area1, int work_area1_offset, byte[] work_area2, int work_area2_offset)
    {
    }

    public TestConfig add(TestConfig config) {
        return db.put(config.getFunctionName(), config);
    }
    private void display(ByteBuffer buffer) throws Exception
    {
        int position = buffer.position();
        CharBuffer charBuffer = DECODER.decode(buffer);
        logger.debug(String.format("-->%s<--", charBuffer.toString()));
        buffer.position(position);
    }

    private String extractFunctionId(ByteBuffer byteBuffer) throws CharacterCodingException {
        // Duplicate ByteBuffer argument which will create a bi-directional "reference" that will reflect buffer
        // changes, but use independent position, limit and mark values
        ByteBuffer buffer = byteBuffer.duplicate();
        int position = buffer.position();
        CharBuffer charBuffer = DECODER.decode(buffer);
        char [] chars = new char[2];
        charBuffer.get(chars, 0, 2);
        buffer.position(position);
        String result = String.copyValueOf(chars);
        logger.debug(String.format("Function[%s]", result));
        return result;
    }
}
