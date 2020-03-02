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
package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DescriptionTest
{
    private List<Paragraph> paragraphs = new ArrayList<Paragraph>();
    
    @AfterEach
    public void teaDown()
    {
        this.paragraphs.clear();
    }
    
    @Test
    public void testHasText()
    {
        Description desc = new Description();
        desc.setParagraphs(null);
        assertFalse(desc.hasText());
        desc.setParagraphs(paragraphs);
        assertFalse(desc.hasText());    
        Paragraph p = new Paragraph();
        assertFalse(p.hasText());
        paragraphs.add(p);
        assertFalse(desc.hasText());    
        p.setText("x");
        assertTrue(p.hasText());
        assertTrue(desc.hasText()); 
    }

    @Test
    public void testSummarize()
    {
        Description desc = new Description();
        assertTrue(desc.summarize(4).isEmpty());
        desc.getParagraphs().add(new Paragraph("12"));
        assertEquals("12",desc.summarize(4));
        desc.getParagraphs().add(new Paragraph("3456"));
        assertEquals("12 3",desc.summarize(4));
    }   
    
    @Test
    public void testJoinText()
    {
        Description desc = new Description();
        assertTrue(desc.joinText(", ").isEmpty());
        desc.getParagraphs().add(new Paragraph("one"));
        assertEquals("one", desc.joinText(", "));
        desc.getParagraphs().add(new Paragraph("two"));
        assertEquals("one, two", desc.joinText(", "));
    }
    
    @Test
    public void testJoinTextWithNullParagraphsList()
    {
        Description desc = new Description();
        desc.setParagraphs(null);
        assertTrue(desc.joinText(", ").isEmpty());
    }
    
    @Test
    public void testNoArgConstructor()
    {
        Description desc = new Description();
        assertNotNull(desc.getParagraphs());
        assertTrue(desc.getParagraphs().isEmpty());
    }

    @Test
    public void testStringConstructor()
    {
        String txt = "Pass the beans";
        Description desc = new Description(txt);
        assertNotNull(desc.getParagraphs());
        assertTrue(desc.getParagraphs().size()==1);
        assertEquals(txt, desc.getParagraphs().get(0).getText());
    }

    @Test
    public void testListOfParagraphsConstructor()
    {
        Description desc = new Description(paragraphs);
        assertSame(this.paragraphs,desc.getParagraphs());
    }

    @Test
    public void testToHtml()
    {
        Description desc = new Description(paragraphs);
        assertEquals("",desc.toHtml());
        Paragraph paragraphOne = new Paragraph("Blah, blah, blah");
        this.paragraphs.add(paragraphOne);
        assertEquals("<p>Blah, blah, blah</p>",desc.toHtml());
        Paragraph paragraphTwo = new Paragraph("Once upon a time");
        this.paragraphs.add(paragraphTwo);
        assertEquals("<p>Blah, blah, blah</p><p>Once upon a time</p>",desc.toHtml());
    }

}
