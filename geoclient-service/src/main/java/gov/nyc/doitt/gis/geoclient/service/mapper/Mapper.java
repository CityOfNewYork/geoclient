package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.Map;

public interface Mapper<T> {

    T fromParameters(Map<String, Object> source, Class<T> destinationClass) throws MappingException;

    T fromParameters(Map<String, Object> source, T destination) throws MappingException;

    Map<String, Object> toParameters(T source, Map<String, Object> destination) throws MappingException;
    
    Map<String, Object> toParameters(T source) throws MappingException;
}
