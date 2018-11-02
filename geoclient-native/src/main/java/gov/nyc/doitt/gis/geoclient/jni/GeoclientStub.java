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
package gov.nyc.doitt.gis.geoclient.jni;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;


public class GeoclientStub implements Geoclient
{
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final CharsetDecoder DECODER = CHARSET.newDecoder();

	@Override
	public void callgeo(ByteBuffer work_area1, ByteBuffer work_area2)
	{
		try
		{
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

	private void display(ByteBuffer buffer) throws Exception
	{
		int position = buffer.position();
		CharBuffer charBuffer = DECODER.decode(buffer);
		System.out.println(String.format("-->%s<--", charBuffer.toString()));
		buffer.position(position);
	}

}
