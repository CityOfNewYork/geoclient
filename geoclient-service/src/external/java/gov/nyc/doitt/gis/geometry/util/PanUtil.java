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
package gov.nyc.doitt.gis.geometry.util;

import static gov.nyc.doitt.gis.geometry.util.Constants.EAST;
import static gov.nyc.doitt.gis.geometry.util.Constants.NORTH;
import static gov.nyc.doitt.gis.geometry.util.Constants.NORTHEAST;
import static gov.nyc.doitt.gis.geometry.util.Constants.NORTHWEST;
import static gov.nyc.doitt.gis.geometry.util.Constants.SOUTH;
import static gov.nyc.doitt.gis.geometry.util.Constants.SOUTHEAST;
import static gov.nyc.doitt.gis.geometry.util.Constants.SOUTHWEST;
import static gov.nyc.doitt.gis.geometry.util.Constants.WEST;
import gov.nyc.doitt.gis.geometry.domain.DoittEnvelope;
import gov.nyc.doitt.gis.geometry.domain.MapEnvelope;
import gov.nyc.doitt.gis.geometry.domain.MapPoint;

public class PanUtil {
	public static void adjustEnvelopeForPan(MapEnvelope mapEnvelope, MapPoint from, MapPoint to) {
		double deltaX = from.getX() - to.getX();
		double deltaY = from.getY() - to.getY();
		mapEnvelope.setMaxX(mapEnvelope.getMaxX() + deltaX);
		mapEnvelope.setMaxY(mapEnvelope.getMaxY() + deltaY);
		mapEnvelope.setMinX(mapEnvelope.getMinX() + deltaX);
		mapEnvelope.setMinY(mapEnvelope.getMinY() + deltaY);
	}
	
	public static void adjustEnvelopeForPan(DoittEnvelope e, String direction, double panFactor) {
		
		double horizontalPanFactor = getHorizontalPanFactor(direction, panFactor);
		double verticalPanFactor = getVerticalPanFactor(direction, panFactor);

		double deltaX = horizontalPanFactor * e.getWidth();
		double deltaY = verticalPanFactor * e.getHeight();
		
		e.setMaxX(e.getMaxX() + deltaX);
		e.setMaxY(e.getMaxY() + deltaY);
		e.setMinX(e.getMinX() + deltaX);
		e.setMinY(e.getMinY() + deltaY);
	}
	
	static double getHorizontalPanFactor(String direction, double panFactor) {
		if (EAST.equalsIgnoreCase(direction))
			return panFactor;
		if (NORTHEAST.equalsIgnoreCase(direction))
			return panFactor;
		if (SOUTHEAST.equalsIgnoreCase(direction))
			return panFactor;
		if (WEST.equalsIgnoreCase(direction))
			return -1 * panFactor;
		if (NORTHWEST.equalsIgnoreCase(direction))
			return -1 * panFactor;
		if (SOUTHWEST.equalsIgnoreCase(direction))
			return -1 * panFactor;
		return 0;
	}
	
	static double getVerticalPanFactor(String direction, double panFactor) {
		if (NORTH.equalsIgnoreCase(direction))
			return panFactor;
		if (NORTHEAST.equalsIgnoreCase(direction))
			return panFactor;
		if (NORTHWEST.equalsIgnoreCase(direction))
			return panFactor;
		if (SOUTH.equalsIgnoreCase(direction))
			return -1 * panFactor;
		if (SOUTHEAST.equalsIgnoreCase(direction))
			return -1 * panFactor;
		if (SOUTHWEST.equalsIgnoreCase(direction))
			return -1 * panFactor;
		return 0;
	}
}