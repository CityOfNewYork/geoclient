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
package gov.nyc.doitt.gis.geometry.util;

import gov.nyc.doitt.gis.geometry.domain.DoittEnvelope;
import gov.nyc.doitt.gis.geometry.domain.DoittPoint;
import gov.nyc.doitt.gis.geometry.domain.ImageEnvelope;
import gov.nyc.doitt.gis.geometry.domain.ImagePoint;
import gov.nyc.doitt.gis.geometry.domain.MapEnvelope;
import gov.nyc.doitt.gis.geometry.domain.MapPoint;
import gov.nyc.doitt.gis.geometry.domain.PointGeometry;

/**
 * This class provides static methods used by the map service to convert image
 * coordinates to map coordinates.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public final class ImageToMap {

    /**
     * @param distanceInPixels A distance value in pixels to be converted to map
     *                         units.
     */
    public static final double convertDistance(int imgWidth, int imgHeight, MapEnvelope env, double distanceInPixels) {
        double adjustForAspectRatio = adjustForAspectRatio(imgWidth, imgHeight, env);
        double pixelWidth = getPixelWidth(imgWidth, adjustForAspectRatio);
        return distanceInPixels * pixelWidth;
    }

    public static final MapEnvelope convertEnvelope(ImageEnvelope userEnv, MapEnvelope mapEnv, int imgHeight,
            int imgWidth) {
        ImagePoint imageUpperLeft = new ImagePoint(userEnv.getMinX(), userEnv.getMinY());
        ImagePoint imageLowerRight = new ImagePoint(userEnv.getMaxX(), userEnv.getMaxY());
        MapPoint mapUpperLeft = convertPoint(imageUpperLeft, mapEnv, imgHeight, imgWidth);
        MapPoint mapLowerLeft = convertPoint(imageLowerRight, mapEnv, imgHeight, imgWidth);

        MapEnvelope result = new MapEnvelope();
        result.setMinX(mapUpperLeft.getX());
        result.setMinY(mapLowerLeft.getY());
        result.setMaxX(mapLowerLeft.getX());
        result.setMaxY(mapUpperLeft.getY());

        return result;
    }

    /**
     * This method assumes that parameter p is a DoittPoint in image coordinates and
     * converts it under the assumption that the mapEnv parameter field is properly
     * set in map coordinates and that the imgHeight and imgWidth are the dimensions
     * of the map image in pixels.
     * 
     * @param imagePoint The DoittPoint to convert from image coordinates to map
     *                   coordinates.
     * @param mapEnv     A DoittEnvelope that is the map extent in map coordinates.
     * @param imgHeight  The pixel height of the map image.
     * @param imgWidth   The pixel width of the map image.
     */
    public static final MapPoint convertPoint(ImagePoint imagePoint, MapEnvelope mapEnv, int imgHeight, int imgWidth) {
        double mapX = convertX(imagePoint, mapEnv, imgWidth);
        double mapY = convertY(imagePoint, mapEnv, imgHeight);
        return new MapPoint(mapX, mapY);
    }

    public static final PointGeometry convertPoint(PointGeometry imagePoint, MapEnvelope mapEnv, int imgHeight,
            int imgWidth) {
        double mapX = convertX(imagePoint.getPoint(), mapEnv, imgWidth);
        double mapY = convertY(imagePoint.getPoint(), mapEnv, imgHeight);
        return new PointGeometry(new MapPoint(mapX, mapY));
    }

    static final double convertX(DoittPoint imagePoint, DoittEnvelope mapEnv, int imgWidth) {
        double pixelWidth = getPixelWidth(imgWidth, mapEnv.getWidth());
        double imageX = imagePoint.getX();
        return mapEnv.getMinX() + (imageX * pixelWidth);
    }

    static final double convertY(DoittPoint imagePoint, DoittEnvelope mapEnv, int imgHeight) {
        double imageY = imagePoint.getY();
        double pixelHeight = getPixelHeight(imgHeight, mapEnv.getHeight());
        return mapEnv.getMinY() + (correctOrigin(imageY, imgHeight) * pixelHeight);
    }

    static final int correctOrigin(double imgY, int imgHeight) {
        // Image origin is top left while map origin is bottom left
        return imgHeight - new Double(imgY).intValue();
    }

    static final double getPixelHeight(int imgHeight, double mapHeight) {
        return mapHeight / imgHeight;
    }

    static final double getPixelWidth(int imgWidth, double mapWidth) {
        return mapWidth / imgWidth;
    }

    static final double adjustForAspectRatio(int imgWidth, int imgHeight, DoittEnvelope e) {
        double ratio = imgWidth / imgHeight;
        if (e.getWidth() / e.getHeight() >= ratio) {
            return e.getWidth();
        }
        return ratio * e.getHeight();
    }
}