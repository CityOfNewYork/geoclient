package gov.nyc.doitt.gis.geoclient.service.mapper;

import com.github.dozermapper.core.MapperModelContext;
import com.github.dozermapper.core.MappingException;
import com.github.dozermapper.core.metadata.MappingMetadata;

public class LegacyMapper implements com.github.dozermapper.core.Mapper {

    private final com.github.dozermapper.core.Mapper target;

    public LegacyMapper(com.github.dozermapper.core.Mapper target) {
        super();
        this.target = target;
    }

    public <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        return target.map(source, destinationClass);
    }

    public void map(Object source, Object destination) throws MappingException {
        target.map(source, destination);
    }

    public int hashCode() {
        return target.hashCode();
    }

    public <T> T map(Object source, Class<T> destinationClass, String mapId) throws MappingException {
        return target.map(source, destinationClass, mapId);
    }

    public void map(Object source, Object destination, String mapId) throws MappingException {
        target.map(source, destination, mapId);
    }

    public MappingMetadata getMappingMetadata() {
        return target.getMappingMetadata();
    }

    public MapperModelContext getMapperModelContext() {
        return target.getMapperModelContext();
    }

    public boolean equals(Object obj) {
        return target.equals(obj);
    }

    public String toString() {
        return target.toString();
    }

}
