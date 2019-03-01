package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.HashMap;
import java.util.Map;

import com.github.dozermapper.core.CustomConverter;

import gov.nyc.doitt.gis.geoclient.api.InputParam;
import gov.nyc.doitt.gis.geoclient.service.search.GeosupportReturnCode;

public class ResponseStatusCustomConverter implements CustomConverter {

    public ResponseStatusCustomConverter() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }
        if (source instanceof Map<?, ?>) {
            GeosupportReturnCode grc = destination != null ? (GeosupportReturnCode) destination
                    : new GeosupportReturnCode();
            return convertToReturnCode((Map<String, Object>) source, grc);
        }
        if (source instanceof GeosupportReturnCode) {
            Map<String, Object> map = destination != null ? (Map<String, Object>) destination : new HashMap<>();
            return convertToMap((GeosupportReturnCode) source, map);
        }

        String msg = String.format("Don't know how to convert from {} to {}. Expected source",
                source.getClass().getCanonicalName(),
                destination != null ? destination.getClass().getCanonicalName() : "null");
        throw new IllegalArgumentException(msg);
    }

    private GeosupportReturnCode convertToReturnCode(Map<String, Object> source, GeosupportReturnCode destination) {
        destination.setMessage(source.get(OutputParam.));
        return null;
    }

    private Map<String, Object> convertToMap(GeosupportReturnCode source, Map<String, Object> destination) {
        Map<String, Object> map = new HashMap<>();
        if (destination != null) {
            destination.clear();
        }
        return null;
    }

}
