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