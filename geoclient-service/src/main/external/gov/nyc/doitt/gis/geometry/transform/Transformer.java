package gov.nyc.doitt.gis.geometry.transform;

import gov.nyc.doitt.gis.geometry.domain.MapPoint;

/**
 * Performs implementation-dependent transformations of a point from one
 * coordinate reference system to another.
 * 
 * @author mlipper
 */
public interface Transformer {

	MapPoint transform(double x, double y)
			throws CoordinateTranformationException;

}