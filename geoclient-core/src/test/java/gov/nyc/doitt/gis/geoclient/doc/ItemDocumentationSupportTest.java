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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemDocumentationSupportTest
{
    private ItemDocumentationSupport support;
    private ItemDocumentation itemDocumentation;

    @BeforeEach
    public void setUp() throws Exception
    {
        support = new ItemDocumentationSupport();
        itemDocumentation = new ItemDocumentation();
    }

    @Test
    public void testHasItemDocumentation()
    {
        assertFalse(support.hasItemDocumentation());
        support.setItemDocumentation(itemDocumentation);
        assertFalse(support.hasItemDocumentation());
        itemDocumentation.setId("foo");
        assertTrue(support.hasItemDocumentation());
    }

    @Test
    public void testGetItemDocumentationId()
    {
        assertNull(support.getItemDocumentationId());
        support.setItemDocumentation(itemDocumentation);
        assertNull(support.getItemDocumentationId());
        itemDocumentation.setId("foo");
        assertEquals("foo",support.getItemDocumentationId());
    }

}
