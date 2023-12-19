package com.digitalclash.geoclient.gradle;

/**
 * {@code RuntimeException} that indicates a property validation failure.
 */
public class GeosupportPropertyValidationException extends RuntimeException {
    /**
     * @{inheritDoc}
     */
    public GeosupportPropertyValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @{inheritDoc}
     */
    public GeosupportPropertyValidationException(Throwable cause) {
        super(cause);
    }
}
