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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import static gov.nyc.doitt.gis.geoclient.api.InputParam.BBL_BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BBL_TAX_BLOCK;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BBL_TAX_LOT;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BIN;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.COMPASS_DIRECTION;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.GEOSUPPORT_FUNCTION_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.HOUSE_NUMBER;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.MODE_SWITCH;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT_COMPACT_VALUE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT_SORT_VALUE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_LENGTH;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.ZIP_CODE;
import static gov.nyc.doitt.gis.geoclient.function.Function.F1AX;
import static gov.nyc.doitt.gis.geoclient.function.Function.F1B;
import static gov.nyc.doitt.gis.geoclient.function.Function.F2;
import static gov.nyc.doitt.gis.geoclient.function.Function.F3;
import static gov.nyc.doitt.gis.geoclient.function.Function.FBL;
import static gov.nyc.doitt.gis.geoclient.function.Function.FBN;
import static gov.nyc.doitt.gis.geoclient.function.Function.FD;
import static gov.nyc.doitt.gis.geoclient.function.Function.FDG;
import static gov.nyc.doitt.gis.geoclient.function.Function.FDN;
import static gov.nyc.doitt.gis.geoclient.function.Function.FHR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.configuration.AppConfig;

// TODO add tests for invalid parameters
// TODO clean up arguments (used by the mock) disconnect with parameters (used to call the actual function)
public class GeosupportServiceImplTest {
    private AppConfig serviceConfigurationMock;
    private GeosupportServiceImpl geosupportServiceImpl;

    @BeforeEach
    public void setUp() throws Exception {
        this.serviceConfigurationMock = mock(AppConfig.class, Mockito.withSettings().verboseLogging());
        this.geosupportServiceImpl = new GeosupportServiceImpl(serviceConfigurationMock);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Mockito.reset( new Class[] {AppConfig.class} );
        this.geosupportServiceImpl = new GeosupportServiceImpl(serviceConfigurationMock);
    }

    @Test
    public void testCallFunctionHR() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(GEOSUPPORT_FUNCTION_CODE, FHR);
        AssertResult assertResult = mockFunctionCall(FHR, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callGeosupport(arguments);
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunction1B_withBorough() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(HOUSE_NUMBER, "280");
        arguments.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments.put(BOROUGH_CODE, 1);
        AssertResult assertResult = mockFunctionCall(F1B, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction1B("280", "RIVERSIDE DRIVE", "Manhattan",
                null);
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunction1B_withBoroughAndZip() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(HOUSE_NUMBER, "280");
        arguments.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(ZIP_CODE, "10025");
        AssertResult assertResult = mockFunctionCall(F1B, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction1B("280", "RIVERSIDE DRIVE", "Manhattan",
                "10025");
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunction1B_withZip() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(HOUSE_NUMBER, "280");
        arguments.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments.put(ZIP_CODE, "10025");
        AssertResult assertResult = mockFunctionCall(F1B, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction1B("280", "RIVERSIDE DRIVE", null,
                "10025");
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunctionBL() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(BBL_BOROUGH_CODE, 1);
        arguments.put(BBL_TAX_BLOCK, "1889");
        arguments.put(BBL_TAX_LOT, "24");
        AssertResult assertResult = mockFunctionCall(FBL, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionBL("Manhattan", "1889", "24");
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunctionBN() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(BIN, "1234567");
        AssertResult assertResult = mockFunctionCall(FBN, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionBN("1234567");
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunctionD() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_CODE, "12345");
        arguments.put(STREET_CODE2, "23456");
        arguments.put(STREET_CODE3, "34567");
        arguments.put(BOROUGH_CODE, Integer.valueOf(1));
        arguments.put(BOROUGH_CODE2, Integer.valueOf(2));
        arguments.put(BOROUGH_CODE3, Integer.valueOf(3));
        arguments.put(NORMALIZATION_FORMAT, NORMALIZATION_FORMAT_COMPACT_VALUE);
        arguments.put(NORMALIZATION_LENGTH, 30);
        AssertResult assertResult = mockFunctionCall(FD, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionD("112345","223456","334567", 30, NORMALIZATION_FORMAT_COMPACT_VALUE);
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunctionDG() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_CODE, "1234567");
        arguments.put(STREET_CODE2, "1234567");
        arguments.put(STREET_CODE3, "1234567");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(BOROUGH_CODE3, 3);
        arguments.put(NORMALIZATION_FORMAT, NORMALIZATION_FORMAT_SORT_VALUE);
        arguments.put(NORMALIZATION_LENGTH, 20);
        AssertResult assertResult = mockFunctionCall(FDG, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionDG("11234567","21234567","31234567", 20, NORMALIZATION_FORMAT_SORT_VALUE);
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunctionDN() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_CODE, "1234567890");
        arguments.put(STREET_CODE2, "1234567890");
        arguments.put(STREET_CODE3, "1234567890");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(BOROUGH_CODE3, 3);
        arguments.put(NORMALIZATION_FORMAT, NORMALIZATION_FORMAT_SORT_VALUE);
        arguments.put(NORMALIZATION_LENGTH, 12);
        AssertResult assertResult = mockFunctionCall(FDN, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionDN("11234567890","21234567890","31234567890", 12, NORMALIZATION_FORMAT_SORT_VALUE);
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunction2() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments.put(STREET_NAME2, "W 100 ST");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(COMPASS_DIRECTION, "N");
        AssertResult assertResult = mockFunctionCall(F2, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction2("RIVERSIDE DRIVE", "Manhattan",
                "W 100 ST", "BRONX", "N");
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallFunction2_HD01971827() {
        // 1st call with compassDirection
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments.put(STREET_NAME2, "W 100 ST");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(COMPASS_DIRECTION, "N");
        AssertResult assertResult = mockFunctionCall(F2, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction2("RIVERSIDE DRIVE", "Manhattan",
                "W 100 ST", "BRONX", "N");
        assertCall(actualResult, assertResult);

        // 2nd call without compassDirection
        Map<String, Object> arguments2 = new HashMap<String, Object>();
        arguments2.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments2.put(STREET_NAME2, "W 100 ST");
        arguments2.put(BOROUGH_CODE, 1);
        arguments2.put(BOROUGH_CODE2, 2);
        AssertResult assertResult2 = mockFunctionCall(F2, arguments2);
        Map<String, Object> actualResult2 = geosupportServiceImpl.callFunction2("RIVERSIDE DRIVE", "Manhattan",
                "W 100 ST", "BRONX", null);
        assertCall(actualResult2, assertResult2);
    }

    @Test
    public void testCallFunction3() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(STREET_NAME, "RIVERSIDE DRIVE");
        arguments.put(STREET_NAME2, "W 100 ST");
        arguments.put(STREET_NAME3, "AMSTERDAM AV");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(BOROUGH_CODE3, 3);
        arguments.put(COMPASS_DIRECTION, "E");
        AssertResult assertResult = mockFunctionCall(F3, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction3("RIVERSIDE DRIVE", "Manhattan",
                "W 100 ST", "Bronx", "AMSTERDAM AV", "BROOKLYN", "E");
        assertCall(actualResult, assertResult);
    }

    @Test
    public void testCallGeosupport() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(GEOSUPPORT_FUNCTION_CODE, F1AX);
        AssertResult assertResult = mockFunctionCall(F1AX, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callGeosupport(arguments);
        assertCall(actualResult, assertResult);
    }

    private void assertCall(Map<String, Object> actualResult, AssertResult assertResult) {
        assertResult.assertActual(actualResult);
        assertLatLongEnhancerCalled(actualResult);
    }

    private void assertLatLongEnhancerCalled(Map<String, Object> actualResult) {
        verify(this.serviceConfigurationMock.latLongEnhancer()).addLatLong(actualResult);
    }

    private AssertResult mockFunctionCall(String functionName, Map<String, Object> arguments) {

        // Function#configuration
        Configuration functionConfigurationMock = mock(Configuration.class);
        Map<String, Object> requiredArguments = new HashMap<String, Object>();
        requiredArguments.put(MODE_SWITCH, "X");
        when(functionConfigurationMock.requiredArguments()).thenReturn(requiredArguments);

        // Function
        Function functionMock = mock(Function.class);
        when(functionMock.getId()).thenReturn(functionName);
        when(functionMock.getConfiguration()).thenReturn(functionConfigurationMock);
        Map<String, Object> clientAndRequiredArguments = new HashMap<String, Object>();
        clientAndRequiredArguments.putAll(requiredArguments);
        clientAndRequiredArguments.putAll(arguments);

        when(functionMock.call(clientAndRequiredArguments)).thenReturn(clientAndRequiredArguments);

        // LatLongEnhancer
        LatLongEnhancer latLongEnhancerMock = mock(LatLongEnhancer.class);

        // AppConfig: serviceConfiguration
        when(this.serviceConfigurationMock.functionBL()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.functionBN()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.functionD()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.functionDG()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.functionDN()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.function1B()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.function2()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.function3()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.functionHR()).thenReturn(functionMock);
        when(this.serviceConfigurationMock.geosupportFunction(functionName)).thenReturn(functionMock);
        when(this.serviceConfigurationMock.latLongEnhancer()).thenReturn(latLongEnhancerMock);

        return new AssertResult(functionName, arguments, requiredArguments);
    }

    private static class AssertResult {
        private String functionId;
        private Map<String, Object> clientArguments;
        private Map<String, Object> requiredArguments;

        AssertResult(String functionId, Map<String, Object> clientArguments, Map<String, Object> requiredArguments) {
            super();
            this.functionId = functionId;
            this.clientArguments = clientArguments;
            this.requiredArguments = requiredArguments;
        }

        void assertActual(Map<String, Object> actualResult) {
            assertNotNull(actualResult, "Function " + functionId + " returned null.");
            assertTrue(actualResult.entrySet().containsAll(this.clientArguments.entrySet()),
                    "Actual result " + actualResult + " does not contain all client arguments " + this.clientArguments);
            assertTrue(actualResult.entrySet().containsAll(this.clientArguments.entrySet()), "Actual result "
                    + actualResult + " does not contain all required arguments " + this.requiredArguments);
        }
    }

}
