package gov.nyc.doitt.gis.geometry.domain;

/**
 * The ImagePoint is an extension of PointImpl for a point specified in image
 * coordinates from an image element of a web page..
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public class ImagePoint extends PointImpl {

	public ImagePoint() {
		super();
		setUnits(PIXELS);
	}

	public ImagePoint(double x, double y) {
		super(x, y);
		setUnits(PIXELS);
	}
}
