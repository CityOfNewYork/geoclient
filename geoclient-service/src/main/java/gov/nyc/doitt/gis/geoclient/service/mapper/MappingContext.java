/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
