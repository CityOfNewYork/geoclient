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
package gov.nyc.doitt.gis.geometry.domain;

import java.util.Collection;
import java.util.HashSet;

public class LineGeometry extends GeometryImpl {
	private Collection<MapPoint[]> mapPoints;

	public LineGeometry() {
	}

	public LineGeometry(Collection<MapPoint[]> mapPoints) {
		this.mapPoints = mapPoints;
	}

	public Collection<MapPoint[]> getMapPoints() {
		return mapPoints;
	}

	public void setMapPoints(Collection<MapPoint[]> mapPoints) {
		this.mapPoints = mapPoints;
	}

	@Override
	public Collection<DoittPoint> getAllPoints() {
		Collection<DoittPoint> all = new HashSet<DoittPoint>();
		if (mapPoints == null) {
			return all;
		}
		for (DoittPoint[] pts : mapPoints) {
			for (int i = 0; pts != null && i < pts.length; i++) {
				all.add(pts[i]);
			}
		}
		return all;
	}
}
