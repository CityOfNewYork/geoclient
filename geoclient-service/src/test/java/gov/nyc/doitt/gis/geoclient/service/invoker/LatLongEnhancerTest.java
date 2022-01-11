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

import gov.nyc.doitt.gis.geoclient.api.OutputParam;
import gov.nyc.doitt.gis.geometry.domain.MapPoint;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LatLongEnhancerTest
{
    private LatLongEnhancer latLongEnhancer;
    private Map<String, Object> geocodingResult;
    private String xCoord;
    private String yCoord;
    private double expectedLat;
    private double expectedLong;

    @BeforeEach
    public void setUp() throws Exception
    {
        this.latLongEnhancer = new LatLongEnhancer();
        // Intersection: Amsterdam Avenue and West 111 Street, Manhattan
        this.geocodingResult = new HashMap<String, Object>();
        this.xCoord = "0994386";
        this.yCoord = "0232063";
        // GOAT: 40.803630 , -73.963388
        this.expectedLat = 40.80362939651128;
        this.expectedLong = -73.9633880907633;
    }

    @Test
    public void testAddLatLong_validPointAvailable()
    {
        this.geocodingResult.put(OutputParam.XCOORD, this.xCoord);
        this.geocodingResult.put(OutputParam.YCOORD, this.yCoord);
        this.latLongEnhancer.addLatLong(geocodingResult);
        assertEquals(this.expectedLat, (double)geocodingResult.get(LatLongEnhancer.DEFAULT_LATLONG_CONFIG.getLatName()));
        assertEquals(this.expectedLong, (double)geocodingResult.get(LatLongEnhancer.DEFAULT_LATLONG_CONFIG.getLongName()));
    }

    @Test
    public void testAddLatLong_invalidPointAvailable()
    {
        this.geocodingResult.put(OutputParam.XCOORD, this.xCoord);
        this.latLongEnhancer.addLatLong(geocodingResult);
        assertFalse(geocodingResult.containsKey(LatLongEnhancer.DEFAULT_LATLONG_CONFIG.getLatName()));
        assertFalse(geocodingResult.containsKey(LatLongEnhancer.DEFAULT_LATLONG_CONFIG.getLongName()));
    }

    @Test
    public void testGetNyspPoint()
    {
        // Empty
        MapPoint result = this.latLongEnhancer.getNyspPoint(geocodingResult, LatLongEnhancer.DEFAULT_LATLONG_CONFIG);
        assertZeroXY(result);
        // Only x available
        this.geocodingResult.put(OutputParam.XCOORD, this.xCoord);
        result = this.latLongEnhancer.getNyspPoint(geocodingResult, LatLongEnhancer.DEFAULT_LATLONG_CONFIG);
        assertZeroXY(result);
        // Only y available
        this.geocodingResult.remove(OutputParam.XCOORD);
        this.geocodingResult.put(OutputParam.YCOORD, this.yCoord);
        result = this.latLongEnhancer.getNyspPoint(geocodingResult, LatLongEnhancer.DEFAULT_LATLONG_CONFIG);
        assertZeroXY(result);
        // Both available
        this.geocodingResult.put(OutputParam.XCOORD, this.xCoord);
        this.geocodingResult.put(OutputParam.YCOORD, this.yCoord);
        result = this.latLongEnhancer.getNyspPoint(geocodingResult, LatLongEnhancer.DEFAULT_LATLONG_CONFIG);
        assertEquals(Double.parseDouble(this.xCoord), result.getX());
        assertEquals(Double.parseDouble(this.yCoord), result.getY());
    }

    @Test
    public void testDefaultPropertyValues()
    {
        assertSame(LatLongEnhancer.DEFAULT_TRANSFORMER, this.latLongEnhancer.getTransformer());
        assertSame(LatLongEnhancer.DEFAULT_LATLONG_CONFIG, this.latLongEnhancer.getLatLongConfigs().get(0));
        assertSame(LatLongEnhancer.DEFAULT_LATLONG_INTERNAL_LABEL_CONFIG, this.latLongEnhancer.getLatLongConfigs().get(1));
    }

    private void assertZeroXY(MapPoint mapPoint)
    {
        assertEquals(0.0, mapPoint.getX());
        assertEquals(0.0, mapPoint.getY());
    }
}
