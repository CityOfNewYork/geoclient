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

/**
 * The PointImpl is an abstract class that implements DoittPoint.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public abstract class PointImpl extends SerializationPrecision implements DoittPoint {

    private double x;
    private double y;

    public PointImpl() {
    }

    /**
     * Constructs a new point with the specified coordinates.
     * 
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     */
    public PointImpl(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public final void setX(double x) {
        this.x = x;
    }

    public final void setY(double y) {
        this.y = y;
    }

    public boolean isValid() {
        return x > 0 && y > 0;
    }

    private int compare(double d1, double d2) {
        if (d1 < d2)
            return -1;

        if (d1 > d2)
            return 1;

        return 0;
    }

    public int compareTo(DoittPoint point) {
        if (point == null) {
            return -1;
        }

        double pointX = point.getX();
        double pointY = point.getY();

        int result = compare(x, pointX);

        if (result == 0) {
            result = compare(y, pointY);
        }

        return result;
    }

    public int hashCode() {
        return 37 * new Double(1 + x).intValue() / new Double(1 + y).intValue();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this)
            return true;

        if (!(o.getClass().equals(this.getClass()))) {
            return false;
        }

        PointImpl other = (PointImpl) o;

        return x == other.x && y == other.y;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            // ignore, clone is supported
        }
        PointImpl point = (PointImpl) clone;
        point.setX(getX());
        point.setY(getY());
        return point;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + x + ", " + y;
    }
}
