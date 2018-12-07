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

public class ExteriorPolygon {
	private DoittPoint[] exteriorPoints;
	private Collection<DoittPoint[]> interiorPoints;
	
	public ExteriorPolygon(DoittPoint[] exteriorPoints,
			Collection<DoittPoint[]> interiorPoints) {
		this.exteriorPoints = exteriorPoints;
		this.interiorPoints = interiorPoints;
	}

	public ExteriorPolygon(DoittPoint[] exteriorPoints) {
		this.exteriorPoints = exteriorPoints;
	}

	public ExteriorPolygon() {
	}

	public DoittPoint[] getExteriorPoints() {
		return exteriorPoints;
	}
	
	public void setExteriorPoints(DoittPoint[] exteriorPoints) {
		this.exteriorPoints = exteriorPoints;
	}
	
	public Collection<DoittPoint[]> getInteriorPoints() {
		return interiorPoints;
	}
	
	public void setInteriorPoints(Collection<DoittPoint[]> interiorPoints) {
		this.interiorPoints = interiorPoints;
	}
	
	public boolean hasInteriorPoints() {
		if (interiorPoints == null || interiorPoints.size() < 1) {
			return false;
		}
		return true;
	}
}
