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

import java.util.ArrayList;
import java.util.Collection;

import gov.nyc.doitt.gis.geometry.util.EnvelopeUtil;

public abstract class EnvelopeImpl extends GeometryImpl implements DoittEnvelope {
	private DoittPoint lowerLeft;
	private DoittPoint upperRight;

	public EnvelopeImpl() {
		setLowerLeft(getPoint(0,0));
		setUpperRight(getPoint(0,0));
	}

	/**
	 * Constructs an envelope with the specified coordinates.
	 * 
	 * @param minX
	 *            The minimum x coordinate of the envelope.
	 * @param minY
	 *            The minimum y coordinate of the envelope.
	 * @param maxX
	 *            The maximum x coordinate of the envelope.
	 * @param maxY
	 *            The maximum y coordinate of the envelope.
	 */
	public EnvelopeImpl(double minX, double minY, double maxX, double maxY) {
		setLowerLeft(getPoint(minX, minY));
		setUpperRight(getPoint(maxX, maxY));
	}

	public abstract DoittPoint getPoint(double x, double y);

	@Override
	public Collection<DoittPoint> getAllPoints(){
		DoittPoint upperLeft = (DoittPoint)upperRight.clone();
		upperLeft.setX(upperLeft.getX() - getWidth());
		DoittPoint lowerRight = (DoittPoint)lowerLeft.clone();
		lowerRight.setX(lowerLeft.getX() + getWidth());
		Collection<DoittPoint> result = new ArrayList<DoittPoint>(4);
		result.add(lowerLeft);
		result.add(upperRight);
		result.add(upperLeft);
		result.add(lowerRight);
		return result;
	}
	
	public void setCenter(DoittPoint center) {
		double currentWidth = getWidth();
		double currentHeight = getHeight();

		double halfCurrentWidth = currentWidth / 2;
		double halfCurrentHeight = currentHeight / 2;
		
		double centerX = center.getX();
		double centerY = center.getY();

		lowerLeft.setX(centerX - halfCurrentWidth);
		lowerLeft.setY(centerY - halfCurrentHeight);

		upperRight.setX(centerX + halfCurrentWidth);
		upperRight.setY(centerY + halfCurrentHeight);
	}

	public final double getHeight() {
		return upperRight.getY() - lowerLeft.getY();
	}

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// ignore, clone is supported
		}
		EnvelopeImpl env = (EnvelopeImpl) clone;
		env.setLowerLeft((DoittPoint)getLowerLeft().clone());
		env.setUpperRight((DoittPoint)getUpperRight().clone());
		return env;
	}

	public int hashCode() {
		int hashCodeFactor = 37;
		int hashCode = 0;
		if (getLowerLeft() != null) {
			hashCode =  getLowerLeft().hashCode();
		}
		if (getUpperRight() != null) {
			hashCode += getUpperRight().hashCode();
		}
		return hashCodeFactor * hashCode;
	}

	public String toString() {
		return getClass().getName() + ": " + lowerLeft.toString() + ", " + upperRight.toString();
	}

	public final DoittPoint getLowerLeft() {
		return lowerLeft;
	}

	public final double getMaxX() {
		return upperRight.getX();
	}

	public final double getMaxY() {
		return upperRight.getY();
	}

	public final double getMinX() {
		return lowerLeft.getX();
	}

	public final double getMinY() {
		return lowerLeft.getY();
	}

	public final DoittPoint getUpperRight() {
		return upperRight;
	}

	public final double getWidth() {
		return upperRight.getX() - lowerLeft.getX();
	}

	public final void setLowerLeft(DoittPoint lowerLeft) {
		this.lowerLeft = lowerLeft;
	}

	public final void setMaxX(double maxX) {
		upperRight.setX(maxX);
	}

	public final void setMaxY(double maxY) {
		upperRight.setY(maxY);
	}

	public final void setMinX(double minX) {
		lowerLeft.setX(minX);
	}

	public final void setMinY(double minY) {
		lowerLeft.setY(minY);
	}

	public final void setUpperRight(DoittPoint upperRight) {
		this.upperRight = upperRight;
	}
	
	public final boolean isValid() {
		return getLowerLeft().isValid() && getUpperRight().isValid();
	}
	
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if (!(this.getClass().equals(o.getClass())))
			return false;

		EnvelopeImpl other = (EnvelopeImpl) o;

		boolean isLowerLeftEqual;
		
		if (getLowerLeft() == null && other.getLowerLeft() == null) {
			isLowerLeftEqual = true;
		} else if (getLowerLeft() == null || other.getLowerLeft() == null) {
			isLowerLeftEqual = false;
		} else {
			isLowerLeftEqual = getLowerLeft().equals(other.getLowerLeft());
		}
		
		if (!isLowerLeftEqual) {
			return false;
		}
		
		boolean isUpperRightEqual;
		if (getUpperRight() == null && other.getUpperRight() == null) {
			isUpperRightEqual = true;
		} else if (getUpperRight() == null || other.getUpperRight() == null) {
			isUpperRightEqual = false;
		} else {
			isUpperRightEqual = getUpperRight().equals(other.getUpperRight());
		}
		
		return isUpperRightEqual;
	}
	
	static void updateEnvelope(DoittEnvelope template, DoittEnvelope e) {
		e.setMinX(template.getMinX());
		e.setMinY(template.getMinY());
		e.setMaxX(template.getMaxX());
		e.setMaxY(template.getMaxY());
	}

	public boolean contains(Geometry geometry) {
		return EnvelopeUtil.contains(this, geometry);
	}
}
