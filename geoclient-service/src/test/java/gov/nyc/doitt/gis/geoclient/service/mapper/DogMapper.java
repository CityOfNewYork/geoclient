package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.Map;

class DogMapper extends AbstractParameterMapper<Dog> {

    @Override
    public Dog fromParameters(Map<String, Object> source, Dog destination) throws MappingException {
        String name = (String) source.get("name");
        destination.setName(name);
        return destination;
    }

    @Override
    public Map<String, Object> toParameters(Dog source, Map<String, Object> destination) throws MappingException {
        destination.put("name", source.getName());
        return destination;
    }
}