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
package gov.nyc.doitt.gis.geoclient.parser.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ParserConfig.class})
public class PatternDataTest{

    @Autowired
    private ParserConfig patternData;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testBoroughNames()
    {
        assertTrue(applicationContext.containsBean("boroughNamesToBoroughMap"));
        assertNotNull(patternData);
        assertFalse(patternData.boroughNamesToBoroughMap().isEmpty());
    }

    @Test
    public void testCityNames()
    {
        assertTrue(applicationContext.containsBean("cityNamesToBoroughMap"));
        assertNotNull(patternData);
        assertFalse(patternData.cityNamesToBoroughMap().isEmpty());
    }
}
