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
        this.buffer = ByteBuffer.allocate(8);
        this.field = new Field("Frank", 2, 4);
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        this.buffer.clear();
    }

    @Test
    public void testRead_withSignificantWhitespaceButEmptyWithSpaces()
    {
        Field aField = new Field("abc", 2, 6, false,false,null,true);
        this.buffer.position(0);
        //String s = "12 456  ";
        byte[] bytes = new byte[]{'1','2',' ',' ',' ',' ',' ',' '};
        this.buffer.put(bytes);
        String result = aField.read(this.buffer);
        assertEquals("",result);
    }

    @Test
    public void testRead_withSignificantWhitespaceButEmptyWithNull()
    {
        Field aField = new Field("abc", 2, 6, false,false,null,true);
        this.buffer.position(0);
        //String s = "12 456  ";
        byte[] bytes = new byte[]{'1','2','\u0000','\u0000','\u0000','\u0000','\u0000','\u0000'};
        this.buffer.put(bytes);
        String result = aField.read(this.buffer);
        assertEquals("",result);
    }

    @Test
    public void testRead_withSignificantWhitespace()
    {
        Field aField = new Field("abc", 2, 6, false,false,null,true);
        this.buffer.position(0);
        String s = "12 456  ";
        this.buffer.put(s.getBytes());
        String result = aField.read(this.buffer);
        assertEquals(" 456  ",result);
    }

    @Test
    public void testRead_withoutSignificantWhitespace()
    {
        Field aField = new Field("abc", 2, 6);
        this.buffer.position(0);
        String s = "12 456  ";
        this.buffer.put(s.getBytes());
        String result = aField.read(this.buffer);
        assertEquals("456",result);
    }

    @Test
    public void testRead_notEmpty()
    {
        this.buffer.position(0);
        String s = "12345678";
        this.buffer.put(s.getBytes());
        String result = this.field.read(this.buffer);
        assertEquals("3456",result);
    }

    @Test
    public void testRead_empty()
    {
        this.buffer.position(0);
        String s = "        ";
        this.buffer.put(s.getBytes());
        String result = this.field.read(this.buffer);
        assertEquals("",result);
    }

    @Test
    public void testConstructor_idStartLength()
    {
        assertEquals("Frank", this.field.getId());
        assertEquals(2, this.field.getStart());
        assertEquals(4, this.field.getLength());
        assertFalse(this.field.isComposite());
        assertFalse(this.field.isInput());
        assertFalse(this.field.isAliased());
        assertFalse(this.field.isWhitespaceSignificant());
        assertFalse(this.field.isOutputAliased());
    }

    @Test
    public void testConstructor_idStartLengthComposite()
    {
        Field compositeField = new Field("Frank", 2, 4, true);
        assertEquals("Frank", compositeField.getId());
        assertEquals(2, compositeField.getStart());
        assertEquals(4, compositeField.getLength());
        assertTrue(compositeField.isComposite());
        assertFalse(compositeField.isInput());
        assertFalse(compositeField.isAliased());
        assertFalse(compositeField.isWhitespaceSignificant());
        assertFalse(compositeField.isOutputAliased());
    }

    @Test
    public void testConstructor_idStartLengthCompositeInputAlias()
    {
        Field aField = new Field("Frank", 2, 4, false,true,"Doug");
        assertEquals("Frank", aField.getId());
        assertEquals(2, aField.getStart());
        assertEquals(4, aField.getLength());
        assertFalse(aField.isComposite());
        assertTrue(aField.isInput());
        assertTrue(aField.isAliased());
        assertEquals("Doug", aField.getAlias());
        assertFalse(aField.isWhitespaceSignificant());
        assertFalse(this.field.isOutputAliased());
    }

    @Test
    public void testConstructor_idStartLengthCompositeInputAliasWhitespace()
    {
        Field aField = new Field("Frank", 2, 4, false,true,"Doug",true);
        assertEquals("Frank", aField.getId());
        assertEquals(2, aField.getStart());
        assertEquals(4, aField.getLength());
        assertFalse(aField.isComposite());
        assertTrue(aField.isInput());
        assertTrue(aField.isAliased());
        assertEquals("Doug", aField.getAlias());
        assertTrue(aField.isWhitespaceSignificant());
        assertFalse(this.field.isOutputAliased());
    }

    @Test
    public void testConstructor_idStartLengthCompositeInputAliasWhitespaceOutputAlias()
    {
        Field aField = new Field("Frank", 2, 4, false,true,"Doug",true, "Fraunk");
        assertEquals("Frank", aField.getId());
        assertEquals(2, aField.getStart());
        assertEquals(4, aField.getLength());
        assertFalse(aField.isComposite());
        assertTrue(aField.isInput());
        assertTrue(aField.isAliased());
        assertEquals("Doug", aField.getAlias());
        assertTrue(aField.isWhitespaceSignificant());
        assertTrue(aField.isOutputAliased());
        assertEquals("Fraunk", aField.getOutputAlias());
    }



    @Test
    public void testWrite_withParams() throws Exception
    {
        this.field.write("abc", this.buffer);
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
        this.buffer.position(0);
        this.buffer.get(actualBytes);
        assertEquals(new String(expectedBytes), new String(actualBytes));
    }

    @Test
    public void testWrite_withoutParams()
    {
        this.field.write(this.buffer);
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
        this.buffer.position(0);
        this.buffer.get(actualBytes);
        assertEquals(new String(expectedBytes), new String(actualBytes));
    }

    @Test
    public void testGetBytes_inputWithExtraChars()
    {
        // input > field length
        byte[] actualBytes = this.field.getBytes("abcdefg");
        assertEquals("abcd", new String(actualBytes));
        // input == field length
        actualBytes = this.field.getBytes("abcd");
        assertEquals("abcd", new String(actualBytes));
        // input < field length
        actualBytes = this.field.getBytes("ab");
        assertEquals("ab  ", new String(actualBytes));
    }

    @Test
    public void testGetBytes_null()
    {
        byte[] actualBytes = this.field.getBytes(null);
        assertEquals("    ", new String(actualBytes));
    }

    @Test
    public void testGetBytes_emptyString()
    {
        byte[] actualBytes = this.field.getBytes("");
        assertEquals("    ", new String(actualBytes));
    }

    @Test
    public void testCompareTo()
    {
        // field == Field [name='Frank', start=2, length=4]
        String baseName = this.field.getId();
        int baseStart = this.field.getStart();
        int baseLength = this.field.getLength();
        Field fieldIsEqual = new Field(baseName, baseStart, baseLength);
        assertTrue(this.field.compareTo(fieldIsEqual)==0);
        assertTrue(fieldIsEqual.compareTo(this.field)==0);
        assertTrue(this.field.compareTo(this.field)==0);

        Field startIsLess = new Field(baseName, baseStart - 1, baseLength);
        assertTrue(this.field.compareTo(startIsLess)>0);
        assertTrue(startIsLess.compareTo(this.field)<0);

        Field startIsMore = new Field(baseName, baseStart + 1, baseLength);
        assertTrue(this.field.compareTo(startIsMore)<0);
        assertTrue(startIsMore.compareTo(this.field)>0);

        Field lengthIsLess = new Field(baseName, baseStart, baseLength - 1);
        assertTrue(this.field.compareTo(lengthIsLess)>0);
        assertTrue(lengthIsLess.compareTo(this.field)<0);

        Field lengthIsMore = new Field(baseName, baseStart, baseLength + 1);
        assertTrue(this.field.compareTo(lengthIsMore)<0);
        assertTrue(lengthIsMore.compareTo(this.field)>0);

        Field onlyNameIsDifferent = new Field("A"+baseName, baseStart, baseLength);
        assertTrue(this.field.compareTo(onlyNameIsDifferent)==0);
    }

}
