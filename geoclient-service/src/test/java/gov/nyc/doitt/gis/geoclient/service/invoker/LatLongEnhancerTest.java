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

import static org.junit.Assert.*;
import gov.nyc.doitt.gis.geoclient.config.OutputParam;
import gov.nyc.doitt.gis.geometry.domain.MapPoint;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class LatLongEnhancerTest
{
	private LatLongEnhancer latLongEnhancer;
	private Map<String, Object> geocodingResult;
	private String xCoord;
	private String yCoord;
	private double expectedLat;
	private double expectedLong;

	@Before
	public void setUp() throws Exception
	{
		this.latLongEnhancer = new LatLongEnhancer();
		this.geocodingResult = new HashMap<String, Object>();
		this.xCoord = "0994386";
		this.yCoord = "0232063";
		this.expectedLat = 40.80362939651128;
		this.expectedLong = -73.9633880907633;
	}

	@Test
	public void testAddLatLong_validPointAvailable()
	{
		this.geocodingResult.put(OutputParam.XCOORD, this.xCoord);
		this.geocodingResult.put(OutputParam.YCOORD, this.yCoord);
		this.latLongEnhancer.addLatLong(geocodingResult);
		assertEquals(this.expectedLat, (double)geocodingResult.get(LatLongEnhancer.DEFAULT_LATLONG_CONFIG.getLatName()), 0);
		assertEquals(this.expectedLong, (double)geocodingResult.get(LatLongEnhancer.DEFAULT_LATLONG_CONFIG.getLongName()), 0);
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
		assertEquals(Double.valueOf(this.xCoord), result.getX(), 0);
		assertEquals(Double.valueOf(this.yCoord), result.getY(), 0);
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
		assertEquals(0, mapPoint.getX(), 0);
		assertEquals(0, mapPoint.getY(), 0);
	}
}
