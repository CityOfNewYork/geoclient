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
package gov.nyc.doitt.gis.geometry.domain;

import java.util.Collection;

public interface Geometry {

	/**
	 * Returns a collection of all points related to the Geometry.  Not necessary in 
	 * any order or grouping, just a big collection of them.
	 */
	public abstract Collection<DoittPoint> getAllPoints();

	public abstract DoittPoint getCenter();

	public abstract boolean isMapCoordinates();

	public abstract boolean isImageCoordinates();

}