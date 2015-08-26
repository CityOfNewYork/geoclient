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
