package gov.nyc.doitt.gis.geometry.transform;

/**
 * Thrown when an exception occurs during a coordinate tranformation.
 * 
 * @author mlipper
 *
 */
public class CoordinateTranformationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CoordinateTranformationException(String message) {
		super(message);
	}
	
	public CoordinateTranformationException(String message, Throwable cause) {
		super(message, cause);
	}

}
