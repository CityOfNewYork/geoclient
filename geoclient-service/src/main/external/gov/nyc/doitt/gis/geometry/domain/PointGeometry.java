package gov.nyc.doitt.gis.geometry.domain;

import java.util.Collection;
import java.util.Collections;

public class PointGeometry extends GeometryImpl {
	private DoittPoint point;

	public PointGeometry() {
	}

	public PointGeometry(DoittPoint point) {
		this.point = point;
	}

	public DoittPoint getPoint() {
		return point;
	}

	public void setPoint(DoittPoint point) {
		this.point = point;
	}

	@Override
	public Collection<DoittPoint> getAllPoints() {
		return Collections.singleton(point);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PointGeometry){
			PointGeometry geom = (PointGeometry) obj;
			DoittPoint myPoint = getPoint();
			DoittPoint otherPoint = geom.getPoint();
			return myPoint.equals(otherPoint);
		}
		return false;
	}
}
