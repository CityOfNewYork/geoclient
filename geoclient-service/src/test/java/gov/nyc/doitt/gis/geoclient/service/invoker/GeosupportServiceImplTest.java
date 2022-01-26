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
import static gov.nyc.doitt.gis.geoclient.function.Function.F2W;
import static gov.nyc.doitt.gis.geoclient.function.Function.F3;
import static gov.nyc.doitt.gis.geoclient.function.Function.FBL;
import static gov.nyc.doitt.gis.geoclient.function.Function.FBN;
import static gov.nyc.doitt.gis.geoclient.function.Function.FD;
import static gov.nyc.doitt.gis.geoclient.function.Function.FDG;
import static gov.nyc.doitt.gis.geoclient.function.Function.FDN;
import static gov.nyc.doitt.gis.geoclient.function.Function.FHR;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class GeosupportServiceImplTest extends AbstractMockInvokerTests {

    @Test
    public void testCallFunction1B_withBorough() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(HOUSE_NUMBER, "280");
        arguments.put(STREET_NAME, "RSD");
        arguments.put(BOROUGH_CODE, 1);
        mockFunctionCall(F1B, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction1B("280", "RSD", "Manhattan", null);
        verifyMocks("F" + F1B, actualResult);
    }

    @Test
    public void testCallFunction1B_withZip() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(HOUSE_NUMBER, "280");
        arguments.put(STREET_NAME, "RSD");
        arguments.put(ZIP_CODE, "10025");
        mockFunctionCall(F1B, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction1B("280", "RSD", null, "10025");
        verifyMocks("F" + F1B, actualResult);
    }

    @Test
    public void testCallFunction2() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_NAME, "RSD");
        arguments.put(STREET_NAME2, "W 100 ST");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(COMPASS_DIRECTION, "N");
        mockFunctionCall(F2W, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction2("RSD", "Manhattan","W 100 ST", "BRONX", "N");
        verifyMocks("F" + F2W, actualResult);
    }

    @Test
    public void testCallFunction2_HD01971827() {
        // 1st call with compassDirection
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_NAME, "RSD");
        arguments.put(STREET_NAME2, "W 100 ST");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(COMPASS_DIRECTION, "N");
        mockFunctionCall(F2W, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction2("RSD", "Manhattan", "W 100 ST", "BRONX", "N");
        verifyMocks("F" + F2W, actualResult);

        // 2nd call without compassDirection
        Map<String, Object> arguments2 = new HashMap<String, Object>();
        arguments2.put(STREET_NAME, "RSD");
        arguments2.put(STREET_NAME2, "W 100 ST");
        arguments2.put(BOROUGH_CODE, 1);
        arguments2.put(BOROUGH_CODE2, 2);
        mockFunctionCall(F2W, arguments2);
        Map<String, Object> actualResult2 = geosupportServiceImpl.callFunction2("RSD", "Manhattan", "W 100 ST", "BRONX", null);
        verifyMocks("F" + F2W, actualResult2);
    }

    @Test
    public void testCallFunction3() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(STREET_NAME, "RSD");
        arguments.put(STREET_NAME2, "W 100 ST");
        arguments.put(STREET_NAME3, "AMSTERDAM AV");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(BOROUGH_CODE3, 3);
        arguments.put(COMPASS_DIRECTION, "E");
        mockFunctionCall(F3, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunction3("RSD", "Manhattan", "W 100 ST", "Bronx", "AMSTERDAM AV", "BROOKLYN", "E");
        verifyMocks("F" + F3, actualResult);
    }

    @Test
    public void testCallFunctionBL() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(BBL_BOROUGH_CODE, 1);
        arguments.put(BBL_TAX_BLOCK, "1889");
        arguments.put(BBL_TAX_LOT, "24");
        mockFunctionCall(FBL, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionBL("Manhattan", "1889", "24");
        verifyMocks("F" + FBL, actualResult);
    }

    @Test
    public void testCallFunctionBN() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(BIN, "1234567");
        mockFunctionCall(FBN, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionBN("1234567");
        verifyMocks("F" + FBN, actualResult);
    }

    @Test
    public void testCallFunctionD() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(STREET_CODE, "12345");
        arguments.put(STREET_CODE2, "23456");
        arguments.put(STREET_CODE3, "34567");
        arguments.put(BOROUGH_CODE, 1);
        arguments.put(BOROUGH_CODE2, 2);
        arguments.put(BOROUGH_CODE3, 3);
        arguments.put(NORMALIZATION_FORMAT, NORMALIZATION_FORMAT_COMPACT_VALUE);
        arguments.put(NORMALIZATION_LENGTH, 30);
        mockFunctionCall(FD, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionD("112345","223456","334567", 30, NORMALIZATION_FORMAT_COMPACT_VALUE);
        verifyMocks("F" + FD, actualResult);
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
        mockFunctionCall(FDG, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionDG("11234567","21234567","312        arguments.put(GEOSUPPORT_FUNCTION_CODE, FHR);\n" +
                "34567", 20, NORMALIZATION_FORMAT_SORT_VALUE);
        verifyMocks("F" + FDG, actualResult);
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
        mockFunctionCall(FDN, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionDN("11234567890","21234567890","31234567890", 12, NORMALIZATION_FORMAT_SORT_VALUE);
        verifyMocks("F" + FDN, actualResult);
    }

    @Test
    public void testCallFunctionHR() {
        Map<String, Object> arguments = new HashMap<>();
        mockFunctionCall(FHR, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionHR();
        verifyMocks("F" + FHR, actualResult);
    }

    @Test
    public void testCallGeosupport() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(GEOSUPPORT_FUNCTION_CODE, F1AX);
        mockFunctionCall(F1AX, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callGeosupport(arguments);
        verifyMocks("F" + F1AX, actualResult);
    }

}
