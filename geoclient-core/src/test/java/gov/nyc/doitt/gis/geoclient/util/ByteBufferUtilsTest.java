package gov.nyc.doitt.gis.geoclient.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ByteBufferUtilsTest
{

	@Test
	public void testReadString()throws Exception
	{
		String string = "I like cake";
		ByteBuffer buffer = ByteBuffer.wrap(string.getBytes());
		int positionBeforeCall = buffer.position();
		assertEquals(string, ByteBufferUtils.readString(buffer));
		assertTrue(positionBeforeCall==buffer.position());
	}

}
