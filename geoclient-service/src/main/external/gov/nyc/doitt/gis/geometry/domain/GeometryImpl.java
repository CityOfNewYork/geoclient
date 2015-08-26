package gov.nyc.doitt.gis.geometry.domain;


import java.util.Collection;

public abstract class GeometryImpl extends SerializationPrecision implements Geometry {
	/* (non-Javadoc)
	 * @see gov.nyc.doitt.gis.geometry.domain.Geometry#getAllPoints()
	 */
	public abstract Collection<DoittPoint> getAllPoints();
	/* (non-Javadoc)
	 * @see gov.nyc.doitt.gis.geometry.domain.Geometry#getCenter()
	 */
	public DoittPoint getCenter(){
		double xTotal = 0;
		double yTotal = 0;
		int count = 0;
		for (DoittPoint p : getAllPoints()){
			xTotal += p.getX();
			yTotal += p.getY();
			count++;
		}
		if (this.isMapCoordinates())
			return new MapPoint(xTotal/count, yTotal/count);
		return new ImagePoint(xTotal/count, yTotal/count);
	};
	private DoittPoint getFirstPoint(){
		for (DoittPoint p : getAllPoints()){
			return p;
		}
		return null;
	};
	/* (non-Javadoc)
	 * @see gov.nyc.doitt.gis.geometry.domain.Geometry#isMapCoordinates()
	 */
	public boolean isMapCoordinates(){
		return getFirstPoint() instanceof MapPoint;
	}
	/* (non-Javadoc)
	 * @see gov.nyc.doitt.gis.geometry.domain.Geometry#isImageCoordinates()
	 */
	public boolean isImageCoordinates(){
		return getFirstPoint() instanceof ImagePoint;
	}
}
