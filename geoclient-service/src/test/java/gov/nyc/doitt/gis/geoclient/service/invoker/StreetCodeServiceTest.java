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

import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.MODE_SWITCH;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT_COMPACT_VALUE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_LENGTH;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE3;
import static gov.nyc.doitt.gis.geoclient.function.Function.FD;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.configuration.AppConfig;

// TODO add tests for invalid parameters
// TODO clean up arguments (used by the mock) disconnect with parameters (used to call the actual function)
public class StreetCodeServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(GeosupportServiceImpl.class);
    
    private AppConfig serviceConfigurationMock;
    private Function functionMock;
    private GeosupportServiceImpl geosupportServiceImpl;
    private LatLongEnhancer latLongEnhancerMock;

    @BeforeEach
    public void setUp() throws Exception {
        this.serviceConfigurationMock = mock(AppConfig.class, Mockito.withSettings().verboseLogging());
        this.geosupportServiceImpl = new GeosupportServiceImpl(serviceConfigurationMock);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Mockito.reset(this.functionMock, this.latLongEnhancerMock, this.serviceConfigurationMock);
        this.geosupportServiceImpl = null;
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
        logger.info("Calling mocked function {} with arguments {}", FD, arguments);
        Map<String, Object> actualResult = geosupportServiceImpl.callFunctionD("112345","223456","334567", 30, NORMALIZATION_FORMAT_COMPACT_VALUE);
        logger.info("Mocked function {} returned {}", FD, actualResult);
        verifyCall(actualResult);
    }

    private void verifyCall(Map<String, Object> actualResult) {
        verify(latLongEnhancerMock).addLatLong(actualResult);
    }

    private Map<String, Object> mockFunctionCall(String functionName, Map<String, Object> arguments) {

        // Function#configuration
        Configuration functionConfigurationMock = mock(Configuration.class, withSettings().verboseLogging());
        Map<String, Object> requiredArguments = new HashMap<String, Object>();
        requiredArguments.put(MODE_SWITCH, "X");
        when(functionConfigurationMock.requiredArguments()).thenReturn(requiredArguments);

        // Function
        functionMock = mock(Function.class, withSettings().verboseLogging());
        when(functionMock.getId()).thenReturn(functionName);
        when(functionMock.getConfiguration()).thenReturn(functionConfigurationMock);
        Map<String, Object> allArguments = new HashMap<String, Object>();
        allArguments.putAll(requiredArguments);
        allArguments.putAll(arguments);
        Map<String, Object> expectedFunctionResult = new HashMap<String, Object>();
        expectedFunctionResult.put("called", functionName);
        logger.info("Configuring mock to expect to be called with argument {} and then to return result {}", allArguments, expectedFunctionResult);
        when(functionMock.call(allArguments)).thenReturn(expectedFunctionResult);
        
        // LatLongEnhancer
        latLongEnhancerMock = mock(LatLongEnhancer.class, withSettings().verboseLogging());
        latLongEnhancerMock.addLatLong(expectedFunctionResult);
        
        // AppConfig: serviceConfiguration
        when(serviceConfigurationMock.functionD()).thenReturn(functionMock);
        when(serviceConfigurationMock.latLongEnhancer()).thenReturn(latLongEnhancerMock);
        
        return expectedFunctionResult;
    }

}
