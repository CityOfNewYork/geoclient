package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.Map;

public interface Mapper<T> {

    void fromParameters(Map<String, Object> source, T destination) throws MappingException;
    
    void toParameters(T source, Map<String, Object> destination) throws MappingException;
    
    String getId();
}
