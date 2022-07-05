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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkAreaTest
{
    private SortedSet<Field> fields;
    private Field fieldOne;
    private Field fieldTwo;
    private Field fieldComposite;
    private Field fieldThree;
    private Field fieldFour;
    private Field fieldFourDuplicate;
    private Field fieldFive;
    private List<Filter> outputFilters;
    private WorkArea workArea;
    private WorkArea workAreaWithFilters;

    @BeforeEach
    public void setUp() throws Exception
    {
        //     non-composite work area bytes: 122333445555
        //         composite work area bytes: _CCCCC______  (fieldComposite)
        //             input work area bytes: 1CCCCC44____
        //            output work area bytes: ________5555  (fieldFive)
        // ignored duplicate work area bytes: ______II____  (fieldFourDuplicate)
        fieldOne = new Field("fieldOne", 0, 1,false,true,"aliasForFieldOne");
        fieldTwo = new Field("fieldTwo", 1, 2,false,true,null);
        fieldComposite = new Field("fieldComposite", 1, 5,true,true,"aliasForFieldComposite");
        fieldThree = new Field("fieldThree", 3, 3,false,true,"aliasForFieldThree");
        fieldFour = new Field("fieldFour", 6, 2,false,true,null);
        fieldFourDuplicate = new Field("fieldFourDuplicate", 6, 2,false,true,null);
        fieldFive = new Field("fieldFive", 8, 4,false,false,"aliasForFieldFive");
        fields = new TreeSet<Field>();
        fields.add(fieldOne);
        fields.add(fieldTwo);
        fields.add(fieldThree);
        fields.add(fieldComposite);
        fields.add(fieldFour);
        fields.add(fieldFourDuplicate);
        fields.add(fieldFive);
        // Make sure the set didn't exclude anything besides duplicate
        assertEquals(6, fields.size());
        workArea = new WorkArea("MyWorkArea",fields);
        this.outputFilters = new ArrayList<Filter>();
        this.outputFilters.add(new Filter(fieldOne.getId()));
        this.outputFilters.add(new Filter(fieldThree.getId()));
        workAreaWithFilters = new WorkArea("waWithFilters", this.fields, this.outputFilters);
    }

    @Test
    public void testIsFiltered() throws Exception
    {
        assertFalse(workArea.isFiltered(fieldOne));
        assertFalse(workArea.isFiltered(fieldTwo));
        assertFalse(workArea.isFiltered(fieldThree));
        assertTrue(workAreaWithFilters.isFiltered(fieldOne));
        assertFalse(workAreaWithFilters.isFiltered(fieldTwo));
        assertTrue(workAreaWithFilters.isFiltered(fieldThree));
    }

    @Test
    public void testResolveInputValue() throws Exception
    {
        Map<String, Object> params = new HashMap<String, Object>();
        assertNull(workArea.resolveInputValue(params, fieldOne));
        params.put(fieldOne.getId(), "X");
        assertEquals("X",workArea.resolveInputValue(params, fieldOne));
        params.put(fieldOne.getAlias(), "Y");
        assertEquals("Y",workArea.resolveInputValue(params, fieldOne));
        params.clear();
        params.put(fieldFive.getId(),"Q");
        assertFalse(fieldFive.isInput());
        assertNull(workArea.resolveInputValue(params, fieldFive));
    }

    @Test
    public void testGetFieldIds_default()
    {
        assertEquals(Arrays.asList("fieldTwo","fieldComposite","fieldFour","fieldFive"), workAreaWithFilters.getFieldIds());
        assertEquals(workAreaWithFilters.getFieldIds(null,false,true), workAreaWithFilters.getFieldIds());
        assertEquals(workAreaWithFilters.getFieldIds(Field.DEFAULT_SORT,false,true), workAreaWithFilters.getFieldIds());
    }

    @Test
    public void testGetFieldIds_withComparator()
    {
        assertEquals(Arrays.asList("fieldComposite","fieldFive","fieldFour","fieldOne","fieldThree","fieldTwo"), workAreaWithFilters.getFieldIds(Field.NAME_SORT,true, true));
        assertEquals(Arrays.asList("fieldFive"), workAreaWithFilters.getFieldIds(Field.NAME_SORT,true, false));
        assertEquals(Arrays.asList("fieldComposite","fieldFive","fieldFour","fieldTwo"), workAreaWithFilters.getFieldIds(Field.NAME_SORT,false, true));
        assertEquals(Arrays.asList("fieldFive"), workAreaWithFilters.getFieldIds(Field.NAME_SORT,false, false));
    }

    @Test
    public void testConstructorAndFindField()
    {
        assertEquals("MyWorkArea",workArea.getId());
        assertSame(fieldOne, workArea.findField(fieldOne.getId()));
        assertSame(fieldTwo, workArea.findField(fieldTwo.getId()));
        assertSame(fieldThree, workArea.findField(fieldThree.getId()));
        assertNull(workArea.findField("dilbert"));
    }

    @Test
    public void testCreateBuffer_withParamsNoAliasesInInput()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("random", "xxxxxx");
        params.put(fieldOne.getId(), generateValue(fieldOne, "1", 0));
        params.put(fieldTwo.getId(), generateValue(fieldTwo, "2", 0));
        params.put(fieldComposite.getId(), generateValue(fieldComposite, "C", 0));
        params.put(fieldThree.getId(), generateValue(fieldThree, "3", 0));
        params.put(fieldFour.getId(), generateValue(fieldFour, "4", 0));
        params.put(fieldFive.getId(), generateValue(fieldFive, "5", 0));
        ByteBuffer buffer = workArea.createBuffer(params);
        // fieldComposite will be written after fieldTwo and will overwrite it
        assertCreateBuffer(buffer, "1CC33344    ");
    }

    @Test
    public void testCreateBuffer_withParamsAliasesInInput()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        // Put paramters for both fieldOne's id and alias; alias should
        // take precedence
        params.put(fieldOne.getId(), generateValue(fieldOne, "1", 0));
        params.put(fieldOne.getAlias(), generateValue(fieldOne, "A", 0));
        params.put(fieldTwo.getId(), generateValue(fieldTwo, "2", 0));
        params.put(fieldComposite.getAlias(), generateValue(fieldComposite, "C", 0));
        params.put(fieldThree.getAlias(), generateValue(fieldThree, "3", 0));
        params.put(fieldFour.getId(), generateValue(fieldFour, "4", 0));
        params.put(fieldFive.getAlias(), generateValue(fieldFive, "5", 0));
        ByteBuffer buffer = workArea.createBuffer(params);
        // fieldOne will be written with the aliased parameter and not by id
        // fieldComposite will be written after fieldTwo and will overwrite it
        assertCreateBuffer(buffer, "ACC33344    ");
    }

    @Test
    public void testCreateBuffer_withoutParams()
    {
        ByteBuffer buffer = workArea.createBuffer();
        assertCreateBuffer(buffer, "            ");
    }

    @Test
    public void testParseResults()
    {
        String inputString = "abcdefghijklmnopq";
        ByteBuffer buffer = ByteBuffer.allocate(inputString.length());
        buffer.put(inputString.getBytes());
        Map<String, Object> result = workArea.parseResults(buffer);
        // Field one is IN and should not be in the result
        assertEquals("a", result.get(fieldOne.getId()));
        assertEquals("bc", result.get(fieldTwo.getId()));
        assertEquals("def", result.get(fieldThree.getId()));
        assertEquals("bcdef", result.get(fieldComposite.getId()));
    }

    @Test
    public void testParseResults_withFilters()
    {
        String inputString = "abcdefghijklmnopq";
        ByteBuffer buffer = ByteBuffer.allocate(inputString.length());
        buffer.put(inputString.getBytes());
        Map<String, Object> result = workAreaWithFilters.parseResults(buffer);
        // Field one is IN and should not be in the result
        assertNull(result.get(fieldOne.getId()));
        assertEquals("bc", result.get(fieldTwo.getId()));
        assertNull(result.get(fieldThree.getId()));
        assertEquals("bcdef", result.get(fieldComposite.getId()));
    }


    @Test
    public void testLength()
    {
        // Excludes fieldComposite and fieldFourDuplicate
        assertEquals(
                (fieldOne.getLength() +
                 fieldTwo.getLength() +
                 fieldThree.getLength() +
                 fieldFour.getLength() +
                 fieldFive.getLength()), workArea.length());
    }

    private void assertCreateBuffer(ByteBuffer buffer, String expectedValue)
    {
        int bufferLength = workArea.length();
        // Buffer capacity should equal workArea length
        assertEquals(bufferLength, buffer.capacity());
        // Buffer limit should equal workArea length
        assertEquals(bufferLength, buffer.limit());
        // Internal call to ByteBuffer#flip should have moved position back to 0
        assertEquals(0, buffer.position());
        byte[] actualBytes = new byte[bufferLength];
        buffer.get(actualBytes);
        assertEquals(expectedValue, new String(actualBytes));

    }

    private String generateValue(Field field, String value, int extra)
    {
        StringBuffer buffer = new StringBuffer();
        int size = field.getLength() + extra;
        for (int i = 0; i < size; i++)
        {
            buffer.append(value);
        }
        return buffer.toString();
    }

}
