package gov.nyc.doitt.gis.geoclient.gradle.ctx;

public class ValueResolutionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValueResolutionException(String message) {
        super(message);
    }

    public ValueResolutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
