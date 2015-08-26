package gov.nyc.doitt.gis.geometry.util;

import gov.nyc.doitt.gis.geometry.domain.DoittEnvelope;
import gov.nyc.doitt.gis.geometry.domain.DoittPoint;
import gov.nyc.doitt.gis.geometry.domain.Geometry;

public class EnvelopeUtil {
	public static final boolean contains(DoittEnvelope outer, Geometry inner) {
		double maxX = outer.getMaxX();
		double minX = outer.getMinX();
		double maxY = outer.getMaxY();
		double minY = outer.getMinY();
		for (DoittPoint p : inner.getAllPoints()){
			double x = p.getX();
			if (x > maxX) return false;
			if (x < minX) return false;
			double y = p.getY();
			if (y > maxY) return false;
			if (y < minY) return false;
		}
		return true;
	}
	
	public static final boolean contains (DoittEnvelope outer, DoittEnvelope inner) {
		double outerMinX = outer.getMinX();
		double outerMinY = outer.getMinY();
		double outerMaxX = outer.getMaxX();
		double outerMaxY = outer.getMaxY();
		
		double innerMinX = inner.getMinX();
		double innerMinY = inner.getMinY();
		double innerMaxX = inner.getMaxX();
		double innerMaxY = inner.getMaxY();
		
		if (outerMinX <= innerMinX && outerMinY <= innerMinY &&
				outerMaxX >= innerMaxX && outerMaxY >= innerMaxY) {
			return true;
		}
		return false;
	}
}