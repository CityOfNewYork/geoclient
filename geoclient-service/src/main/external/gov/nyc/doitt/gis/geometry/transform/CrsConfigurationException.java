package gov.nyc.doitt.gis.geometry.transform;

/**
 * This indicates a programmer error in configuring the metadata necessary to
 * create CRS classes.
 * 
 * @author mlipper
 * 
 */
public class CrsConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CrsConfigurationException(String message) {
		super(message);
	}

	public CrsConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
