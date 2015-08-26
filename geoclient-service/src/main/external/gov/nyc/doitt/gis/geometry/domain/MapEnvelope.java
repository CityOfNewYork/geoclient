package gov.nyc.doitt.gis.geometry.domain;

/**
 * The MapEnvelope is an extension of EnvelopeImpl for a rectangle that
 * represents a bounding-box or an extent specified in map coordinates (also know
 * as database coordinates). The fields lowerLeft and upperRight are MapPoint
 * objects as is the MapEnvelope's center.
 * 
 * @author DOITT City-wide GIS
 * @version 1.0
 */
public class MapEnvelope extends EnvelopeImpl {
	public MapEnvelope() {
		super();
	}

	public MapEnvelope(double minX, double minY, double maxX, double maxY) {
		super(minX, minY, maxX, maxY);
	}

	@Override
	public DoittPoint getPoint(double x, double y) {
		return new MapPoint(x, y);
	}
}
