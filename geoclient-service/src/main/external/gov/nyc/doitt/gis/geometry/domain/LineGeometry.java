package gov.nyc.doitt.gis.geometry.domain;

import java.util.Collection;
import java.util.HashSet;

public class LineGeometry extends GeometryImpl {
	private Collection<MapPoint[]> mapPoints;

	public LineGeometry() {
	}

	public LineGeometry(Collection<MapPoint[]> mapPoints) {
		this.mapPoints = mapPoints;
	}

	public Collection<MapPoint[]> getMapPoints() {
		return mapPoints;
	}

	public void setMapPoints(Collection<MapPoint[]> mapPoints) {
		this.mapPoints = mapPoints;
	}

	@Override
	public Collection<DoittPoint> getAllPoints() {
		Collection<DoittPoint> all = new HashSet<DoittPoint>();
		if (mapPoints == null) {
			return all;
		}
		for (DoittPoint[] pts : mapPoints) {
			for (int i = 0; pts != null && i < pts.length; i++) {
				all.add(pts[i]);
			}
		}
		return all;
	}
}
