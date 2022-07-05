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

import static gov.nyc.doitt.gis.geoclient.api.InputParam.MODE_SWITCH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.configuration.AppConfig;

// TODO add tests for invalid parameters
// TODO clean up arguments (used by the mock) disconnect with parameters (used to call the actual function)
public abstract class AbstractMockInvokerTests {

    protected static final Logger logger = LoggerFactory.getLogger(GeosupportServiceImpl.class);

    private AppConfig serviceConfigurationMock;
    private Configuration functionConfigurationMock;
    private Function functionMock;
    private FieldSetConverter latLongConverterMock;
    protected GeosupportServiceImpl geosupportServiceImpl;
    protected MockSettings mockSettings;

    @BeforeEach
    public void setUp() throws Exception {
        mockSettings = withSettings();
    }

    @AfterEach
    public void tearDown() throws Exception {
        Mockito.reset(this.functionMock, this.latLongConverterMock, this.serviceConfigurationMock);
        this.geosupportServiceImpl = null;
    }

    protected Map<String, Object> mockFunctionCall(String functionName, Map<String, Object> arguments) {

        Map<String, Object> requiredArguments = new HashMap<String, Object>();
        requiredArguments.put(MODE_SWITCH, "X");

        Map<String, Object> allArguments = new HashMap<String, Object>();
        allArguments.putAll(requiredArguments);
        allArguments.putAll(arguments);

        Map<String, Object> expectedFunctionResult = new HashMap<String, Object>();
        expectedFunctionResult.put("called", functionName);

        mockConfiguration(requiredArguments);
        mockFunction(functionName, allArguments, expectedFunctionResult);
        mockLatLongConverter();
        mockAppConfig(functionName);

        constructClassUnderTest();

        return expectedFunctionResult;
    }

    protected void constructClassUnderTest() {
        this.geosupportServiceImpl = new GeosupportServiceImpl(serviceConfigurationMock);
    }

    protected void mockAppConfig(String functionName) {
        this.serviceConfigurationMock = mock(AppConfig.class, mockSettings);
        if (Function.F1AX.equals(functionName)) {
            // Special case naming for callGeosupport(...) test. F1AX is used.
            when(serviceConfigurationMock.geosupportFunction(functionName)).thenReturn(functionMock);
        } else if(Function.F1B.equals(functionName)) {
            when(serviceConfigurationMock.function1B()).thenReturn(functionMock);
        } else if(Function.F2W.equals(functionName)) {
            when(serviceConfigurationMock.function2W()).thenReturn(functionMock);
        } else if(Function.F3.equals(functionName)) {
            when(serviceConfigurationMock.function3()).thenReturn(functionMock);
        } else if(Function.FBL.equals(functionName)) {
            when(serviceConfigurationMock.functionBL()).thenReturn(functionMock);
        } else if(Function.FBN.equals(functionName)) {
            when(serviceConfigurationMock.functionBN()).thenReturn(functionMock);
        } else if(Function.FD.equals(functionName)) {
            when(serviceConfigurationMock.functionD()).thenReturn(functionMock);
        } else if(Function.FDG.equals(functionName)) {
            when(serviceConfigurationMock.functionDG()).thenReturn(functionMock);
        } else if(Function.FDN.equals(functionName)) {
            when(serviceConfigurationMock.functionDN()).thenReturn(functionMock);
        } else if(Function.FHR.equals(functionName)) {
            when(serviceConfigurationMock.functionHR()).thenReturn(functionMock);
        } else {
            // Unrecognized function name
            throw new IllegalArgumentException("Unrecognized function name: " + functionName);
        }
        when(serviceConfigurationMock.latLongFieldSetConverter()).thenReturn(latLongConverterMock);
    }

    protected void mockConfiguration(Map<String, Object> requiredArguments) {
        functionConfigurationMock = mock(Configuration.class, mockSettings);
        requiredArguments.put(MODE_SWITCH, "X");
        when(functionConfigurationMock.requiredArguments()).thenReturn(requiredArguments);
    }

    protected void mockFunction(String functionName, Map<String, Object> allArguments, Map<String, Object> expectedFunctionResult) {
        functionMock = mock(Function.class, mockSettings);
        when(functionMock.getId()).thenReturn(functionName);
        when(functionMock.getConfiguration()).thenReturn(functionConfigurationMock);
        when(functionMock.call(allArguments)).thenReturn(expectedFunctionResult);
    }

    protected void mockLatLongConverter() {
        latLongConverterMock = mock(FieldSetConverter.class, mockSettings);
    }

    protected void verifyMocks(String functionId, Map<String, Object> actualResult) {
        verify(latLongConverterMock).convert(functionId, actualResult);
    }
}
