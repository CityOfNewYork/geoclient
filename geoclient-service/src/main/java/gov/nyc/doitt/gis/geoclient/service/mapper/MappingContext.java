package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingContext {

    private final ConcurrentMap<Class<?>, Mapper<?>> mappers = new ConcurrentHashMap<Class<?>, Mapper<?>>();

    public MappingContext() {
    }

    public MappingContext(List<Mapper<?>> mappers) {
        this.mappers.putAll(mappers.stream().collect(Collectors.toMap(Mapper::getClass, Function.identity())));
    }

    public Mapper<?> add(Mapper<?> mapper) {
        return this.mappers.put(mapper.getClass(), mapper);
    }

    public boolean containsMapper(Class<?> mapperClass) {
        return this.mappers.containsKey(mapperClass);
    }

    public Mapper<?> getMapper(Class<?> clazz) {
        if (this.mappers.containsKey(clazz)) {
            return this.mappers.get(clazz);
        }
        throw new IllegalArgumentException(
                String.format("No Mapper configured for Class<%s>", clazz.getCanonicalName()));
    }
}
