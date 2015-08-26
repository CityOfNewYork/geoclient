package gov.nyc.doitt.gis.geometry.domain;


/**
 * The MapPoint is an extension of PointImpl for a point specifed in map
 * coordinates (also known as database coordinates).
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public class MapPoint extends PointImpl {

	public MapPoint() {
		super();
	}

	public MapPoint(double x, double y) {
		super(x, y);
	}
}
