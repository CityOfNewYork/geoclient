package gov.nyc.doitt.gis.geoclient.service.mapper;

public class MappingException extends RuntimeException {

    private static final long serialVersionUID = -8294913337317033662L;

    public MappingException(String message) {
    super(message);
    }

    public MappingException(String message, Throwable cause) {
    super(message, cause);
    }

    public MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    }

}
