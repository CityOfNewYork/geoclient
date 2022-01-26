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
package gov.nyc.doitt.gis.geoclient.config.xml;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.config.FunctionConfig;
import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.config.WorkAreaConfig;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;

public class GeoclientXmlReaderIntegrationTest {
    private static GeoclientXmlReader xmlReader;

    @BeforeAll
    public static void setUp() throws Exception {
        xmlReader = GeoclientXmlReader.fromXml(GeosupportConfig.DEFAULT_CONFIG_FILE);
        assertNotNull(xmlReader);
    }

    @Test
    public void testFilters() {
        List<Filter> filters = xmlReader.getFilters();
        for (Filter filter : filters) {
            assertFilter(filter);
        }
    }

    @Test
    public void testFunctionConfig() {
        List<FunctionConfig> functions = xmlReader.getFunctions();
        for (FunctionConfig fConfig : functions) {
            assertFunctionConfig(fConfig);
        }
    }

    @Test
    public void testWorkAreaConfig() {
        boolean foundWorkAreaOne = false;
        List<WorkAreaConfig> workAreas = xmlReader.getWorkAreas();
        for (WorkAreaConfig wConfig : workAreas) {
            if ("WA1".equals(wConfig.getId())) {
                foundWorkAreaOne = true;
                assertWorkAreaConfig(wConfig, true);
            } else {
                assertWorkAreaConfig(wConfig, false);
            }
        }
        assertTrue(foundWorkAreaOne);
    }

    private void assertFilter(Filter filter) {
        assertNotNull(filter);
    }

    private void assertFunctionConfig(FunctionConfig fConfig) {
        String id = fConfig.getId();
        assertNotNull(id);
        assertNotNull(fConfig.getWorkAreaOneConfig());
        if (!Function.FD.equals(id) && !Function.FDG.equals(id) && !Function.FDN.equals(id) && !Function.FBB.equals(id) && !Function.FBF.equals(id) && !Function.FN.equals(id)) {
            assertNotNull(fConfig.getWorkAreaTwoConfig());
        } else {
            assertNull(fConfig.getWorkAreaTwoConfig());
        }
        if (Function.FBL.equals(id)) {
            assertNotNull(fConfig.getConfiguration());
            assertNotNull(fConfig.getConfiguration().requiredArguments());
            assertFalse(fConfig.getConfiguration().requiredArguments().isEmpty());
        }
    }

    private void assertField(Field field) {
        assertNotNull(field);
        assertNotNull(field.getId());
        assertTrue(field.getStart() >= 0);
        assertTrue(field.getLength() > 0);
    }

    private void assertWorkAreaConfig(WorkAreaConfig wConfig, boolean isWorkAreaOne) {
        assertNotNull(wConfig.getId());
        assertTrue(wConfig.getLength() > 0);
        if (isWorkAreaOne) {
            assertTrue(wConfig.isWorkAreaOne());
            List<Field> fields = wConfig.getFields();
            assertFalse(fields.isEmpty());
            for (Field field : fields) {
                assertField(field);
            }
        } else {
            assertFalse(wConfig.isWorkAreaOne());
        }
    }

}
