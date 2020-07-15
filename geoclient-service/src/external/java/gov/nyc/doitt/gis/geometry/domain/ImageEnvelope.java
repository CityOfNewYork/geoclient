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
 * The ImageEnvelope is an extension of EnvelopeImpl for a rectangle that
 * represents a bounding-box or an extent specified in image coordinates from an
 * image element of a web page. The fields lowerLeft and upperRight are
 * ImagePoint objects as is the ImageEnvelope's center.
 *
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public class ImageEnvelope extends EnvelopeImpl {

    public ImageEnvelope() {
        super();
        setUnits(PIXELS);
    }

    public ImageEnvelope(double minX, double minY, double maxX, double maxY) {
        super(minX, minY, maxX, maxY);
        setUnits(PIXELS);
    }

    @Override
    public DoittPoint getPoint(double x, double y) {
        return new ImagePoint(x, y);
    }

}
