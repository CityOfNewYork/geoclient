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
package gov.nyc.doitt.gis.geoclient.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;

public class GeosupportConfigIntegrationTest {
    static final Logger logger = LoggerFactory.getLogger(GeosupportConfigIntegrationTest.class);
    protected static GeosupportConfig geosupportConfig;

    @BeforeAll
    public static void beforeAll() throws Exception {
        logger.info("java.library.path={}", System.getProperty("java.library.path"));
        geosupportConfig = new GeosupportConfig(new GeoclientJni());
    }

    @Test
    public void nonExistentFunction() {
        Throwable exception = assertThrows(UnknownFunctionException.class, () -> {
            geosupportConfig.getFunction("fun");
        });
        assertEquals("Unknown function id 'fun'", exception.getMessage());
    }

    @Test
    public void testGetFunction() {
        assertNotNull(geosupportConfig.getFunction(Function.FAP));
        assertNotNull(geosupportConfig.getFunction(Function.F1));
        assertNotNull(geosupportConfig.getFunction(Function.F1E));
        assertNotNull(geosupportConfig.getFunction(Function.F1A));
        assertNotNull(geosupportConfig.getFunction(Function.F1AX));
        assertNotNull(geosupportConfig.getFunction(Function.F1B));
        assertNotNull(geosupportConfig.getFunction(Function.FBL));
        assertNotNull(geosupportConfig.getFunction(Function.FBN));
        assertNotNull(geosupportConfig.getFunction(Function.F2));
        assertNotNull(geosupportConfig.getFunction(Function.F3));
        assertNotNull(geosupportConfig.getFunction(Function.FDG));
        assertNotNull(geosupportConfig.getFunction(Function.FHR));
        assertNotNull(geosupportConfig.getFunction(Function.FN));        
    }

    @Test
    public void testGetFunctionConfiguration() {
        assertNotNull(geosupportConfig.getFunction(Function.FAP).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1E).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1A).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1AX).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F1B).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FBL).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FBN).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F2).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F2W).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.F3).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FDG).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FHR).getConfiguration());
        assertNotNull(geosupportConfig.getFunction(Function.FN).getConfiguration());
    }

    @Test
    public void testItemDocumentationDisplayNameSetProperly() {
        assertItemDocumentationDisplayNames(geosupportConfig.getFunctionDocumentation(Function.F1B),
                Arrays.asList("listOfSecondSetOf5Lgcs"));
        assertItemDocumentationDisplayNames(geosupportConfig.getFunctionDocumentation(Function.F2),
                Arrays.asList("dcpPreferredLgcForStreet1"));
    }

    private void assertItemDocumentationDisplayNames(FunctionDocumentation functionDocumentation, List<String> names) {
        for (String name : names) {
            assertTrue(containsItemDocumentationWithDisplayName(name, functionDocumentation.getFields()), String.format(
                    "%s is missing expected ItemDocumentation with displayName='%s'", functionDocumentation, name));
        }
    }

    private boolean containsItemDocumentationWithDisplayName(String displayName, SortedSet<ItemDocumentation> items) {
        for (ItemDocumentation itemDocumentation : items) {
            if (displayName.equals(itemDocumentation.getDisplayName())) {
                return true;
            }
        }
        return false;
    }

}
