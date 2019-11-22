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

import java.util.StringTokenizer;

import gov.nyc.doitt.gis.geometry.domain.DoittEnvelope;
import gov.nyc.doitt.gis.geometry.domain.ImagePoint;
import gov.nyc.doitt.gis.geometry.domain.MapEnvelope;
import gov.nyc.doitt.gis.geometry.domain.MapPoint;

/**
 * This class provides static methods used to convert map
 * coordinates to image coordinates.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public final class MapToImage {

	/**
	 * This method assumes that parameter p is a DoittPoint in map coordinates
	 * and converts it under the assumption that the mapEnv parameter field is
	 * properly set in map coordinates and that the imgHeight and imgWidth are
	 * the dimensions of the map image in pixels.
	 * 
	 * @param mapPoint
	 *            The DoittPoint to convert from map coordinates to image
	 *            coordinates.
	 * @param mapEnv
	 *            A DoittEnvelope that is the map extent in map coordinates.
	 * @param imgHeight
	 *            The pixel height of the map image.
	 * @param imgWidth
	 *            The pixel width of the map image.
	 */
	public static final ImagePoint convertPoint(MapPoint mapPoint, MapEnvelope mapEnv, int imgHeight, int imgWidth) {
		long imageY = Math.round(convertY(mapPoint, mapEnv, imgHeight));
		long imageX = Math.round(convertX(mapPoint, mapEnv, imgWidth));
		return new ImagePoint(imageX, imageY);
	}

	static final double convertX(MapPoint mapPoint, MapEnvelope mapEnv, int imgWidth) {
		double mapX = mapPoint.getX();
		double pixelsPerHorizontalMapUnit = getPixelsPerHorizontalMapUnit(imgWidth, mapEnv.getWidth());
		return (mapX - mapEnv.getMinX()) * pixelsPerHorizontalMapUnit;
	}

	static final double convertY(MapPoint mapPoint, DoittEnvelope mapEnv, int imgHeight) {
		double mapY = mapPoint.getY();
		double pixelsPerVerticalMapUnit = getPixelsPerVerticalMapUnit(imgHeight, mapEnv.getHeight());
		return correctOrigin((mapY - mapEnv.getMinY()) * pixelsPerVerticalMapUnit, imgHeight);
	}

	static final double correctOrigin(double y, int imgHeight) {
		// Image origin is top left while map origin is bottom left
		return imgHeight - y;
	}

	static final double getPixelsPerHorizontalMapUnit(int imgWidth, double mapWidth) {
		return imgWidth / mapWidth;
	}

	static final double getPixelsPerVerticalMapUnit(int imgHeight, double mapHeight) {
		return imgHeight / mapHeight;
	}
	
	/**
	 * @param allMapPoints List of map points where x and y are separated by 
	 * commas and each point x/y pair is separated by a space.
	 * @return List of image points where x and y are separated by commas
	 * and each point x/y pair is separated by a space.
	 */
	public static final String convertAllPoints(String allMapPoints, 
			String envMinXString, String envMinYString, String envMaxXString, String envMaxYString, 
			String imgHeightString, String imgWidthString) {
		StringBuffer allImagePoints = new StringBuffer();
		
		StringTokenizer tokenizer = new StringTokenizer(allMapPoints, " ");
		while (tokenizer.hasMoreTokens()) {
			String mapXYPair = tokenizer.nextToken();
			int idx = mapXYPair.indexOf(',');
			if (idx != -1) {
				String mapX = mapXYPair.substring(0, idx);
				String mapY = mapXYPair.substring(idx+1);
				ImagePoint imagePoint = getImagePoint(mapX, mapY, envMinXString, envMinYString, envMaxXString, envMaxYString, imgHeightString, imgWidthString);
				allImagePoints.append(imagePoint.getX() + "," + imagePoint.getY() + " ");
			}
		}
		return allImagePoints.toString().trim();
	}
	
	public static final double convertPointX(String mapX, String mapY, 
			String envMinX, String envMinY, String envMaxX, String envMaxY, 
			String imgHeight, String imgWidth) {
		ImagePoint imgPoint = getImagePoint(mapX, mapY, envMinX, envMinY, envMaxX, envMaxY, imgHeight, imgWidth);
		return imgPoint.getX();
	}

	public static final double convertPointX(double mapX, double mapY, 
			double envMinX, double envMinY, double envMaxX, double envMaxY, 
			int imgHeight, int imgWidth) {
		ImagePoint imgPoint = getImagePoint(mapX, mapY, envMinX, envMinY, envMaxX, envMaxY, imgHeight, imgWidth);
		return imgPoint.getX();
	}

	public static final double convertPointY(String mapX, String mapY, 
			String envMinX, String envMinY, String envMaxX, String envMaxY, 
			String imgHeight, String imgWidth) {
		ImagePoint imgPoint = getImagePoint(mapX, mapY, envMinX, envMinY, envMaxX, envMaxY, imgHeight, imgWidth);
		return imgPoint.getY();
	}

	public static final double convertPointY(double mapX, double mapY, 
			double envMinX, double envMinY, double envMaxX, double envMaxY, 
			int imgHeight, int imgWidth) {
		ImagePoint imgPoint = getImagePoint(mapX, mapY, envMinX, envMinY, envMaxX, envMaxY, imgHeight, imgWidth);
		return imgPoint.getY();
	}

	private static ImagePoint getImagePoint(String mapXString, String mapYString, 
			String envMinXString, String envMinYString, String envMaxXString, String envMaxYString, 
			String imgHeightString, String imgWidthString) {
		double mapX = Double.valueOf(mapXString);
		double mapY = Double.valueOf(mapYString);
		double envMinX = Double.valueOf(envMinXString);
		double envMinY = Double.valueOf(envMinYString);
		double envMaxX = Double.valueOf(envMaxXString);
		double envMaxY = Double.valueOf(envMaxYString);
		int imgHeight = Integer.valueOf(imgHeightString);
		int imgWidth = Integer.valueOf(imgWidthString);
		
		ImagePoint imgPoint = getImagePoint(mapX, mapY, envMinX, envMinY, envMaxX, envMaxY, imgHeight, imgWidth);
		return imgPoint;
	}

	private static ImagePoint getImagePoint(double mapX, double mapY, double envMinX, double envMinY, double envMaxX, double envMaxY, int imgHeight, int imgWidth) {
		MapPoint mapPoint = new MapPoint(mapX, mapY);
		MapEnvelope mapEnv = new MapEnvelope(envMinX, envMinY, envMaxX, envMaxY);
		ImagePoint imgPoint = MapToImage.convertPoint(mapPoint, mapEnv, imgHeight, imgWidth);
		return imgPoint;
	}
}