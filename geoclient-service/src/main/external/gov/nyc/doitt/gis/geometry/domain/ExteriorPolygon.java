package gov.nyc.doitt.gis.geometry.domain;

import java.util.Collection;

public class ExteriorPolygon {
	private DoittPoint[] exteriorPoints;
	private Collection<DoittPoint[]> interiorPoints;
	
	public ExteriorPolygon(DoittPoint[] exteriorPoints,
			Collection<DoittPoint[]> interiorPoints) {
		this.exteriorPoints = exteriorPoints;
		this.interiorPoints = interiorPoints;
	}

	public ExteriorPolygon(DoittPoint[] exteriorPoints) {
		this.exteriorPoints = exteriorPoints;
	}

	public ExteriorPolygon() {
	}

	public DoittPoint[] getExteriorPoints() {
		return exteriorPoints;
	}
	
	public void setExteriorPoints(DoittPoint[] exteriorPoints) {
		this.exteriorPoints = exteriorPoints;
	}
	
	public Collection<DoittPoint[]> getInteriorPoints() {
		return interiorPoints;
	}
	
	public void setInteriorPoints(Collection<DoittPoint[]> interiorPoints) {
		this.interiorPoints = interiorPoints;
	}
	
	public boolean hasInteriorPoints() {
		if (interiorPoints == null || interiorPoints.size() < 1) {
			return false;
		}
		return true;
	}
}
