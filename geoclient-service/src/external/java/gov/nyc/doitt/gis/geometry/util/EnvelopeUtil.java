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
package gov.nyc.doitt.gis.geometry.util;

import gov.nyc.doitt.gis.geometry.domain.DoittEnvelope;
import gov.nyc.doitt.gis.geometry.domain.DoittPoint;
import gov.nyc.doitt.gis.geometry.domain.Geometry;

public class EnvelopeUtil {
	public static final boolean contains(DoittEnvelope outer, Geometry inner) {
		double maxX = outer.getMaxX();
		double minX = outer.getMinX();
		double maxY = outer.getMaxY();
		double minY = outer.getMinY();
		for (DoittPoint p : inner.getAllPoints()){
			double x = p.getX();
			if (x > maxX) return false;
			if (x < minX) return false;
			double y = p.getY();
			if (y > maxY) return false;
			if (y < minY) return false;
		}
		return true;
	}
	
	public static final boolean contains (DoittEnvelope outer, DoittEnvelope inner) {
		double outerMinX = outer.getMinX();
		double outerMinY = outer.getMinY();
		double outerMaxX = outer.getMaxX();
		double outerMaxY = outer.getMaxY();
		
		double innerMinX = inner.getMinX();
		double innerMinY = inner.getMinY();
		double innerMaxX = inner.getMaxX();
		double innerMaxY = inner.getMaxY();
		
		if (outerMinX <= innerMinX && outerMinY <= innerMinY &&
				outerMaxX >= innerMaxX && outerMaxY >= innerMaxY) {
			return true;
		}
		return false;
	}
}