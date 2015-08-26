package gov.nyc.doitt.gis.geometry.domain;

import java.util.Collection;
import java.util.HashSet;

public class PolygonGeometry extends GeometryImpl {
	private Collection<ExteriorPolygon> exteriorPolygons;

	public PolygonGeometry() {
	}

	public PolygonGeometry(Collection<ExteriorPolygon> exteriorPolygons) {
		this.exteriorPolygons = exteriorPolygons;
	}

	public Collection<ExteriorPolygon> getExteriorPolygons() {
		return exteriorPolygons;
	}

	public void setExteriorPolygons(Collection<ExteriorPolygon> exteriorPolygons) {
		this.exteriorPolygons = exteriorPolygons;
	}

	@Override
	public Collection<DoittPoint> getAllPoints() {
		Collection<DoittPoint> all = new HashSet<DoittPoint>();
		if (exteriorPolygons == null) {
			return all;
		}
		
		for (ExteriorPolygon extPol : exteriorPolygons) {
			DoittPoint[] extPts = extPol.getExteriorPoints();
			for (int i = 0; extPts != null && i < extPts.length; i++) {
				all.add(extPts[i]);
			}
		}
		return all;
	}
}
