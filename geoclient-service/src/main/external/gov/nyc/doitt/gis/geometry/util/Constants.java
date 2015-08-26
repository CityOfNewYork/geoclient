package gov.nyc.doitt.gis.geometry.util;

import gov.nyc.doitt.gis.geometry.domain.DoittEnvelope;
import gov.nyc.doitt.gis.geometry.domain.MapEnvelope;

public interface Constants {
	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * east.
	 */
	static final String EAST = "E";

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * north.
	 */
	static final String NORTH = "N";

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * northeast.
	 */
	static final String NORTHEAST = "NE";

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * northwest.
	 */
	static final String NORTHWEST = "NW";

	/**
	 * Maximum x value of NYC_FULL_EXTENT.
	 */
	static final double NYC_MAX_X = 1067831.600072;

	/**
	 * Maximum y value of NYC_FULL_EXTENT.
	 */
	static final double NYC_MAX_Y = 273693.229912;

	/**
	 * Minimum x value of NYC_FULL_EXTENT.
	 */
	static final double NYC_MIN_X = 912489.999928;

	/**
	 * Minimum y value of NYC_FULL_EXTENT.
	 */
	static final double NYC_MIN_Y = 117727.750088;

	/**
	 * The default full map extent if the fullExtent field is not set.
	 */
	static final DoittEnvelope NYC_FULL_EXTENT = new MapEnvelope(
			NYC_MIN_X, NYC_MIN_Y, NYC_MAX_X, NYC_MAX_Y);

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * south.
	 */
	static final String SOUTH = "S";

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * southeast.
	 */
	static final String SOUTHEAST = "SE";

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * southwest.
	 */
	static final String SOUTHWEST = "SW";

	/**
	 * Use as the direction parameter of the pan method to move the map to the
	 * west.
	 */
	static final String WEST = "W";
}