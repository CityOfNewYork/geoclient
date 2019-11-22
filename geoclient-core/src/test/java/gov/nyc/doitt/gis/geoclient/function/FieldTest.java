/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldTest
{
	private ByteBuffer buffer;
	private Field field;

	@BeforeEach
	public void setUp() throws Exception
	{
		buffer = ByteBuffer.allocate(8);
		field = new Field("Frank", 2, 4);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		buffer.clear();
	}
	
	@Test
	public void testRead_withSignificantWhitespaceButEmptyWithSpaces()
	{
		Field aField = new Field("abc", 2, 6, false,false,null,true);
		buffer.position(0);
		//String s = "12 456  ";
		byte[] bytes = new byte[]{'1','2',' ',' ',' ',' ',' ',' '};
		buffer.put(bytes);
		String result = aField.read(buffer);
		assertEquals("",result);
	}

	@Test
	public void testRead_withSignificantWhitespaceButEmptyWithNull()
	{
		Field aField = new Field("abc", 2, 6, false,false,null,true);
		buffer.position(0);
		//String s = "12 456  ";
		byte[] bytes = new byte[]{'1','2','\u0000','\u0000','\u0000','\u0000','\u0000','\u0000'};
		buffer.put(bytes);
		String result = aField.read(buffer);
		assertEquals("",result);
	}

	@Test
	public void testRead_withSignificantWhitespace()
	{
		Field aField = new Field("abc", 2, 6, false,false,null,true);
		buffer.position(0);
		String s = "12 456  ";
		buffer.put(s.getBytes());
		String result = aField.read(buffer);
		assertEquals(" 456  ",result);
	}

	@Test
	public void testRead_withoutSignificantWhitespace()
	{
		Field aField = new Field("abc", 2, 6);
		buffer.position(0);
		String s = "12 456  ";
		buffer.put(s.getBytes());
		String result = aField.read(buffer);
		assertEquals("456",result);
	}

	@Test
	public void testRead_notEmpty()
	{
		buffer.position(0);
		String s = "12345678";
		buffer.put(s.getBytes());
		String result = field.read(buffer);
		assertEquals("3456",result);
	}

	@Test
	public void testRead_empty()
	{
		buffer.position(0);
		String s = "        ";
		buffer.put(s.getBytes());
		String result = field.read(buffer);
		assertEquals("",result);
	}

	@Test
	public void testConstructor_idStartLength()
	{
		assertEquals("Frank", field.getId());
		assertEquals(new Integer(2), field.getStart());
		assertEquals(new Integer(4), field.getLength());
		assertFalse(field.isComposite());
		assertFalse(field.isInput());
		assertFalse(field.isAliased());
		assertFalse(field.isWhitespaceSignificant());
	}

	@Test
	public void testConstructor_idStartLengthComposite()
	{
		Field compositeField = new Field("Frank", 2, 4, true);
		assertEquals("Frank", compositeField.getId());
		assertEquals(new Integer(2), compositeField.getStart());
		assertEquals(new Integer(4), compositeField.getLength());
		assertTrue(compositeField.isComposite());
		assertFalse(compositeField.isInput());
		assertFalse(compositeField.isAliased());
		assertFalse(compositeField.isWhitespaceSignificant());
	}

	@Test
	public void testConstructor_idStartLengthCompositeInputAlias()
	{
		Field aField = new Field("Frank", 2, 4, false,true,"Doug");
		assertEquals("Frank", aField.getId());
		assertEquals(new Integer(2), aField.getStart());
		assertEquals(new Integer(4), aField.getLength());
		assertFalse(aField.isComposite());
		assertTrue(aField.isInput());
		assertTrue(aField.isAliased());
		assertEquals("Doug", aField.getAlias());
		assertFalse(aField.isWhitespaceSignificant());
	}

	@Test
	public void testConstructor_idStartLengthCompositeInputAliasWhitespace()
	{
		Field aField = new Field("Frank", 2, 4, false,true,"Doug",true);
		assertEquals("Frank", aField.getId());
		assertEquals(new Integer(2), aField.getStart());
		assertEquals(new Integer(4), aField.getLength());
		assertFalse(aField.isComposite());
		assertTrue(aField.isInput());
		assertTrue(aField.isAliased());
		assertEquals("Doug", aField.getAlias());
		assertTrue(aField.isWhitespaceSignificant());
	}


	
	@Test
	public void testWrite_withParams() throws Exception
	{
		field.write("abc", buffer);
		byte[] expectedBytes = new byte[8];
		expectedBytes[0] = (byte) '\u0000';
		expectedBytes[1] = (byte) '\u0000';
		expectedBytes[2] = (byte) 'a'; // Start=2, Length=4
		expectedBytes[3] = (byte) 'b';
		expectedBytes[4] = (byte) 'c';
		expectedBytes[5] = (byte) ' ';
		expectedBytes[6] = (byte) '\u0000';
		expectedBytes[7] = (byte) '\u0000';
		byte[] actualBytes = new byte[8];
		buffer.position(0);
		buffer.get(actualBytes);
		assertEquals(new String(expectedBytes), new String(actualBytes));
	}

	@Test
	public void testWrite_withoutParams()
	{
		field.write(buffer);
		byte[] expectedBytes = new byte[8];
		expectedBytes[0] = (byte) '\u0000';
		expectedBytes[1] = (byte) '\u0000';
		expectedBytes[2] = (byte) ' '; // Start=2, Length=4
		expectedBytes[3] = (byte) ' ';
		expectedBytes[4] = (byte) ' ';
		expectedBytes[5] = (byte) ' ';
		expectedBytes[6] = (byte) '\u0000';
		expectedBytes[7] = (byte) '\u0000';
		byte[] actualBytes = new byte[8];
		buffer.position(0);
		buffer.get(actualBytes);
		assertEquals(new String(expectedBytes), new String(actualBytes));
	}

	@Test
	public void testGetBytes_inputWithExtraChars()
	{
		// input > field length
		byte[] actualBytes = field.getBytes("abcdefg");
		assertEquals("abcd", new String(actualBytes));
		// input == field length
		actualBytes = field.getBytes("abcd");
		assertEquals("abcd", new String(actualBytes));
		// input < field length
		actualBytes = field.getBytes("ab");
		assertEquals("ab  ", new String(actualBytes));
	}

	@Test
	public void testGetBytes_null()
	{
		byte[] actualBytes = field.getBytes(null);
		assertEquals("    ", new String(actualBytes));
	}

	@Test
	public void testGetBytes_emptyString()
	{
		byte[] actualBytes = field.getBytes("");
		assertEquals("    ", new String(actualBytes));
	}

	@Test
	public void testCompareTo()
	{
		// field == Field [name='Frank', start=2, length=4]
		String baseName = field.getId();
		int baseStart = field.getStart();
		int baseLength = field.getLength();
		Field fieldIsEqual = new Field(baseName, baseStart, baseLength);
		assertTrue(field.compareTo(fieldIsEqual)==0);
		assertTrue(fieldIsEqual.compareTo(field)==0);
		assertTrue(field.compareTo(field)==0);
		
		Field startIsLess = new Field(baseName, baseStart - 1, baseLength);
		assertTrue(field.compareTo(startIsLess)>0);
		assertTrue(startIsLess.compareTo(field)<0);

		Field startIsMore = new Field(baseName, baseStart + 1, baseLength);
		assertTrue(field.compareTo(startIsMore)<0);
		assertTrue(startIsMore.compareTo(field)>0);

		Field lengthIsLess = new Field(baseName, baseStart, baseLength - 1);
		assertTrue(field.compareTo(lengthIsLess)>0);
		assertTrue(lengthIsLess.compareTo(field)<0);
		
		Field lengthIsMore = new Field(baseName, baseStart, baseLength + 1);
		assertTrue(field.compareTo(lengthIsMore)<0);
		assertTrue(lengthIsMore.compareTo(field)>0);

		Field onlyNameIsDifferent = new Field("A"+baseName, baseStart, baseLength);
		assertTrue(field.compareTo(onlyNameIsDifferent)==0);
	}

}
