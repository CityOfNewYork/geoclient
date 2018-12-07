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
 * The DoittEnvelope is a rectangle that represents a bounding-box or an extent.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public interface DoittEnvelope extends Cloneable {
	/**
	 * @return boolean is the geometry contained within the envelope.
	 */
	boolean contains(Geometry geometry);

	/**
	 * @return A DoittPoint that is the center of the DoittEnvelope.
	 */
	DoittPoint getCenter();

	/**
	 * @param p A DoittPoint that is the center of the DoittEnvelope.
	 */
	void setCenter(DoittPoint p);

	/**
	 * @return The length of the vertical dimension of hte DoittEnvelope.
	 */
	double getHeight();

	/**
	 * @return A DoittPoint (minX, minY) that is the lower left corner of the
	 *         DoittEnvelope.
	 */
	DoittPoint getLowerLeft();

	/**
	 * @return The maximum x coordinate value of the DoittEnvelope.
	 */
	double getMaxX();

	/**
	 * @return The maximum y coordinate value of the DoittEnvelope.
	 */
	double getMaxY();

	/**
	 * @return The minimum x coordinate value of the DoittEnvelope.
	 */
	double getMinX();

	/**
	 * @return The maximum y coordinate value of the DoittEnvelope.
	 */
	double getMinY();

	/**
	 * @return A DoittPoint (maxX, maxY) that is the upper right corner of the
	 *         DoittEnvelope.
	 */
	DoittPoint getUpperRight();

	/**
	 * @return The length of the horizontal dimension of hte DoittEnvelope.
	 */
	double getWidth();

	/**
	 * @param lowerLeft
	 *            A DoittPoint (minX, minY) that is the lower left corner of the
	 *            DoittEnvelope.
	 */
	void setLowerLeft(DoittPoint lowerLeft);

	/**
	 * @param maxX
	 *            The maximum x coordinate value of the DoittEnvelope.
	 */
	void setMaxX(double maxX);

	/**
	 * @param maxY
	 *            The maximum y coordinate value of the DoittEnvelope.
	 */
	void setMaxY(double maxY);

	/**
	 * @param minX
	 *            The minimum x coordinate value of the DoittEnvelope.
	 */
	void setMinX(double minX);

	/**
	 * @param minY
	 *            The minimum y coordinate value of the DoittEnvelope.
	 */
	void setMinY(double minY);

	/**
	 * Set a DoittPoint (maxX, maxY) that is the upper right corner of the
	 * DoittEnvelope.
	 */
	void setUpperRight(DoittPoint upperRight);
}
