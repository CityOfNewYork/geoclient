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
package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileInfoTest
{
    private FileInfo fileInfo;

    @BeforeEach
    public void setUp() throws Exception
    {
        this.fileInfo = new FileInfo();
    }

    @Test
    public void testGetFormattedDate()
    {
        String dateString = "021112";
        this.fileInfo.setDate(dateString);
        assertEquals(this.fileInfo.applyFormat(dateString),this.fileInfo.getFormattedDate());
    }

    @Test
    public void testApplyFormat()
    {
        assertNull(this.fileInfo.applyFormat(null));
        String badDate = "bad";
        assertEquals(badDate,this.fileInfo.applyFormat(badDate));
        assertEquals("2014-01-21",this.fileInfo.applyFormat("140121"));
    }

}
