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
