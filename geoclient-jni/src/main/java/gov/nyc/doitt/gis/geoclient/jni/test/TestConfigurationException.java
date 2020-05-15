package gov.nyc.doitt.gis.geoclient.jni.test;

/**
 *
 * Extends {@link RuntimeException} and indicates a problem with the creation of
 * {@link TestConfig} instances.
 *
 * @author mlipper
 *
 */
public class TestConfigurationException extends RuntimeException {

    /**
     * Generated serialVersionId.
     */
    private static final long serialVersionUID = -3864318281523561115L;

    /**
     * Creates a new {@link TestConfigurationException}.
     *
     * @param message describing the problem
     */
    public TestConfigurationException(String message) {
        super(message);
    }

}
