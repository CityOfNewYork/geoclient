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
package gov.nyc.doitt.gis.geoclient.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.test.GeoclientStub;

// TODO Cleanup this test and move FunctionConfig creation into each test case
public class FunctionConfigTest
{
    private WorkAreaConfig wa1Config;
    private WorkAreaConfig wa2Config;
    private FunctionConfig oneWorkAreaFunction;
    private FunctionConfig twoWorkAreaFunction;
    private FunctionConfig twoWorkAreaDefaultArgsFunction;
    private DefaultConfiguration configuration;

    @BeforeEach
    public void setUp() throws Exception
    {
        this.wa1Config = new WorkAreaConfig("WW1", 12, true, TestData.newFieldList(TestData.fieldOne, TestData.fieldTwo), Collections.<Filter>emptyList());
        this.wa2Config = new WorkAreaConfig("WW2_F1B", 4, false, TestData.newFieldList(TestData.makeField("pqr", 1, 4)), Collections.<Filter>emptyList());
        this.configuration = new DefaultConfiguration();
        // Don't use the names of real functions. Previously, using function "1B"
        // was causing the real 1B in GeosupportConfigIntegration test to fail
        // when all tests were run from Maven;  basically, the 1B that was
        // returned by the integration test was the mock one created in
        // FunctionConfigTest. Issue did not occur within Eclipse or when
        // test was run individually.
        this.oneWorkAreaFunction = new FunctionConfig("EG", this.wa1Config, null);
        this.twoWorkAreaFunction = new FunctionConfig("9B", this.wa1Config, this.wa2Config);
        this.twoWorkAreaDefaultArgsFunction = new FunctionConfig("12", this.wa1Config, this.wa2Config, this.configuration);
    }

    @AfterEach
    public void tearDown()
    {
        this.wa1Config = null;
        this.wa2Config = null;
        this.oneWorkAreaFunction = null;
        this.twoWorkAreaFunction = null;
        this.twoWorkAreaDefaultArgsFunction = null;
    }

    @Test
    public void testCreateUsesGivenDefaultConfiguration()
    {
        DefaultConfiguration config = new DefaultConfiguration();
        FunctionConfig functionConfig = new FunctionConfig("XX", this.wa1Config, this.wa2Config,config);
        Function function = functionConfig.createFunction(new GeoclientStub());
        assertSame(config, function.getConfiguration());
    }

    @Test
    public void testCreateNewDefaultConfigurationEvenIfFieldIsNull()
    {
        FunctionConfig functionConfig = new FunctionConfig("XX", this.wa1Config, this.wa2Config);
        Function function = functionConfig.createFunction(new GeoclientStub());
        assertNotNull(function.getConfiguration());
    }


    @Test
    public void testCreateOneWorkAreaFunction()
    {
        Function function = this.oneWorkAreaFunction.createFunction(new GeoclientStub());
        assertEquals("EG", function.getId());
    }

    @Test
    public void testCreateTwoWorkAreaFunction()
    {
        Function function = this.twoWorkAreaFunction.createFunction(new GeoclientStub());
        assertEquals("9B", function.getId());
    }

    @Test
    public void testOneWorkAreaConstructor()
    {
        assertSame(this.wa1Config, this.oneWorkAreaFunction.getWorkAreaOneConfig());
        assertNull(this.oneWorkAreaFunction.getWorkAreaTwoConfig());
        assertNull(this.oneWorkAreaFunction.getConfiguration());
        assertFalse(this.oneWorkAreaFunction.isTwoWorkAreaFunction());
        assertFalse(this.oneWorkAreaFunction.hasDefaultArguments());
    }

    @Test
    public void testTwoWorkAreaConstructor()
    {
        assertSame(this.wa1Config, this.twoWorkAreaFunction.getWorkAreaOneConfig());
        assertSame(this.wa2Config, this.twoWorkAreaFunction.getWorkAreaTwoConfig());
        assertNull(this.oneWorkAreaFunction.getConfiguration());
        assertTrue(this.twoWorkAreaFunction.isTwoWorkAreaFunction());
        assertFalse(this.twoWorkAreaFunction.hasDefaultArguments());
    }

    @Test
    public void testTwoWorkAreaArgumentsConstructor()
    {
        assertSame(this.wa1Config, this.twoWorkAreaDefaultArgsFunction.getWorkAreaOneConfig());
        assertSame(this.wa2Config, this.twoWorkAreaDefaultArgsFunction.getWorkAreaTwoConfig());
        assertSame(this.configuration, this.twoWorkAreaDefaultArgsFunction.getConfiguration());
        assertTrue(this.twoWorkAreaDefaultArgsFunction.isTwoWorkAreaFunction());
        assertTrue(this.twoWorkAreaDefaultArgsFunction.hasDefaultArguments());
    }

    @Test//(expected=DuplicateFieldNameException.class)
    public void testCreateTwoWorkAreaFunction_duplicateFields()
    {
        WorkAreaConfig duplicateFieldWa2 = new WorkAreaConfig("DUP", TestData.fieldDuplicateIdOfOne.getLength(), true, TestData.newFieldList(TestData.fieldDuplicateIdOfOne),Collections.<Filter>emptyList());
        FunctionConfig badFun = new FunctionConfig("DUP", this.wa1Config, duplicateFieldWa2);
        badFun.createFunction(new GeoclientStub());
    }


}
