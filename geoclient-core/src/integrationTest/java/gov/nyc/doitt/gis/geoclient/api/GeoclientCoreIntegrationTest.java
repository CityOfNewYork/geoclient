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
package gov.nyc.doitt.gis.geoclient.api;

import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.HOUSE_NUMBER;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME;
import static gov.nyc.doitt.gis.geoclient.api.OutputParam.GEOSUPPORT_RETURN_CODE;
import static gov.nyc.doitt.gis.geoclient.api.OutputParam.GEOSUPPORT_RETURN_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue.SUCCESS;
import static gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue.WARN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;
import gov.nyc.doitt.gis.geoclient.util.StringUtils;

public class GeoclientCoreIntegrationTest {

    private static GeosupportConfig geosupportConfig;

    @BeforeAll
    public static void beforeAll() throws Exception {
        geosupportConfig = new GeosupportConfig(new GeoclientJni());
    }

    @Test
    public void testFunctionD() {
        Function function = geosupportConfig.getFunction(Function.FD);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.STREET_CODE, "10410");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        parameters.put(InputParam.STREET_CODE2, "10510");
        parameters.put(InputParam.BOROUGH_CODE2, "1");
        parameters.put(InputParam.STREET_CODE3, "10610");
        parameters.put(InputParam.BOROUGH_CODE3, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
        String expectedStreetName = "5 AVENUE";
        String expectedStreetCode = "11041001010";
        String expectedStreetNameTwo = "AVENUE OF THE AMERICAS";
        String expectedStreetCodeTwo = "11051001030";
        String expectedStreetNameThree = "7 AVENUE";
        String expectedStreetCodeThree = "11061004010";

        assertEquals(expectedStreetName, result.get("firstStreetNameNormalized"));
        assertEquals(expectedStreetCode, result.get("firstStreetCode"));
        assertEquals(expectedStreetNameTwo, result.get("secondStreetNameNormalized"));
        assertEquals(expectedStreetCodeTwo, result.get("secondStreetCode"));
        assertEquals(expectedStreetNameThree, result.get("thirdStreetNameNormalized"));
        assertEquals(expectedStreetCodeThree, result.get("thirdStreetCode"));
    }

    @Test
    public void testFunctionDG() {
        Function function = geosupportConfig.getFunction(Function.FD);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.STREET_CODE, "1041001");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        parameters.put(InputParam.STREET_CODE2, "1051001");
        parameters.put(InputParam.BOROUGH_CODE2, "1");
        parameters.put(InputParam.STREET_CODE3, "1061004");
        parameters.put(InputParam.BOROUGH_CODE3, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
        String expectedStreetName = "5 AVENUE";
        String expectedStreetCode = "11041001010";
        String expectedStreetNameTwo = "AVENUE OF THE AMERICAS";
        String expectedStreetCodeTwo = "11051001030";
        String expectedStreetNameThree = "7 AVENUE";
        String expectedStreetCodeThree = "11061004010";

        assertEquals(expectedStreetName, result.get("firstStreetNameNormalized"));
        assertEquals(expectedStreetCode, result.get("firstStreetCode"));
        assertEquals(expectedStreetNameTwo, result.get("secondStreetNameNormalized"));
        assertEquals(expectedStreetCodeTwo, result.get("secondStreetCode"));
        assertEquals(expectedStreetNameThree, result.get("thirdStreetNameNormalized"));
        assertEquals(expectedStreetCodeThree, result.get("thirdStreetCode"));
    }

    @Test
    public void testFunctionDN() {
        Function function = geosupportConfig.getFunction(Function.FD);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.STREET_CODE, "1041001010");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        parameters.put(InputParam.STREET_CODE2, "1051001030");
        parameters.put(InputParam.BOROUGH_CODE2, "1");
        parameters.put(InputParam.STREET_CODE3, "1061004010");
        parameters.put(InputParam.BOROUGH_CODE3, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
        String expectedStreetName = "5 AVENUE";
        String expectedStreetCode = "11041001010";
        String expectedStreetNameTwo = "AVENUE OF THE AMERICAS";
        String expectedStreetCodeTwo = "11051001030";
        String expectedStreetNameThree = "7 AVENUE";
        String expectedStreetCodeThree = "11061004010";

        assertEquals(expectedStreetName, result.get("firstStreetNameNormalized"));
        assertEquals(expectedStreetCode, result.get("firstStreetCode"));
        assertEquals(expectedStreetNameTwo, result.get("secondStreetNameNormalized"));
        assertEquals(expectedStreetCodeTwo, result.get("secondStreetCode"));
        assertEquals(expectedStreetNameThree, result.get("thirdStreetNameNormalized"));
        assertEquals(expectedStreetCodeThree, result.get("thirdStreetCode"));
    }

    @Test
    public void testFunction3() {
        Function function = geosupportConfig.getFunction(Function.F3);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.STREET_NAME, "Broadway");
        parameters.put(InputParam.STREET_NAME2, "W 144 ST");
        parameters.put(InputParam.STREET_NAME3,"W 143 ST");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
        String expectedLeftSegmentBlockfaceId = "1322602403";
        String expectedRightSegmentBlockfaceId = "1322601168";
        assertEquals(expectedLeftSegmentBlockfaceId, result.get("leftSegmentBlockfaceId"));
        assertEquals(expectedRightSegmentBlockfaceId, result.get("rightSegmentBlockfaceId"));
    }

    @Test
    public void testFunction2w() {
        Function function = geosupportConfig.getFunction(Function.F2W);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.STREET_NAME, "Broadway");
        parameters.put(InputParam.STREET_NAME2, "W 144 ST");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
    }

    @Test
    public void testFunctionAp() {
        Function function = geosupportConfig.getFunction(Function.FAP);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.HOUSE_NUMBER, "59");
        parameters.put(InputParam.STREET_NAME, "Maiden Lane");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
    }

    @Test
    public void testFunction1B() {
        Function function = geosupportConfig.getFunction(Function.F1B);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(HOUSE_NUMBER, "280");
        parameters.put(STREET_NAME, "Riverside Drive");
        parameters.put(BOROUGH_CODE, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeededWithWarning(GEOSUPPORT_RETURN_CODE, result)); // F1EX success with warning
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE2, result)); // F1AX success
    }

    private boolean succeeded(String grcName, Map<String, Object> result) {
        String grc = StringUtils.stringOrNullValueOf(result.get(grcName));
        return SUCCESS.is(grc);
    }

    private boolean succeededWithWarning(String grcName, Map<String, Object> result) {
        String grc = StringUtils.stringOrNullValueOf(result.get(grcName));
        return WARN.is(grc);
    }
}
