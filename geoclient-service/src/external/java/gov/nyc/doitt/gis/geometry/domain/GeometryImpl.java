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

public abstract class GeometryImpl extends SerializationPrecision implements Geometry {
    /*
     * (non-Javadoc)
     *
     * @see gov.nyc.doitt.gis.geometry.domain.Geometry#getAllPoints()
     */
    public abstract Collection<DoittPoint> getAllPoints();

    /*
     * (non-Javadoc)
     *
     * @see gov.nyc.doitt.gis.geometry.domain.Geometry#getCenter()
     */
    public DoittPoint getCenter() {
        double xTotal = 0;
        double yTotal = 0;
        int count = 0;
        for (DoittPoint p : getAllPoints()) {
            xTotal += p.getX();
            yTotal += p.getY();
            count++;
        }
        if (this.isMapCoordinates())
            return new MapPoint(xTotal / count, yTotal / count);
        return new ImagePoint(xTotal / count, yTotal / count);
    };

    private DoittPoint getFirstPoint() {
        for (DoittPoint p : getAllPoints()) {
            return p;
        }
        return null;
    };

    /*
     * (non-Javadoc)
     *
     * @see gov.nyc.doitt.gis.geometry.domain.Geometry#isMapCoordinates()
     */
    public boolean isMapCoordinates() {
        return getFirstPoint() instanceof MapPoint;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.nyc.doitt.gis.geometry.domain.Geometry#isImageCoordinates()
     */
    public boolean isImageCoordinates() {
        return getFirstPoint() instanceof ImagePoint;
    }
}
