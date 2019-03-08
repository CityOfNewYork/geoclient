package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.Map;

class CatMapper extends AbstractParameterMapper<Cat> {

    @Override
    public Cat fromParameters(Map<String, Object> source, Cat destination) throws MappingException {
        String name = (String) source.get("name");
        destination.setName(name);
        return destination;
    }

    @Override
    public Map<String, Object> toParameters(Cat source, Map<String, Object> destination) throws MappingException {
        destination.put("name", source.getName());
        return destination;
    }

}