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
package gov.nyc.doitt.gis.geoclient.service.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import gov.nyc.doitt.gis.geoclient.service.domain.BadRequest;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;

public class RestControllerTest {
    private GeosupportService geosupportServiceMock;
    private RestController restController;
    private Map<String, Object> expectedResult;

    @BeforeEach
    public void setUp() throws Exception {
        this.geosupportServiceMock = Mockito.mock(GeosupportService.class);
        this.restController = new RestController();
        this.restController.setGeosupportService(geosupportServiceMock);
        this.expectedResult = new HashMap<String, Object>();
    }

    @Test
    public void testAddress_withBorough() throws Exception {
        String houseNumber = "59";
        String street = "Maiden Ln";
        String borough = "Manhattan";
        Mockito.when(this.geosupportServiceMock.callFunction1B(houseNumber, street, borough, null))
                .thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.address(houseNumber, street, borough, null);
        assertSame(expectedResult, actualResult.get(RestController.ADDRESS_OBJ));
    }

    @Test
    public void testAddress_withZip() throws Exception {
        String houseNumber = "59";
        String street = "Maiden Ln";
        String zip = "10038";
        Mockito.when(this.geosupportServiceMock.callFunction1B(houseNumber, street, null, zip))
                .thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.address(houseNumber, street, null, zip);
        assertSame(expectedResult, actualResult.get(RestController.ADDRESS_OBJ));
    }

    @Test
    public void testAddress_withBoroughAndZip() throws Exception {
        String houseNumber = "59";
        String street = "Maiden Ln";
        String borough = "Manhattan";
        String zip = "10038";
        Mockito.when(this.geosupportServiceMock.callFunction1B(houseNumber, street, borough, zip))
                .thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.address(houseNumber, street, borough, zip);
        assertSame(expectedResult, actualResult.get(RestController.ADDRESS_OBJ));
    }

    @Test
    public void testAddress_withoutBoroughOrZip() throws Exception {
        assertThrows(MissingAnyOfOptionalServletRequestParametersException.class, () -> {
            this.restController.address("59", "Maiden Ln", null, null);
        });
    }

    @Test
    public void testAddressPoint_withBorough() throws Exception {
        String houseNumber = "59";
        String street = "Maiden Ln";
        String borough = "Manhattan";
        Mockito.when(this.geosupportServiceMock.callFunctionAP(houseNumber, street, borough, null))
                .thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.addresspoint(houseNumber, street, borough, null);
        assertSame(expectedResult, actualResult.get(RestController.ADDRESSPOINT_OBJ));
    }

    @Test
    public void testPlace_withBorough() throws Exception {
        String street = "Empire State Building";
        String borough = "Manhattan";
        Mockito.when(this.geosupportServiceMock.callFunction1B(null, street, borough, null)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.place(street, borough, null);
        assertSame(expectedResult, actualResult.get(RestController.PLACE_OBJ));
    }

    @Test
    public void testPlace_withBoroughAndZip() throws Exception {
        String street = "Empire State Building";
        String borough = "Manhattan";
        String zip = "10025";
        Mockito.when(this.geosupportServiceMock.callFunction1B(null, street, borough, zip)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.place(street, borough, zip);
        assertSame(expectedResult, actualResult.get(RestController.PLACE_OBJ));
    }

    @Test
    public void testPlace_withZip() throws Exception {
        String street = "Empire State Building";
        String zip = "10025";
        Mockito.when(this.geosupportServiceMock.callFunction1B(null, street, null, zip)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.place(street, null, zip);
        assertSame(expectedResult, actualResult.get(RestController.PLACE_OBJ));
    }

    @Test
    public void testPlace_withoutBoroughOrZip() throws Exception {
        assertThrows(MissingAnyOfOptionalServletRequestParametersException.class, () -> {
            this.restController.place("GWB", null, null);
        });
    }

    @Test
    public void testIntersection() {
        String crossStreetOne = "John St";
        String crossStreetTwo = "Maiden Ln";
        String borough = "Manhattan";
        String borough2 = "Manhattan";
        String compassDirection = "W";
        Mockito.when(this.geosupportServiceMock.callFunction2(crossStreetOne, borough, crossStreetTwo, borough2,
                compassDirection)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.intersection(crossStreetOne, crossStreetTwo, borough,
                borough2, compassDirection);
        assertSame(expectedResult, actualResult.get(RestController.INTERSECTION_OBJ));
    }

    @Test
    public void testBlockface() {

        String onStreet = "Broadway";
        String crossStreetOne = "John St";
        String crossStreetTwo = "Maiden Ln";
        String borough = "Manhattan";
        String borough2 = "Manhattan";
        String borough3 = "Texas";
        String compassDirection = "W";
        Mockito.when(this.geosupportServiceMock.callFunction3(onStreet, borough, crossStreetOne, borough2,
                crossStreetTwo, borough3, compassDirection)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.blockface(onStreet, crossStreetOne, crossStreetTwo,
                borough, borough2, borough3, compassDirection);
        assertSame(expectedResult, actualResult.get(RestController.BLOCKFACE_OBJ));
    }

    @Test
    public void testBbl() {

        String borough = "Manhattan";
        String block = "1889";
        String lot = "1";
        Mockito.when(this.geosupportServiceMock.callFunctionBL(borough, block, lot)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.bbl(borough, block, lot);
        assertSame(expectedResult, actualResult.get(RestController.BBL_OBJ));
    }

    @Test
    public void testBin() {
        String bin = "1234567";
        Mockito.when(this.geosupportServiceMock.callFunctionBN(bin)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.bin(bin);
        assertSame(expectedResult, actualResult.get(RestController.BIN_OBJ));
    }

    @Test
    public void testNormalize() {
        String streetName = "w 100 st";
        Integer max = 24;
        String format = "C";
        Mockito.when(this.geosupportServiceMock.callFunctionN(streetName, max, format)).thenReturn(expectedResult);
        Map<String, Object> actualResult = this.restController.normalize(streetName, max, format);
        assertSame(expectedResult, actualResult.get(RestController.NORMALIZE_OBJ));
    }

    @Test
    public void testVersion() {
        Version version = new Version();
        Mockito.when(this.geosupportServiceMock.version()).thenReturn(version);
        assertSame(version, this.restController.version());
    }

    @Test
    public void testHandleMissingRequestParameter() {
        MockHttpServletRequest req = new MockHttpServletRequest();
        String requestUri = "/foo";
        String queryString = "bar=1";
        req.setRequestURI(requestUri);
        req.setQueryString(queryString);
        MissingAnyOfOptionalServletRequestParametersException e = new MissingAnyOfOptionalServletRequestParametersException(
                "dog", "cat");
        ResponseEntity<BadRequest> result = this.restController.handleMissingRequestParameter(e, req);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(String.format("%s?%s", requestUri, queryString), result.getBody().getRequestUri());
        assertEquals(e.getMessage(), result.getBody().getMessage());
    }

}
