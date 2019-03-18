package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.dozermapper.core.CustomConverter;

import gov.nyc.doitt.gis.geoclient.api.OutputParam;
import gov.nyc.doitt.gis.geoclient.service.search.GeosupportReturnCode;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;

public class ResponseStatusMapper extends AbstractParameterMapper<ResponseStatus>
        implements Mapper<ResponseStatus>, CustomConverter {

    public ResponseStatusMapper() {
        super();
    }

    @Override
    public ResponseStatus fromParameters(Map<String, Object> source, ResponseStatus responseStatus)
            throws MappingException {

        GeosupportReturnCode rc1 = responseStatus.getGeosupportReturnCode();
        if (rc1 == null) {
            rc1 = new GeosupportReturnCode();
            responseStatus.setGeosupportReturnCode(rc1);
        }

        convertToGeosupportReturnCodeOne(source, rc1);

        GeosupportReturnCode rc2 = responseStatus.getGeosupportReturnCode2();
        if (rc2 == null) {
            rc2 = new GeosupportReturnCode();
            responseStatus.setGeosupportReturnCode2(rc2);
        }

        convertToGeosupportReturnCodeTwo(source, rc2);

        List<String> similarNames = responseStatus.getSimilarNames();
        similarNames = similarNames != null ? similarNames : new ArrayList<>();

        convertToSimilarNamesList(source, similarNames);

        return responseStatus;
    }

    @Override
    public Map<String, Object> toParameters(ResponseStatus responseStatus, Map<String, Object> map)
            throws MappingException {
        GeosupportReturnCode rc1 = responseStatus.getGeosupportReturnCode();
        if (rc1 == null) {
            rc1 = new GeosupportReturnCode();
            responseStatus.setGeosupportReturnCode(rc1);
        }
        GeosupportReturnCode rc2 = responseStatus.getGeosupportReturnCode2();
        if (rc2 == null) {
            rc2 = new GeosupportReturnCode();
            responseStatus.setGeosupportReturnCode2(rc2);
        }

        convertToMapOfGeosupportReturnCodeOne(rc1, map);
        convertToMapOfGeosupportReturnCodeTwo(rc2, map);
        List<String> similarNames = responseStatus.getSimilarNames();
        similarNames = similarNames != null ? similarNames : new ArrayList<>();
        convertToMapOfSimilarNames(similarNames, map);
        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }

        if (source instanceof Map<?, ?>) {

            ResponseStatus responseStatus = destination != null ? (ResponseStatus) destination : new ResponseStatus();
            return fromParameters((Map<String, Object>) source, responseStatus);
        }

        if (source instanceof ResponseStatus) {
            ResponseStatus responseStatus = (ResponseStatus) source;
            Map<String, Object> map = destination != null ? (Map<String, Object>) destination : new HashMap<>();
            return toParameters(responseStatus, map);
        }

        throw new IllegalArgumentException(buildIllegalArgumentExceptionMessage(source, destination));
    }

    private String buildIllegalArgumentExceptionMessage(Object source, Object destination) {
        return String.format("Don't know how to convert from {} to {}. Expected source",
                source.getClass().getCanonicalName(),
                destination != null ? destination.getClass().getCanonicalName() : "null");
    }

    private void convertToGeosupportReturnCodeOne(Map<String, Object> source, GeosupportReturnCode dest) {
        dest.setReturnCode(stringValue(source.get(OutputParam.GEOSUPPORT_RETURN_CODE)));
        dest.setReasonCode(stringValue(source.get(OutputParam.REASON_CODE)));
        dest.setMessage(stringValue(source.get(OutputParam.MESSAGE)));
    }

    private void convertToGeosupportReturnCodeTwo(Map<String, Object> source, GeosupportReturnCode dest) {
        dest.setReturnCode(stringValue(source.get(OutputParam.GEOSUPPORT_RETURN_CODE2)));
        dest.setReasonCode(stringValue(source.get(OutputParam.REASON_CODE2)));
        dest.setMessage(stringValue(source.get(OutputParam.MESSAGE2)));
    }

    private void convertToMapOfGeosupportReturnCodeOne(GeosupportReturnCode returnCodeOne, Map<String, Object> dest) {
        dest.put(OutputParam.GEOSUPPORT_RETURN_CODE, returnCodeOne.getReturnCode());
        dest.put(OutputParam.REASON_CODE, returnCodeOne.getReasonCode());
        dest.put(OutputParam.MESSAGE, returnCodeOne.getMessage());
    }

    private void convertToMapOfGeosupportReturnCodeTwo(GeosupportReturnCode returnCodeTwo, Map<String, Object> dest) {
        dest.put(OutputParam.GEOSUPPORT_RETURN_CODE2, returnCodeTwo.getReturnCode());
        dest.put(OutputParam.REASON_CODE2, returnCodeTwo.getReasonCode());
        dest.put(OutputParam.MESSAGE2, returnCodeTwo.getMessage());
    }

    private String stringValue(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private void convertToMapOfSimilarNames(List<String> similarNames, Map<String, Object> dest) {
        int i = 1;
        for (String name : similarNames) {
            String key = "streetName" + Integer.toString(i);
            dest.put(key, name);
            i++;
        }
    }

    private void convertToSimilarNamesList(Map<String, Object> source, List<String> dest) {
        for (int i = 0; i < 10; i++) {
            String key = "streetName" + Integer.toString(i);
            String value = stringValue(source.get(key));
            if (value != null) {
                dest.add(value);
            }
        }
    }
}