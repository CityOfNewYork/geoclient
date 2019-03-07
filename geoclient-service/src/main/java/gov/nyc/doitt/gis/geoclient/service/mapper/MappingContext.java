package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingContext {

    private final ConcurrentMap<String, Mapper<? extends Object>> mappers = new ConcurrentHashMap<String, Mapper<? extends Object>>();

    public MappingContext() {
    }

    public MappingContext(List<Mapper<? extends Object>> mappers) {
	this.mappers.putAll(mappers.stream().collect(Collectors.toMap(Mapper::getId, Function.identity())));
    }
    
    public Mapper<? extends Object> add(Mapper<? extends Object> mapper) {
	return this.mappers.put(mapper.getId(), mapper);
    }
    
    public Mapper<?> getMapper(String id) {
	if(this.mappers.containsKey(id)) {
	    return this.mappers.get(id);
	}
	throw new IllegalArgumentException(String.format("No Mapper configured with id '%s'", id));
    }
}
