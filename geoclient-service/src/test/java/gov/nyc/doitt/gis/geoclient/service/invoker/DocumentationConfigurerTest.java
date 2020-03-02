/*
 * Copyright 2013-2019 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.*;
import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.domain.Documentation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DocumentationConfigurerTest
{
    @Test
    public void testConfigureDocumentation()
    {
        GeosupportConfig geosupportConfigMock = Mockito.mock(GeosupportConfig.class);
        DataDictionary dataDictionary = new DataDictionary(new ArrayList<ItemDocumentation>());
        Mockito.when(geosupportConfigMock.getDataDictionary()).thenReturn(dataDictionary);
        
        //FunctionDocumentation addressDocumentation = new FunctionDocumentation(Function.F1B,new Description("Textxt"),new ArrayList<ItemDocumentation>());
        FunctionDocumentation addressDocumentation = new FunctionDocumentation();
        addressDocumentation.setId(Function.F1B);
        addressDocumentation.setDisplayName("address");
        addressDocumentation.setFields(new TreeSet<ItemDocumentation>());
        Mockito.when(geosupportConfigMock.getFunctionDocumentation(Function.F1B)).thenReturn(addressDocumentation);

        FunctionDocumentation bblDocumentation = new FunctionDocumentation();
        bblDocumentation.setId(Function.FBL);
        bblDocumentation.setFields(new TreeSet<ItemDocumentation>());
        Mockito.when(geosupportConfigMock.getFunctionDocumentation(Function.FBL)).thenReturn(bblDocumentation);

        FunctionDocumentation binDocumentation = new FunctionDocumentation();
        binDocumentation.setId(Function.FBN);
        binDocumentation.setFields(new TreeSet<ItemDocumentation>());
        Mockito.when(geosupportConfigMock.getFunctionDocumentation(Function.FBN)).thenReturn(binDocumentation);

        FunctionDocumentation blockfaceDocumentation = new FunctionDocumentation();
        Mockito.when(geosupportConfigMock.getFunctionDocumentation(Function.F3)).thenReturn(blockfaceDocumentation);

        FunctionDocumentation intersectionDocumentation = new FunctionDocumentation();
        intersectionDocumentation.setId(Function.F2);
        intersectionDocumentation.setFields(new TreeSet<ItemDocumentation>());
        Mockito.when(geosupportConfigMock.getFunctionDocumentation(Function.F2)).thenReturn(intersectionDocumentation);

        LatLongEnhancer latLongEnhancerMock = Mockito.mock(LatLongEnhancer.class);
        
        LatLongConfig latLongConfig = LatLongEnhancer.DEFAULT_CONFIGS.get(0);
        LatLongConfig latLongInternalConfig = LatLongEnhancer.DEFAULT_CONFIGS.get(1);
        List<ItemDocumentation> expectedLatLongDocs = new ArrayList<ItemDocumentation>();
        expectedLatLongDocs.add(latLongConfig.getLatDocumentation());
        expectedLatLongDocs.add(latLongConfig.getLongDocumentation());
        expectedLatLongDocs.add(latLongInternalConfig.getLatDocumentation());
        expectedLatLongDocs.add(latLongInternalConfig.getLongDocumentation());
        Mockito.when(latLongEnhancerMock.getItemDocumentation()).thenReturn(expectedLatLongDocs);
        
        Documentation result = new DocumentationConfigurer(geosupportConfigMock, latLongEnhancerMock).configureDocumentation();
        
        assertSame(dataDictionary, result.getDataDictionary());
        assertSame(addressDocumentation, result.getAddressDocumentation());
        assertSame(bblDocumentation, result.getBblDocumentation());
        assertSame(binDocumentation, result.getBinDocumentation());
        assertSame(blockfaceDocumentation, result.getBlockfaceDocumentation());
        assertSame(intersectionDocumentation, result.getIntersectionDocumentation());
        assertNotSame(result.getAddressDocumentation(),result.getPlaceDocumentation());
        assertSame(addressDocumentation.getId(), result.getPlaceDocumentation().getId());
        assertSame(addressDocumentation.getDescription(), result.getPlaceDocumentation().getDescription());
        assertEquals(addressDocumentation.getFields(), result.getPlaceDocumentation().getFields());
        
        assertTrue(addressDocumentation.getFields().contains(latLongConfig.getLatDocumentation()));
        assertTrue(addressDocumentation.getFields().contains(latLongConfig.getLongDocumentation()));
        assertTrue(addressDocumentation.getFields().contains(latLongInternalConfig.getLatDocumentation()));
        assertTrue(addressDocumentation.getFields().contains(latLongInternalConfig.getLongDocumentation()));
            
        assertTrue(result.getPlaceDocumentation().getFields().contains(latLongConfig.getLatDocumentation()));
        assertTrue(result.getPlaceDocumentation().getFields().contains(latLongConfig.getLongDocumentation()));
        assertTrue(result.getPlaceDocumentation().getFields().contains(latLongInternalConfig.getLatDocumentation()));
        assertTrue(result.getPlaceDocumentation().getFields().contains(latLongInternalConfig.getLongDocumentation()));
        
        assertTrue(result.getIntersectionDocumentation().getFields().contains(latLongConfig.getLatDocumentation()));
        assertTrue(result.getIntersectionDocumentation().getFields().contains(latLongConfig.getLongDocumentation()));
        assertFalse(result.getIntersectionDocumentation().getFields().contains(latLongInternalConfig.getLatDocumentation()));
        assertFalse(result.getIntersectionDocumentation().getFields().contains(latLongInternalConfig.getLongDocumentation()));
        
        assertFalse(result.getBblDocumentation().getFields().contains(latLongConfig.getLatDocumentation()));
        assertFalse(result.getBblDocumentation().getFields().contains(latLongConfig.getLongDocumentation()));
        assertTrue(result.getBblDocumentation().getFields().contains(latLongInternalConfig.getLatDocumentation()));
        assertTrue(result.getBblDocumentation().getFields().contains(latLongInternalConfig.getLongDocumentation()));
        
        assertFalse(result.getBinDocumentation().getFields().contains(latLongConfig.getLatDocumentation()));
        assertFalse(result.getBinDocumentation().getFields().contains(latLongConfig.getLongDocumentation()));
        assertTrue(result.getBinDocumentation().getFields().contains(latLongInternalConfig.getLatDocumentation()));
        assertTrue(result.getBinDocumentation().getFields().contains(latLongInternalConfig.getLongDocumentation()));
        
        assertTrue(result.getDataDictionary().getItems().contains(latLongConfig.getLatDocumentation()));
        assertTrue(result.getDataDictionary().getItems().contains(latLongConfig.getLongDocumentation()));
        assertTrue(result.getDataDictionary().getItems().contains(latLongInternalConfig.getLatDocumentation()));
        assertTrue(result.getDataDictionary().getItems().contains(latLongInternalConfig.getLongDocumentation()));

        assertEquals(Documentation.ADDRESS_FUNCTION,addressDocumentation.getDisplayName());
        assertEquals(Documentation.BBL_FUNCTION,bblDocumentation.getDisplayName());
        assertEquals(Documentation.BIN_FUNCTION,binDocumentation.getDisplayName());
        assertEquals(Documentation.BLOCKFACE_FUNCTION,blockfaceDocumentation.getDisplayName());
        assertEquals(Documentation.INTERSECTION_FUNCTION,intersectionDocumentation.getDisplayName());
        assertEquals(Documentation.PLACE_FUNCTION,result.getPlaceDocumentation().getDisplayName());
    }

}
