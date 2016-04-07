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

/**
 * The DoittPoint represents a location in a planar coordinate system.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public interface DoittPoint extends Comparable<DoittPoint>, Cloneable {
	/**
	 * @return The x coordinate of the point.
	 */
	double getX();

	/**
	 * @return The y coordinate of the point.
	 */
	double getY();

	/**
	 * @param x
	 *            The x coordinate of the point.
	 */
	void setX(double x);

	/**
	 * @param y
	 *            The x coordinate of the point.
	 */
	void setY(double y);
	
	boolean isValid();
	
	Object clone();
}
